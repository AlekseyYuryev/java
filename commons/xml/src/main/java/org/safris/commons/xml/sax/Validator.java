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

package org.safris.commons.xml.sax;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.safris.commons.net.CachedURL;
import org.safris.commons.net.URLs;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class Validator {
  private static final SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
  private static final ThreadLocal<javax.xml.validation.Validator> localValidator = new ThreadLocal<javax.xml.validation.Validator>();

  public static void validate(final File file, final boolean offline) throws IOException, SAXException {
    validate(file, offline, new LoggingErrorHandler());
  }

  public static void validate(final File file, final boolean offline, final ErrorHandler errorHandler) throws IOException, SAXException {
    final XMLDocument xmlDocument = XMLDocuments.analyze(file);
    final Map<String,SchemaLocation> schemaReferences = xmlDocument.getSchemaReferences();
    if (schemaReferences.isEmpty() && !xmlDocument.isXSD()) {
      errorHandler.warning(new SAXParseException("There is no schema or DTD associated with the document.", URLs.toExternalForm(file.toURI().toURL()), null, 0, 0));
      return;
    }

    final ValidationHandler handler = new ValidationHandler(schemaReferences, errorHandler);
    javax.xml.validation.Validator validator = localValidator.get();
    if (validator == null)
      localValidator.set(validator = factory.newSchema().newValidator());

    validator.setResourceResolver(handler);
    validator.setErrorHandler(handler);

    validator.validate(new StreamSource(file));
    for (final Map.Entry<String,SchemaLocation> schemaLocation : schemaReferences.entrySet()) {
      final Map<String,CachedURL> locations = schemaLocation.getValue().getLocation();
      for (final Map.Entry<String,CachedURL> location : locations.entrySet())
        location.getValue().destroy();
    }

    if (handler.getErrors() != null) {
      final Iterator<SAXParseException> iterator = handler.getErrors().iterator();
      final SAXException exception = new SAXException(iterator.next());
      while (iterator.hasNext())
        exception.addSuppressed(iterator.next());

      throw exception;
    }
  }

  private Validator() {
  }
}