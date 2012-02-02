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

package org.safris.xml.generator.compiler.runtime;

import java.util.Collection;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;

public class AttributeAudit<T> {
  private final T _default;
  private final QName name;
  private final boolean qualified;
  private final boolean required;
  private T value = null;

  public AttributeAudit(T _default, QName name, boolean qualified, boolean required) {
    this._default = _default;
    this.name = name;
    this.qualified = qualified;
    this.required = required;
  }

  public T getDefault() {
    return _default;
  }

  public QName getName() {
    return name;
  }

  public boolean isQualified() {
    return qualified;
  }

  public boolean isRequired() {
    return required;
  }

  public boolean setAttribute(T value) {
    this.value = value;
    return true;
  }

  public T getAttribute() {
    return value != null ? value : getDefault();
  }

  public void marshal(Element parent) throws MarshalException {
    Object value = getAttribute();
    if (value == null) {
      if (getDefault() == null)
        return;

      if (!isRequired())
        return;

      value = getDefault();
    }

    if (value instanceof Collection) {
      String name = null;
      if (getName() != null) {
        if (isQualified())
          name = Binding._$$getPrefix(parent, getName()) + ":" + getName().getLocalPart();
        else
          name = getName().getLocalPart();
      }

      for (Object object : (Collection)value) {
        Binding binding = (Binding)object;
        if (name == null) {
          QName actualName = Binding._$$getName(binding);
          if (isQualified())
            name = Binding._$$getPrefix(parent, getName()) + ":" + getName().getLocalPart();
          else
            name = actualName.getLocalPart();
        }

        parent.setAttributeNodeNS(binding.marshalAttr(name, parent));
      }
    }
    else {
      QName name = getName();
      if (name == null)
        name = ((Binding)value)._$$getName();

      String marshalName = null;
      if (isQualified())
        marshalName = Binding._$$getPrefix(parent, name) + ":" + name.getLocalPart();
      else
        marshalName = name.getLocalPart();

      parent.setAttributeNodeNS(((Binding)value).marshalAttr(marshalName, parent));
    }
  }

  public boolean equals(Object obj) {
    if (obj != null)
      return obj.equals(value);

    if (value == null)
      return true;

    return false;
  }

  public int hashCode() {
    if (value == null)
      return 0;

    return value.hashCode();
  }

  public String toString() {
    if (value == null)
      return super.toString();

    return value.toString();
  }
}
