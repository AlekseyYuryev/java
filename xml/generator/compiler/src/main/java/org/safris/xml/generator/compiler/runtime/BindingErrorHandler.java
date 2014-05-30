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

import org.safris.commons.logging.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class BindingErrorHandler implements ErrorHandler {
  private final Logger logger = Logger.getLogger(RuntimeLoggerName.VALIDATOR);
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