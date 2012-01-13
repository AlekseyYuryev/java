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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.StringTokenizer;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class UnionModel extends Model {
  private final Collection<SimpleTypeModel> memberTypes = new HashSet<SimpleTypeModel>();
  private final Collection<UnionModel> unions = new HashSet<UnionModel>();

  protected UnionModel(Node node, Model parent) {
    super(node, parent);
    final NamedNodeMap attributes = node.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      final Node attribute = attributes.item(i);
      if ("memberTypes".equals(attribute.getLocalName()))
        parseMemberTypes(attribute.getNodeValue(), node);
    }
  }

  private final void parseMemberTypes(String memberTypes, Node node) {
    final StringTokenizer tokenizer = new StringTokenizer(memberTypes);
    while (tokenizer.hasMoreTokens())
      this.memberTypes.add(SimpleTypeModel.Reference.parseSimpleType(UniqueQName.getInstance(parseQNameValue(tokenizer.nextToken(), node))));
  }

  public final Collection<SimpleTypeModel> getMemberTypes() {
    return memberTypes;
  }

  public final void addUnion(UnionModel unionModel) {
    unions.add(unionModel);
  }

  public final Collection<SimpleTypeModel> getNormalizedMemberTypes() {
    final Collection<SimpleTypeModel> allMemberTypes = new ArrayList<SimpleTypeModel>(getMemberTypes());
    for (UnionModel union : unions)
      allMemberTypes.addAll(union.getNormalizedMemberTypes());

    return allMemberTypes;
  }
}
