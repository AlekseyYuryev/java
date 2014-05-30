/*  Copyright Safris Software 2008
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.xml.generator.compiler.runtime;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.lang.PackageLoader;
import org.safris.commons.lang.Paths;
import org.safris.commons.net.URLs;
import org.safris.commons.xml.NamespaceBinding;
import org.safris.commons.xml.validator.ValidatorError;

import com.sun.org.apache.xerces.internal.impl.xs.XSDDescription;
import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;

public final class BindingEntityResolver implements XMLEntityResolver {
  public static void registerSchemaLocation(final String namespaceURI, final URL schemaReference) {
    final URL present = schemaReferences.get(namespaceURI);
    if (present != null) {
      if (!present.equals(schemaReference))
        throw new ValidatorError("We should not be resetting {" + namespaceURI + "} from " + present + " to " + schemaReference);
      
      return;
    }

    schemaReferences.put(namespaceURI, schemaReference);
  }

  public static URL lookupSchemaLocation(final String namespaceURI) {
    if (namespaceURI == null)
      return null;

    final URL schemaReference = schemaReferences.get(namespaceURI);
    if (schemaReference != null)
      return schemaReference;
    
    // The schemaReference may not have been registered yet
    synchronized (namespaceURI) {
      // When loading the classes, the static block of each binding will call the
      // registerSchemaLocation() function.
      // FIXME: Look this over. Also make a dedicated RuntimeException for this.
      if (!schemaReferences.containsKey(namespaceURI)) {
        try {
          PackageLoader.getSystemPackageLoader().loadPackage(NamespaceBinding.getPackageFromNamespace(namespaceURI));
        }
        catch (final Exception e) {
          throw new RuntimeException(e);
        }
      }
    }

    return schemaReferences.get(namespaceURI);
  }

  private static final Map<String,URL> schemaReferences = new HashMap<String,URL>();

  public XMLInputSource resolveEntity(final XMLResourceIdentifier resourceIdentifier) throws XNIException, IOException {
    if (resourceIdentifier == null)
      return null;

    final String namespaceURI = resourceIdentifier.getNamespace();
    String baseId = resourceIdentifier.getBaseSystemId();
    if (baseId == null)
      baseId = resourceIdentifier.getExpandedSystemId();

    // for some reason, this happens every once in a while
    if (namespaceURI == null && baseId == null)
      return null;

    try {
      final URL schemaReference;
      if (((XSDDescription)resourceIdentifier).getContextType() == XSDDescription.CONTEXT_INCLUDE) {
        final String localName = Paths.getName(resourceIdentifier.getExpandedSystemId());
        schemaReference = new URL(Paths.getParent(baseId) + "/" + localName);
      }
      else {
        schemaReference = lookupSchemaLocation(namespaceURI);
      }

      if (schemaReference == null)
        throw new ValidatorError("The schemaReference for " + resourceIdentifier + " is null!");

      final String expandedSystemId;
      try {
        expandedSystemId = URLs.toExternalForm(schemaReference);
      }
      catch (final MalformedURLException e) {
        final IOException ioException = new IOException("Cannot obtain externalForm of " + schemaReference);
        ioException.initCause(e);
        throw ioException;
      }

      resourceIdentifier.setExpandedSystemId(expandedSystemId);
      resourceIdentifier.setLiteralSystemId(expandedSystemId);

      final XMLInputSource inputSource = new XMLInputSource(resourceIdentifier);
      inputSource.setByteStream(schemaReference.openStream());
      return inputSource;
    }
    catch (final IOException e) {
      throw e;
    }
    catch (final Exception e) {
      throw new ValidatorError(resourceIdentifier.toString(), e);
    }
  }
}