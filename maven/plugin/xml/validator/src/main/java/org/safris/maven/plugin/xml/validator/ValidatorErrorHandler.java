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

import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class ValidatorErrorHandler implements ErrorHandler {
  private static final Map<Log,ValidatorErrorHandler> instances = new HashMap<Log,ValidatorErrorHandler>();
  private static final Object lock = new Object();
  private static ValidatorErrorHandler instance = null;

  public static ValidatorErrorHandler getInstance(final Log log) {
    if (log == null)
      return getInstance();

    ValidatorErrorHandler instance = instances.get(log);
    if (instance != null)
      return instance;

    synchronized (instances) {
      instance = instances.get(log);
      if (instance != null)
        return instance;

      instances.put(log, instance = new ValidatorErrorHandler(log));
    }

    return instance;
  }

  public static ValidatorErrorHandler getInstance() {
    if (instance != null)
      return instance;

    synchronized (lock) {
      if (instance != null)
        return instance;

      instance = new ValidatorErrorHandler();
    }

    return instance;
  }

  private final Log log;

  // ignore fatal errors (final an exception is guaranteed)
  public void fatalError(final SAXParseException e) throws SAXException {
  }

  // treat validation errors as fatal
  public void error(final SAXParseException e) throws SAXParseException {
    throw e;
  }

  // dump warnings too
  public void warning(final SAXParseException e) throws SAXParseException {
    if (e.getMessage() != null && e.getMessage().startsWith("schema_reference.4"))
      throw e;

    if (log != null)
      log.warn(e.getMessage());
    else
      System.err.println("[WARNING] " + e.getMessage());
  }

  public ValidatorErrorHandler(final Log log) {
    this.log = log;
  }

  public ValidatorErrorHandler() {
    this.log = null;
  }
}