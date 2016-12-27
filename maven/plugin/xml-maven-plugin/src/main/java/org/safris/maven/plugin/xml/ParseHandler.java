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

package org.safris.maven.plugin.xml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.safris.commons.lang.Paths;
import org.safris.commons.net.URLConnections;
import org.safris.commons.net.URLs;
import org.safris.commons.xml.validator.OfflineValidationException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DefaultHandler2;

public final class ParseHandler extends DefaultHandler2 {
  private final File basedir;
  private final boolean offline;
  private final ErrorHandler errorHandler;
  private List<SAXParseException> errors;

  public ParseHandler(final File basedir, final boolean offline, final ErrorHandler errorHandler) {
    this.basedir = basedir;
    this.offline = offline;
    this.errorHandler = errorHandler;
  }

  public List<SAXParseException> getErrors() {
    return this.errors;
  }

  @Override
  public InputSource resolveEntity(final String name, final String publicId, final String baseURI, final String systemId) throws SAXException, IOException {
    final URL url;
    if (URLs.isAbsolute(systemId)) {
      url = URLs.makeUrlFromPath(systemId);
    }
    else {
      final String parentBaseId = baseURI != null ? Paths.getParent(baseURI) : basedir.getAbsolutePath();
      url = URLs.makeUrlFromPath(parentBaseId, systemId);
    }

    if (offline && !URLs.isLocal(url))
      throw new OfflineValidationException(url.toExternalForm());

    final InputSource inputSource = new InputSource(URLConnections.checkOpenRedirectStream(url));
    inputSource.setPublicId(publicId);
    inputSource.setSystemId(systemId);
    return inputSource;
  }

  @Override
  public void warning(final SAXParseException exception) throws SAXException {
    if (errorHandler != null)
      errorHandler.warning(exception);
  }

  @Override
  public void error(final SAXParseException exception) throws SAXException {
    if (errors == null)
      errors = new ArrayList<SAXParseException>();

    errors.add(exception);

    if (errorHandler != null)
      errorHandler.error(exception);
  }

  @Override
  public void fatalError(final SAXParseException exception) throws SAXException {
    if (errorHandler != null)
      errorHandler.fatalError(exception);
  }
}