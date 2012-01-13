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

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Referenceable;
import org.safris.xml.generator.lexer.processor.model.AttributableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.NamedModel;
import org.safris.xml.generator.lexer.processor.model.RedefineableModel;
import org.safris.xml.generator.lexer.processor.model.ReferableModel;
import org.safris.xml.generator.lexer.processor.model.element.AttributeModel;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class AttributeGroupModel extends NamedModel implements AttributableModel, RedefineableModel<AttributeGroupModel>, ReferableModel<AttributeGroupModel> {
  private final LinkedHashSet<AttributeModel> attributes = new LinkedHashSet<AttributeModel>();
  private AttributeGroupModel ref = null;
  private AttributeGroupModel redefine = null;

  protected AttributeGroupModel(Node node, Model parent) {
    super(node, parent);
    if (node == null)
      return;

    final NamedNodeMap attributes = node.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      final Node attribute = attributes.item(i);
      if ("ref".equals(attribute.getLocalName()))
        ref = AttributeGroupModel.Reference.parseAttributeGroup(UniqueQName.getInstance(parseQNameValue(attribute.getNodeValue(), node)));
    }
  }

  public final void setRedefine(AttributeGroupModel redefine) {
    this.redefine = redefine;
  }

  public final AttributeGroupModel getRedefine() {
    return redefine;
  }

  public final void addAttribute(AttributeModel attribute) {
    attributes.add(attribute);
  }

  public final void addAllAttributes(Collection<AttributeModel> attributes) {
    attributes.addAll(attributes);
  }

  public final LinkedHashSet<AttributeModel> getAttributes() {
    return attributes;
  }

  public final void setRef(AttributeGroupModel ref) {
    this.ref = ref;
  }

  public final AttributeGroupModel getRef() {
    return ref;
  }

  public String toString() {
    return super.toString().replace(TO_STRING_DELIMITER, "ref=\"" + ref + "\"");
  }

  public static class Reference extends AttributeGroupModel implements Referenceable {
    private static final Map<UniqueQName,Reference> all = new HashMap<UniqueQName,Reference>();

    protected Reference(Model parent) {
      super(null, parent);
    }

    public static Reference parseAttributeGroup(UniqueQName name) {
      Reference type = all.get(name);
      if (type != null)
        return type;

      type = new Reference(null);
      type.setName(name);
      Reference.all.put(name, type);
      return type;
    }
  }
}
