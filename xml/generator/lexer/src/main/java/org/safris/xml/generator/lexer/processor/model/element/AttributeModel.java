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

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Formable;
import org.safris.xml.generator.lexer.processor.Referenceable;
import org.safris.xml.generator.lexer.processor.model.AliasModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.ReferableModel;
import org.safris.xml.generator.lexer.processor.model.RestrictableModel;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.safris.xml.generator.lexer.schema.attribute.Use;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class AttributeModel extends SimpleTypeModel<SimpleTypeModel<?>> implements Formable<Model>, ReferableModel<AttributeModel>, RestrictableModel<AttributeModel> {
  private QName _default = null;
  private QName fixed = null;
  private Form form = null;
  private Use use = Use.OPTIONAL;
  private Form formDefault = null;
  private AttributeModel ref = null;
  private AliasModel restrictionOwner = null;
  private AttributeModel restriction = null;

  public AttributeModel(final Node node, final Model parent) {
    super(node, parent);
    if (node == null)
      return;

    final NamedNodeMap attributes = node.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      final Node attribute = attributes.item(i);
      if ("default".equals(attribute.getLocalName()))
        _default = parseQNameValue(attribute.getNodeValue(), node);
      else if ("fixed".equals(attribute.getLocalName()))
        fixed = parseQNameValue(attribute.getNodeValue(), node);
      else if ("form".equals(attribute.getLocalName()))
        form = Form.parseForm(attribute.getNodeValue());
      else if ("ref".equals(attribute.getLocalName()))
        setRef(AttributeModel.Reference.parseAttribute(UniqueQName.getInstance(parseQNameValue(attribute.getNodeValue(), node))));
      else if ("type".equals(attribute.getLocalName()))
        setSuperType(SimpleTypeModel.Reference.parseSimpleType(UniqueQName.getInstance(parseQNameValue(attribute.getNodeValue(), node))));
      else if ("use".equals(attribute.getLocalName()))
        use = Use.parseUse(attribute.getNodeValue());
    }
  }

  public final void setRestriction(final AttributeModel restriction) {
    this.restriction = restriction;
  }

  public final AttributeModel getRestriction() {
    return restriction;
  }

  public final AliasModel getRestrictionOwner() {
    return restrictionOwner;
  }

  public final void setRestrictionOwner(final AliasModel restrictionOwner) {
    this.restrictionOwner = restrictionOwner;
  }

  public final AttributeModel getRef() {
    return ref;
  }

  public final void setRef(final AttributeModel ref) {
    this.ref = ref;
  }

  public final UniqueQName getName() {
    return ref != null ? ref.getName() : super.getName();
  }

  public final SimpleTypeModel<?> getSuperType() {
    return ref != null ? ref.getSuperType() : super.getSuperType();
  }

  public final QName getDefault() {
    return _default;
  }

  public final QName getFixed() {
    return fixed;
  }

  public final Form getForm() {
    return form;
  }

  public final Use getUse() {
    return use;
  }

  public final void setFormDefault(final Form formDefault) {
    this.formDefault = formDefault;
  }

  public final Form getFormDefault() {
    return formDefault;
  }

  public boolean equals(final Object obj) {
    final boolean equals = super.equals(obj);
    if (!equals)
      return false;

    final AttributeModel that = (AttributeModel)obj;
    return (getRef() == null && that.getRef() == null) || (getRef() != null && getRef().equals(that.getRef()));
  }

  public int hashCode() {
    return (getClass().getName() + toString()).hashCode();
  }

  public String toString() {
    if (getName() == null) {
      if (getRef() == null)
        return null;

      return getRef().toString();
    }

    return getName().toString();
  }

  public static final class Reference extends AttributeModel implements Referenceable {
    private static final Map<UniqueQName,Reference> all = new HashMap<UniqueQName,Reference>();

    protected Reference(final Model parent) {
      super(null, parent);
    }

    public static Reference parseAttribute(final UniqueQName name) {
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