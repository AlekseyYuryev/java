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

package org.safris.xml.generator.lexer.processor.document;

import java.net.URL;
import java.util.Collection;

import org.safris.commons.pipeline.PipelineEntity;
import org.safris.xml.generator.lexer.processor.reference.SchemaReference;
import org.w3c.dom.Document;

public final class SchemaDocument implements PipelineEntity {
  private final SchemaReference schemaReference;
  private final Document document;
  private Collection<URL> includes;

  public SchemaDocument(final SchemaReference schemaReference, final Document document) {
    this.schemaReference = schemaReference;
    this.document = document;
  }

  public SchemaReference getSchemaReference() {
    return schemaReference;
  }

  public Document getDocument() {
    return document;
  }

  public Collection<URL> getIncludes() {
    return includes;
  }

  public void setIncludes(final Collection<URL> includes) {
    this.includes = includes;
  }

  public boolean equals(final Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof SchemaDocument))
      return false;

    final SchemaDocument that = (SchemaDocument)obj;
    return schemaReference != null ? schemaReference.equals(that.schemaReference) : that.schemaReference == null;
  }

  public int hashCode() {
    return (schemaReference != null ? schemaReference.hashCode() : -1) + (document != null ? 1 : -1);
  }
}