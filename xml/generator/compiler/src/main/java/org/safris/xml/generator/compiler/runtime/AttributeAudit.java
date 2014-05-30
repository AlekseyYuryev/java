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

import org.w3.x2001.xmlschema.$xs_anySimpleType;
import org.w3c.dom.Element;

public final class AttributeAudit<T> {
  private final $xs_anySimpleType parent;
  private final T _default;
  private final QName name;
  private final boolean qualified;
  private final boolean required;
  private T value = null;

  public AttributeAudit(final $xs_anySimpleType parent, final T _default, final QName name, final boolean qualified, final boolean required) {
    this.parent = parent;
    this._default = _default;
    this.name = name;
    this.qualified = qualified;
    this.required = required;
  }

  public AttributeAudit(final AttributeAudit<T> copy) {
    this.parent = copy.parent;
    this._default = copy._default;
    this.name = copy.name;
    this.qualified = copy.qualified;
    this.required = copy.required;
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

  public boolean setAttribute(final T value) {
    if (parent.isNull())
      throw new BindingRuntimeException("NULL Object is immutable.");
    
    this.value = value;
    return true;
  }

  public T getAttribute() {
    return value != null ? value : getDefault();
  }

  public void marshal(final Element parent) throws MarshalException {
    final Object value = getAttribute();
    if (value == null || value.equals(getDefault()))
        return;

    if (value instanceof Collection) {
      String name = null;
      if (getName() != null)
        name = isQualified() ? Binding._$$getPrefix(parent, getName()) + ":" + getName().getLocalPart() : getName().getLocalPart();

      for (final Object object : (Collection<?>)value) {
        Binding binding = (Binding)object;
        if (name == null) {
          final QName actualName = Binding.name(binding);
          name = isQualified() ? Binding._$$getPrefix(parent, getName()) + ":" + getName().getLocalPart() : actualName.getLocalPart();
        }

        parent.setAttributeNodeNS(binding.marshalAttr(name, parent));
      }
    }
    else {
      QName name = getName();
      if (name == null)
        name = ((Binding)value).name();

      final String marshalName = isQualified() ? Binding._$$getPrefix(parent, name) + ":" + name.getLocalPart() :  name.getLocalPart();
      parent.setAttributeNodeNS(((Binding)value).marshalAttr(marshalName, parent));
    }
  }

  public AttributeAudit<T> clone() {
    return new AttributeAudit<T>(this);
  }

  public boolean equals(final Object obj) {
    return obj != null ? obj.equals(value) : value == null;
  }

  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }

  public String toString() {
    return value != null ? value.toString() : super.toString();
  }
}