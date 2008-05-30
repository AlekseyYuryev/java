/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.xml.generator.compiler.runtime;

import com.sun.org.apache.xerces.internal.impl.xs.XSDDescription;
import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
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

public class BindingEntityResolver implements XMLEntityResolver
{
	public static void registerSchemaLocation(String namespaceURI, URL schemaReference)
	{
		final URL present = schemaReferences.get(namespaceURI);
		if(present != null && !present.equals(schemaReference))
			throw new ValidatorError("We should not be resetting {" + namespaceURI + "} from " + present + " to " + schemaReference);

		schemaReferences.put(namespaceURI, schemaReference);
	}

	public static URL lookupSchemaLocation(String namespaceURI)
	{
		if(namespaceURI == null)
			return null;

		URL schemaReference = schemaReferences.get(namespaceURI);
		if(schemaReference == null)
		{
			// The schemaReference may not have been registered yet
			synchronized(namespaceURI)
			{
				// When loading the classes, the static block of each binding will call the
				// registerSchemaLocation() function.
				// FIXME: Look this over. Also make a dedicated RuntimeException for this.
				if(!schemaReferences.containsKey(namespaceURI))
				{
					try
					{
						PackageLoader.getSystemPackageLoader().loadPackage(NamespaceBinding.getPackageFromNamespace(namespaceURI));
					}
					catch(Exception e)
					{
						throw new RuntimeException(e);
					}
				}
			}

			schemaReference = schemaReferences.get(namespaceURI);
		}

		return schemaReference;
	}

	private static final Map<String,URL> schemaReferences = new HashMap<String,URL>();

	public XMLInputSource resolveEntity(XMLResourceIdentifier resourceIdentifier) throws XNIException, IOException
	{
		if(resourceIdentifier == null)
			return null;

		final String namespaceURI = resourceIdentifier.getNamespace();
		String baseId = resourceIdentifier.getBaseSystemId();
		if(baseId == null)
			baseId = resourceIdentifier.getExpandedSystemId();

		// for some reason, this happens every once in a while
		if(namespaceURI == null && baseId == null)
			return null;

		try
		{
			final URL schemaReference;
			if(((XSDDescription)resourceIdentifier).getContextType() == XSDDescription.CONTEXT_INCLUDE)
			{
				final String localName = Paths.getName(resourceIdentifier.getExpandedSystemId());
				schemaReference = new URL(Paths.getParent(baseId) + "/" + localName);
			}
			else
				schemaReference = lookupSchemaLocation(namespaceURI);

			if(schemaReference == null)
				throw new ValidatorError("The schemaReference for " + resourceIdentifier + " is null!");

			final String expandedSystemId;
			try
			{
				expandedSystemId = URLs.toExternalForm(schemaReference);
			}
			catch(MalformedURLException e)
			{
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
		catch(IOException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new ValidatorError(resourceIdentifier.toString(), e);
		}
	}
}
