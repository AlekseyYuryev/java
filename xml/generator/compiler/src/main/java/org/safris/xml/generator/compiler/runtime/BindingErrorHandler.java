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

package org.safris.xml.generator.compiler.runtime;

import java.util.logging.Logger;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class BindingErrorHandler implements ErrorHandler {
  private final Logger logger = Logger.getLogger(BindingErrorHandler.class.getName());
  private static final BindingErrorHandler instance = new BindingErrorHandler();

  public static BindingErrorHandler getInstance() {
    return instance;
  }

  // ignore fatal errors (final an exception is guaranteed)
  public void fatalError(final SAXParseException e) throws SAXException {
  }

  // treat validation errors as fatal
  public void error(final SAXParseException e) throws SAXParseException {
    final String systemId = e.getSystemId() != null ? " systemId=\"" + e.getSystemId() + "\"" : "";
    logger.severe("[" + e.getLineNumber() + "," + e.getColumnNumber() + "]" + systemId);
    throw e;
  }

  // dump warnings too
  public void warning(final SAXParseException e) throws SAXParseException {
    final String message = e.getMessage() != null ? " " + e.getMessage() : "";
    logger.warning("[" + e.getLineNumber() + "," + e.getColumnNumber() + "] systemId=\"" + e.getSystemId() + "\"" + message);
  }

  private BindingErrorHandler() {
  }
}