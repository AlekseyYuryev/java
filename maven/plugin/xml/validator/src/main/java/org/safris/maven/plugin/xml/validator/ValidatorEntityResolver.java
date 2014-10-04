/* Copyright (c) 2008 Seva Safris
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
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