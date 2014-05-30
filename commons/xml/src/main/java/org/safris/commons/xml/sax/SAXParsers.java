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

package org.safris.commons.xml.sax;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;

public final class SAXParsers {
  public static SAXParser createParser() throws SAXException {
    // Create a specific SAXParser so that it works with our XMLSchemaResolver
    return new SAXParser(XMLReaderFactory.createXMLReader(com.sun.org.apache.xerces.internal.parsers.SAXParser.class.getName()));
  }

  private SAXParsers() {
  }
}