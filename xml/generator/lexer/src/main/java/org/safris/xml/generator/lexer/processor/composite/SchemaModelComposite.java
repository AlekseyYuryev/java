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

package org.safris.xml.generator.lexer.processor.composite;

import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;

public final class SchemaModelComposite implements SchemaComposite {
  private final SchemaDocument schemaDocument;
  private SchemaModel schemaModel = null;

  public SchemaModelComposite(final SchemaDocument schemaDocument) {
    this.schemaDocument = schemaDocument;
  }

  public SchemaDocument getSchemaDocument() {
    return schemaDocument;
  }

  public void setSchemaModel(final SchemaModel schemaModel) {
    this.schemaModel = schemaModel;
  }

  public SchemaModel getSchemaModel() {
    return schemaModel;
  }
  
  public boolean equals(final Object obj) {
    if (obj == this)
      return true;
    
    if (!(obj instanceof SchemaModelComposite))
      return false;
    
    final SchemaModelComposite that = (SchemaModelComposite)obj;
    return (schemaDocument != null ? schemaDocument.equals(that.schemaDocument) : that.schemaDocument == null) && (schemaModel != null ? schemaModel.equals(that.schemaModel) : that.schemaModel == null);
  }
  
  public int hashCode() {
    return (schemaDocument != null ? schemaDocument.hashCode() : -7) * (schemaModel != null ? schemaModel.hashCode() : -3);
  }
}