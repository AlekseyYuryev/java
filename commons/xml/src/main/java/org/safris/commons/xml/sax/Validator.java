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

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class Validator {
  private static SAXParserFactory factory;

  static {
    try {
      factory = SAXParserFactory.newInstance("org.apache.xerces.jaxp.SAXParserFactoryImpl", null);
    }
    catch (final FactoryConfigurationError e) {
      factory = SAXParserFactory.newInstance();
    }
  }

  private static SAXParser newParser() throws SAXException {
    factory.setNamespaceAware(true);
    factory.setValidating(true);
    try {
      try {
        factory.setSchema(SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1").newSchema());
      }
      catch (final IllegalArgumentException e) {
      }

      factory.setFeature("http://xml.org/sax/features/validation", true);
      factory.setFeature("http://apache.org/xml/features/validation/schema", true);
      factory.setFeature("http://apache.org/xml/features/validation/dynamic", true);
      factory.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
      factory.setFeature("http://apache.org/xml/features/honour-all-schemaLocations", true);
      factory.setFeature("http://apache.org/xml/features/continue-after-fatal-error", true);

      return factory.newSAXParser();
    }
    catch (final ParserConfigurationException e) {
      throw new SAXException(e);
    }
  }

  public static void validate(final File file, final boolean offline) throws IOException, SAXException {
    validate(file, offline, new LoggingErrorHandler());
  }

  public static void validate(final File file, final boolean offline, final ErrorHandler errorHandler) throws IOException, SAXException {
    final ParseHandler parseHandler = new ParseHandler(file.getParentFile(), offline, errorHandler);
    newParser().parse(file, parseHandler);
    if (parseHandler.getErrors() != null) {
      final Iterator<SAXParseException> iterator = parseHandler.getErrors().iterator();
      final SAXParseException firstException = iterator.next();
      final SAXException exception = new SAXException(firstException.getMessage(), firstException);
      while (iterator.hasNext())
        exception.addSuppressed(iterator.next());

      throw exception;
    }
  }

  private Validator() {
  }
}