/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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

        int index = attributes.getIndex("targetNamespace");
        if (index == -1) {
            logger.severe("Schema in file " + schemaUrl + " does not define a targetNamespace.");
            System.exit(1);
        }

        final NamespaceURI namespaceURI = NamespaceURI.getInstance(attributes.getValue(index));
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
