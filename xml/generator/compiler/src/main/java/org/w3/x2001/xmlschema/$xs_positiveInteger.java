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

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class $xs_positiveInteger<T extends BindingType> extends $xs_integer<T> {
  public $xs_positiveInteger(final $xs_positiveInteger<T> binding) {
    super(binding);
  }

  public $xs_positiveInteger(final Integer value) {
    super(value);
  }

  protected $xs_positiveInteger() {
    super();
  }

  public Integer getText() {
    return (Integer)super.getText();
  }

  public void setText(final Integer text) {
    super.setText(text);
  }

  protected void _$$decode(final Element parent, final String value) throws ParseException {
    super.setText(Integer.parseInt(value));
  }

  protected String _$$encode(final Element parent) throws MarshalException {
    if (super.getText() == null)
      return "";

    return super.getText().toString();
  }

  public $xs_positiveInteger<T> clone() {
    return new $xs_positiveInteger<T>(this) {
      protected $xs_positiveInteger<T> inherits() {
        return this;
      }
    };
  }
}