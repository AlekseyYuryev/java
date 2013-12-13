/*  Copyright Safris Software 2006
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

package org.safris.commons.xml.dom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class DOMParsers {
  private static final ErrorHandler defaultErrorHandler = new ErrorHandler()
  {
    // ignore fatal errors (an exception is guaranteed)
    public void fatalError(final SAXParseException exception) throws SAXException {
    }

    // treat validation errors as fatal
    public void error(final SAXParseException e) throws SAXParseException {
      final String systemId = e.getSystemId() != null ? " systemId=\"" + e.getSystemId() + "\"" : "";
      System.err.println("ERROR [" + e.getLineNumber() + "," + e.getColumnNumber() + "]" + systemId);
      throw e;
    }

    // dump warnings too
    public void warning(final SAXParseException e) throws SAXParseException {
      final String message = e.getMessage() != null ? " " + e.getMessage() : "";
      System.err.println("WARNING [" + e.getLineNumber() + "," + e.getColumnNumber() + "] systemId=\"" + e.getSystemId() + "\"" + message);
    }
  };

  public static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
    final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    documentBuilderFactory.setNamespaceAware(true);
    documentBuilderFactory.setIgnoringComments(true);
    documentBuilderFactory.setIgnoringElementContentWhitespace(true);
    documentBuilderFactory.setValidating(false);

    final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    documentBuilder.setErrorHandler(defaultErrorHandler);

    return documentBuilder;
  }

  private DOMParsers() {
  }
}