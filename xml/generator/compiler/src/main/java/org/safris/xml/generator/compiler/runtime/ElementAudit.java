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

import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;

public final class ElementAudit<T extends Binding> {
  private final Binding parent;
  private final List<T> _default;
  private final QName name;
  private final QName typeName;
  private final boolean qualified;
  private final boolean nillable;
  private final int minOccurs;
  private final int maxOccurs;
  private SpecificElementList<T> value = null;

  public ElementAudit(Binding parent, T _default, QName name, QName typeName, boolean qualified, boolean nillable, int minOccurs, int maxOccurs) {
    this.parent = parent;
    this._default = Collections.<T>singletonList(_default);
    this.name = name;
    this.typeName = typeName;
    this.qualified = qualified;
    this.nillable = nillable;
    this.minOccurs = minOccurs;
    this.maxOccurs = maxOccurs;
  }

  protected Binding getParent() {
    return parent;
  }

  public boolean isQualified() {
    return qualified;
  }

  public List<T> getDefault() {
    return _default;
  }

  public QName getName() {
    return name;
  }

  public QName getTypeName() {
    return typeName;
  }

  public boolean isNillable() {
    return nillable;
  }

  public int getMinOccurs() {
    return minOccurs;
  }

  public int getMaxOccurs() {
    return maxOccurs;
  }

  public boolean addElement(T element) {
    if (this.value == null)
      this.value = new SpecificElementList<T>(this, 2);

    return this.value.add(element, false);
  }

  public List<T> getElements() {
    return value;
  }

  protected void reset() {
    value = null;
  }

  private void marshalNil(Element element, Element parent) {
    // NOTE: This makes the assumption that the xmlns:xsi will be present if
    // NOTE: xsi:nil is present, saving us a hasAttributeNS() call.
    if (!element.hasAttributeNS(Binding.XSI_NIL.getNamespaceURI(), Binding.XSI_NIL.getLocalPart())) {
      element.setAttributeNS(Binding.XSI_NIL.getNamespaceURI(), Binding.XSI_NIL.getPrefix() + ":" + Binding.XSI_NIL.getLocalPart(), "true");
      if (!parent.getOwnerDocument().getDocumentElement().hasAttributeNS(Binding.XMLNS.getNamespaceURI(), Binding.XSI_NIL.getPrefix()))
        parent.getOwnerDocument().getDocumentElement().setAttributeNS(Binding.XMLNS.getNamespaceURI(), Binding.XMLNS.getLocalPart() + ":" + Binding.XSI_NIL.getPrefix(), Binding.XSI_NIL.getNamespaceURI());
    }
  }

  protected void marshal(Element parent, T element) throws MarshalException {
    if (value == null)
      return;

    QName name = getName();
    if (name == null)
      name = element._$$getName();

    final Element node = element.marshal(parent, name, getTypeName());
    if (!element._$$hasElements() && isNillable())
      marshalNil(node, parent);

    element._$$marshalElements(node);
    if (!isQualified())
      node.setPrefix(null);

    parent.appendChild(node);
  }

  public boolean equals(Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof ElementAudit))
      return false;

    return value != null ? value.equals(((ElementAudit)obj).value) : ((ElementAudit)obj).value == null;
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
