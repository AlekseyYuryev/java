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

package org.safris.xml.generator.lexer.processor.model.element;

import java.util.LinkedHashSet;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class RedefineModel extends Model {
  private final LinkedHashSet<SimpleTypeModel> simpleTypeModels = new LinkedHashSet<SimpleTypeModel>();
  private final LinkedHashSet<ComplexTypeModel> complexTypeModels = new LinkedHashSet<ComplexTypeModel>();
  private final LinkedHashSet<GroupModel> groupModels = new LinkedHashSet<GroupModel>();
  private final LinkedHashSet<AttributeGroupModel> attributeGroupModels = new LinkedHashSet<AttributeGroupModel>();
  private String schemaLocation = null;

  protected RedefineModel(Node node, Model parent) {
    super(node, parent);
    final NamedNodeMap attributes = node.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      final Node attribute = attributes.item(i);
      if ("schemaLocation".equals(attribute.getLocalName()))
        schemaLocation = attribute.getNodeValue();
    }
  }

  public final String getSchemaLocation() {
    return schemaLocation;
  }

  public final LinkedHashSet<SimpleTypeModel> getSimpleTypeModels() {
    return simpleTypeModels;
  }

  public final LinkedHashSet<ComplexTypeModel> getComplexTypeModels() {
    return complexTypeModels;
  }

  public final LinkedHashSet<GroupModel> getGroupModels() {
    return groupModels;
  }

  public final LinkedHashSet<AttributeGroupModel> getAttributeGroupModels() {
    return attributeGroupModels;
  }
}
