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

package org.safris.xml.generator.lexer.processor.reference;

import java.net.URL;
import org.safris.commons.logging.Logger;
import org.safris.commons.xml.NamespaceURI;
import org.safris.xml.generator.lexer.lang.LexerLoggerName;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SchemaNamespaceHandler extends DefaultHandler {
  private static final Logger logger = Logger.getLogger(LexerLoggerName.REFERENCE);
  private final URL schemaUrl;

  public SchemaNamespaceHandler(URL schemaUrl) {
    this.schemaUrl = schemaUrl;
  }

  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (!UniqueQName.XS.getNamespaceURI().toString().equals(uri) || !"schema".equals(localName))
      return;

    final int index = attributes.getIndex("targetNamespace");
    final NamespaceURI namespaceURI = NamespaceURI.getInstance(index != -1 ? attributes.getValue(index) : "");

    String prefix = null;
    for (int i = 0; i < attributes.getLength(); i++) {
      final String name = attributes.getQName(i);
      if (name.startsWith("xmlns:") && namespaceURI.toString().equals(attributes.getValue(i)))
        prefix = name.substring(6);
    }

    if (prefix == null)
      prefix = "";

    throw new SAXException(schemaUrl.hashCode() + "\"" + namespaceURI + "\"" + prefix);
  }
}
