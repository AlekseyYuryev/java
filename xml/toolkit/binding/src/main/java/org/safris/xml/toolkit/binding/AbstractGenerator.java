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

  public static SchemaDocument parse(final SchemaReference schemaReference) {
    URL url = null;
    SchemaDocument parsedDocument = null;
    Document document = null;
    try {
      url = URLs.canonicalizeURL(schemaReference.getURL());
      final DocumentBuilder documentBuilder = DOMParsers.newDocumentBuilder();
      document = documentBuilder.parse(url.toString());
    }
    catch (final FileNotFoundException e) {
      throw new BindingError(e.getMessage());
    }
    catch (final Exception e) {
      throw new CompilerError(e);
    }

    parsedDocument = new SchemaDocument(schemaReference, document);
    parsedDocuments.put(schemaReference.getNamespaceURI() + url.toString(), parsedDocument);
    return parsedDocument;
  }
}