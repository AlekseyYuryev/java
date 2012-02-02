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

import java.util.Arrays;
import java.util.Collection;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ListModel extends SimpleTypeModel {
  private SimpleTypeModel itemType = null;
  private UnionModel unionType = null;

  protected ListModel(Node node, Model parent) {
    super(node, parent);
    final NamedNodeMap attributes = node.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      final Node attribute = attributes.item(i);
      if ("itemType".equals(attribute.getLocalName()))
        setItemType(SimpleTypeModel.Reference.parseSimpleType(UniqueQName.getInstance(parseQNameValue(attribute.getNodeValue(), node))));
    }
  }

  public final void setItemType(SimpleTypeModel itemType) {
    this.itemType = itemType;
  }

  public final void setItemType(UnionModel unionType) {
    this.unionType = unionType;
  }

  public final Collection<SimpleTypeModel> getItemType() {
    if (unionType != null)
      return unionType.getNormalizedMemberTypes();

    if (itemType != null)
      return Arrays.<SimpleTypeModel>asList(itemType);

    return null;
  }
}
