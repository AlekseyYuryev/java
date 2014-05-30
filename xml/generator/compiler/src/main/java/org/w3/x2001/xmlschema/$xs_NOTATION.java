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
import org.safris.xml.generator.compiler.runtime.NotationType;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class $xs_NOTATION extends $xs_anySimpleType {
  public $xs_NOTATION(final $xs_NOTATION binding) {
    super(binding);
  }

  public $xs_NOTATION(final NotationType value) {
    super(value);
  }

  protected $xs_NOTATION() {
    super();
  }

  public NotationType text() {
    return (NotationType)super.text();
  }

  public void text(final NotationType text) {
    super.text(text);
  }

  protected void _$$decode(final Element parent, final String value) throws ParseException {
    super.text(NotationType.parseNotation(value));
    if (super.text() == null)
      throw new ParseException("Notation \"" + value + "\" is not registered. The code that instantiates the Notation binding for \"" + value + "\" must be run before it is possible for the Binding engine to have to know about it.");
  }

  protected String _$$encode(final Element parent) throws MarshalException {
    return super.text() != null ? super.text().toString() : "";
  }

  public $xs_NOTATION clone() {
    return new $xs_NOTATION(this) {
      protected $xs_NOTATION inherits() {
        return this;
      }
    };
  }
}