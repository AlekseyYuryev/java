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

public abstract class $xs_negativeInteger extends $xs_positiveInteger {
  public $xs_negativeInteger(final $xs_negativeInteger binding) {
    super(binding);
  }

  public $xs_negativeInteger(final Integer value) {
    super(value);
  }

  protected $xs_negativeInteger() {
    super();
  }

  public Integer text() {
    return super.text();
  }

  public void text(final Integer text) {
    super.text(text);
  }

  protected void _$$decode(final Element parent, final String value) throws ParseException {
    super.text(Integer.parseInt(value));
  }

  protected String _$$encode(final Element parent) throws MarshalException {
    return super.text() != null ? super.text().toString() : "";
  }

  public $xs_negativeInteger clone() {
    return new $xs_negativeInteger(this) {
      protected $xs_negativeInteger inherits() {
        return this;
      }
    };
  }
}