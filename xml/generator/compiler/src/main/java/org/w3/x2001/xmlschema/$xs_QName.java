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

import javax.xml.namespace.QName;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class $xs_QName<T extends BindingType> extends $xs_anySimpleType<T> {
  public $xs_QName(final $xs_QName<T> binding) {
    super(binding);
  }

  public $xs_QName(final QName value) {
    super(value);
  }

  protected $xs_QName() {
    super();
  }

  public QName getText() {
    return (QName)super.getText();
  }

  public void setText(final QName text) {
    super.setText(text);
  }

  protected void _$$decode(final Element element, final String value) throws ParseException {
    final QName temp = stringToQName(value);
    super.setText(temp);
    if (element != null)
      super.setText(new javax.xml.namespace.QName(element.getOwnerDocument().getDocumentElement().lookupNamespaceURI(temp.getPrefix()), temp.getLocalPart()));
  }

  protected String _$$encode(final Element parent) throws MarshalException {
    final QName value = (QName)super.getText();
    if (parent != null && value.getNamespaceURI() != null)
      return _$$getPrefix(parent, value) + ":" + value.getLocalPart();

    return value.getLocalPart();
  }

  public $xs_QName clone() {
    return new $xs_QName(this) {
      protected $xs_QName inherits() {
        return this;
      }
    };
  }
}
