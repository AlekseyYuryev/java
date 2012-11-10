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

package org.w3.x2001.xmlschema;

import java.util.Collection;
import javax.xml.namespace.QName;
import org.safris.commons.xml.validator.ValidationException;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public abstract class $xs_anySimpleType<T extends BindingType> extends Binding<T> {
  private Object text = null;

  private $xs_anySimpleType(final $xs_anySimpleType<T> copy) {
    super(copy);
    this.text = copy.text;
  }

  public $xs_anySimpleType(final Object text) {
    super();
    if (text instanceof $xs_anySimpleType)
      merge(($xs_anySimpleType)text);
    else
      this.text = text;
  }

  protected $xs_anySimpleType() {
    super();
  }

  protected void setText(final Object text) {
    this.text = text;
  }

  protected Object getText() {
    return text;
  }

  protected void _$$decode(final Element parent, final String value) throws ParseException {
    this.text = value;
  }

  protected String _$$encode(final Element parent) throws MarshalException {
    // FIXME: Is it right to return "" for a null getText()?
    if (getText() == null)
      return "";

    if (getText() instanceof Collection)
      throw new Error("Why is this a Collection? The collection logic should be in the appropriate subclass.");

    return getText().toString();
  }

  private transient Element parent = null;

  protected Element marshal(final Element parent, final QName name, final QName typeName) throws MarshalException {
    this.parent = parent;
    final Element element = super.marshal(parent, name, typeName);
    if (text != null)
      element.appendChild(parent.getOwnerDocument().createTextNode(String.valueOf(_$$encode(parent))));

    return element;
  }

  protected Attr marshalAttr(final String name, final Element parent) throws MarshalException {
    this.parent = parent;
    final Attr attr = parent.getOwnerDocument().createAttribute(name);
    attr.setNodeValue(String.valueOf(_$$encode(parent)));
    return attr;
  }

  protected void parseText(final Text text) throws ParseException, ValidationException {
    // Ignore all attributes that have a xsi prefix because these are
    // controlled implicitly by the framework
    if (XSI_NIL.getPrefix().equals(text.getPrefix()))
      return;

    final StringBuffer value = new StringBuffer("");
    if (text.getNodeValue() != null)
      value.append(text.getNodeValue().trim());

    if (value.length() == 0)
      return;

    _$$decode((Element)text.getParentNode(), value.toString());
  }

  protected QName _$$getName() {
    return _$$getName(inherits());
  }

  protected QName _$$getTypeName() {
    return null;
  }

  public $xs_anySimpleType clone() {
    return new $xs_anySimpleType(this) {
      protected $xs_anySimpleType inherits() {
        return this;
      }
    };
  }

  public boolean equals(final Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof $xs_anySimpleType))
      return false;

    final $xs_anySimpleType that = ($xs_anySimpleType)obj;
    try {
      final String thisEncoded = _$$encode(parent);
      final String thatEncoded = that._$$encode(parent);
      return thisEncoded != null ? thisEncoded.equals(thatEncoded) : thatEncoded == null;
    }
    catch (MarshalException e) {
      return false;
    }
  }

  public int hashCode() {
    final String value;
    try {
      value = _$$encode(parent);
    }
    catch (MarshalException e) {
      return super.hashCode();
    }

    if (value == null)
      return super.hashCode();

    return value.hashCode();
  }

  public String toString() {
    try {
      return String.valueOf(_$$encode(parent));
    }
    catch (MarshalException e) {
      return super.toString();
    }
  }
}
