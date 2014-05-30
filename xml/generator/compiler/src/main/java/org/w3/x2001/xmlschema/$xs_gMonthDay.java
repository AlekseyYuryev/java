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

import org.safris.commons.xml.binding.MonthDay;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class $xs_gMonthDay extends $xs_anySimpleType {
  public $xs_gMonthDay(final $xs_gMonthDay binding) {
    super(binding);
  }

  public $xs_gMonthDay(final MonthDay value) {
    super(value);
  }

  protected $xs_gMonthDay() {
    super();
  }

  public MonthDay text() {
    return (MonthDay)super.text();
  }

  public void text(final MonthDay text) {
    super.text(text);
  }

  protected void _$$decode(final Element parent, final String value) throws ParseException {
    super.text(MonthDay.parseMonthDay(value));
  }

  protected String _$$encode(final Element parent) throws MarshalException {
    return super.text() != null ? super.text().toString() : "";
  }

  public $xs_gMonthDay clone() {
    return new $xs_gMonthDay(this) {
      protected $xs_gMonthDay inherits() {
        return this;
      }
    };
  }
}