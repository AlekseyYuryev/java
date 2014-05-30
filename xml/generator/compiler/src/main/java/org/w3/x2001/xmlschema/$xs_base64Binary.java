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

import org.safris.commons.xml.binding.Base64Binary;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class $xs_base64Binary extends $xs_anySimpleType {
  public $xs_base64Binary(final $xs_base64Binary binding) {
    super(binding);
  }

  public $xs_base64Binary(final Base64Binary value) {
    super(value);
  }

  protected $xs_base64Binary() {
    super();
  }

  public Base64Binary text() {
    return (Base64Binary)super.text();
  }

  public void text(final Base64Binary text) {
    super.text(text);
  }

  protected void _$$decode(final Element parent, final String value) throws ParseException {
    super.text(Base64Binary.parseBase64Binary(String.valueOf(value)));
  }

  protected String _$$encode(final Element parent) throws MarshalException {
    return super.text() != null ? super.text().toString() : "";
  }

  public $xs_base64Binary clone() {
    return new $xs_base64Binary(this) {
      protected $xs_base64Binary inherits() {
        return this;
      }
    };
  }
}