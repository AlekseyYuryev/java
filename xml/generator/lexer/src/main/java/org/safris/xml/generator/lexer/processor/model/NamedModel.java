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

package org.safris.xml.generator.lexer.processor.model;

import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.element.RestrictionModel;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class NamedModel extends Model implements Nameable<Model> {
  private UniqueQName name = null;

  protected NamedModel(Node node, Model parent) {
    super(node, parent);
    if (node == null)
      return;

    final NamedNodeMap attributes = node.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      final Node attribute = attributes.item(i);
      if ("name".equals(attribute.getLocalName()))
        name = UniqueQName.getInstance(getTargetNamespace(), attribute.getNodeValue());
    }
  }

  protected final void setName(UniqueQName name) {
    this.name = name;
  }

  public UniqueQName getName() {
    return name;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (!(getClass().isInstance(obj)))
      return false;

    final NamedModel that = (NamedModel)obj;
    return name != null ? name.equals(that.name) : that.name == null;
  }

  // FIXME: This is dirty!!
  public static UniqueQName getNameOfRestrictionBase(NamedModel model) {
    if (model == null)
      return null;

    for (final Model child : model.getChildren()) {
      if (!(child instanceof RestrictionModel))
        continue;

      return ((RestrictionModel)child).getBase().getName();
    }

    return null;
  }

  public int hashCode() {
    UniqueQName name = this.name;
    if (name == null)
      name = getNameOfRestrictionBase(this);

    return 3 * (name != null ? name.hashCode() : -1);
  }

  public String toString() {
    UniqueQName name = this.name;
    if (name == null)
      name = getNameOfRestrictionBase(this);

    if (name == null)
      return super.toString();

    return super.toString() + name.toString();
  }
}
