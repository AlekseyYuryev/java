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

import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class $xs_int extends $xs_anySimpleType {
  public $xs_int(final $xs_int binding) {
    super(binding);
  }

  public $xs_int(final Integer value) {
    super(value);
  }

  protected $xs_int(final Number value) {
    super(value);
  }

  protected $xs_int() {
    super();
  }

  public Integer text() {
    return (Integer)super.text();
  }

  public void text(final Integer text) {
    super.text(text);
  }

  protected void _$$decode(final Element parent, final String value) throws ParseException {
    super.text(Integer.parseInt(String.valueOf(value)));
  }

  protected String _$$encode(final Element parent) throws MarshalException {
    return super.text() != null ? super.text().toString() : "";
  }

  public $xs_int clone() {
    return new $xs_int(this) {
      protected $xs_int inherits() {
        return this;
      }
    };
  }
}