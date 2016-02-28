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

import org.safris.commons.maven.Log;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class ValidatorErrorHandler implements ErrorHandler {
  private static final Object lock = new Object();
  private static ValidatorErrorHandler instance = null;

  public static ValidatorErrorHandler instance() {
    if (instance != null)
      return instance;

    synchronized (lock) {
      if (instance != null)
        return instance;

      instance = new ValidatorErrorHandler();
    }

    return instance;
  }

  // ignore fatal errors (final an exception is guaranteed)
  @Override
  public void fatalError(final SAXParseException e) throws SAXException {
  }

  // treat validation errors as fatal
  @Override
  public void error(final SAXParseException e) throws SAXParseException {
    throw e;
  }

  // dump warnings too
  @Override
  public void warning(final SAXParseException e) throws SAXParseException {
    if (e.getMessage() != null && e.getMessage().startsWith("schema_reference.4"))
      throw e;

    Log.warn(e.getMessage());
  }
}