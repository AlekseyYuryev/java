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

package org.safris.maven.plugin.xml.validator;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.safris.commons.lang.Paths;
import org.safris.commons.net.URLs;

import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;

public final class ValidatorEntityResolver implements XMLEntityResolver {
  private final File basedir;

  public ValidatorEntityResolver(final File basedir) {
    this.basedir = basedir;
  }

  public XMLInputSource resolveEntity(final XMLResourceIdentifier resourceIdentifier) throws XNIException, IOException {
    final String systemId = resourceIdentifier.getLiteralSystemId();
    if (systemId == null)
      return null;

    final URL url;
    if (!URLs.isAbsolute(systemId)) {
      final String parentBaseId;
      if (resourceIdentifier.getBaseSystemId() != null)
        parentBaseId = Paths.getParent(resourceIdentifier.getBaseSystemId());
      else
        parentBaseId = basedir.getAbsolutePath();

      url = URLs.makeUrlFromPath(parentBaseId, systemId);
    }
    else {
      url = URLs.makeUrlFromPath(systemId);
    }

    if (resourceIdentifier.getExpandedSystemId() != null && !resourceIdentifier.getExpandedSystemId().equals(resourceIdentifier.getLiteralSystemId()))
      resourceIdentifier.setLiteralSystemId(url.getPath());

    final XMLInputSource inputSource = new XMLInputSource(resourceIdentifier);
    inputSource.setByteStream(url.openStream());
    return inputSource;
  }
}