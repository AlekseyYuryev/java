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

public abstract class $xs_unsignedLong<T extends BindingType> extends $xs_decimal<T> {
  public $xs_unsignedLong($xs_unsignedLong<T> binding) {
    super(binding);
  }

  public $xs_unsignedLong(final Long value) {
    super(value);
  }

  protected $xs_unsignedLong(final Number value) {
    super(value);
  }

  protected $xs_unsignedLong() {
    super();
  }

  public Long getText() {
    return (Long)super.getText();
  }

  protected void setText(final Long text) {
    super.setText(text);
  }

  protected void _$$decode(final Element parent, final String value) throws ParseException {
    super.setText(Long.parseLong(value));
  }

  protected String _$$encode(final Element parent) throws MarshalException {
    if (super.getText() == null)
      return "";

    return super.getText().toString();
  }

  public $xs_unsignedLong<T> clone() {
    return new $xs_unsignedLong<T>(this) {
      protected $xs_unsignedLong<T> inherits() {
        return this;
      }
    };
  }
}