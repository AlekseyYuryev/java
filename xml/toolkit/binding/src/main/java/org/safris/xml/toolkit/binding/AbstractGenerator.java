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

package org.safris.xml.toolkit.binding;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import org.safris.commons.net.URLs;
import org.safris.commons.xml.dom.DOMParsers;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.runtime.BindingError;
import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.xml.generator.lexer.processor.reference.SchemaReference;
import org.w3c.dom.Document;

public abstract class AbstractGenerator {
    private static final Map<String,SchemaDocument> parsedDocuments = new HashMap<String,SchemaDocument>();

    public static SchemaDocument parse(SchemaReference schemaReference) {
        URL url = null;
        SchemaDocument parsedDocument = null;
        Document document = null;
        try {
            url = URLs.canonicalizeURL(schemaReference.getURL());
            parsedDocuments.get(schemaReference.getNamespaceURI() + url.toString());
            if (parsedDocument != null)
                return parsedDocument;

            final DocumentBuilder documentBuilder = DOMParsers.newDocumentBuilder();
            document = documentBuilder.parse(url.toString());
        }
        catch (FileNotFoundException e) {
            throw new BindingError(e.getMessage());
        }
        catch (Exception e) {
            throw new CompilerError(e);
        }

        parsedDocument = new SchemaDocument(schemaReference, document);
        parsedDocuments.put(schemaReference.getNamespaceURI() + url.toString(), parsedDocument);
        return parsedDocument;
    }
}
