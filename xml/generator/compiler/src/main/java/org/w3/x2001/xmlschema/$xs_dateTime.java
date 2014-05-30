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

import org.safris.commons.xml.binding.DateTime;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

/**
 * This final class represents the Java binding of the dateTime instance of time.
 *
 * @see <a href="http://www.w3.org/TR/xmlschema-2/#dateTime">Definition</a>
 */
public abstract class $xs_dateTime extends $xs_anySimpleType {
  public $xs_dateTime(final $xs_dateTime binding) {
    super(binding);
  }

  public $xs_dateTime(final DateTime value) {
    super(value);
  }

  /**
   * Allocates a <code>Date</code> object and initializes it so that it
   * represents the time at which it was allocated. Milliseconds are
   * <b>NOT</b> significant figures and are not represented.
   *
   * @see java.lang.System#currentTimeMillis()
   */
  protected $xs_dateTime() {
    super();
  }

  public DateTime text() {
    return (DateTime)super.text();
  }

  public void text(final DateTime text) {
    super.text(text);
  }

  protected void _$$decode(final Element parent, final String value) throws ParseException {
    super.text(DateTime.parseDateTime(value));
  }

  protected String _$$encode(final Element parent) throws MarshalException {
    return super.text() != null ? super.text().toString() : "";
  }

  public $xs_dateTime clone() {
    return new $xs_dateTime(this) {
      protected $xs_dateTime inherits() {
        return this;
      }
    };
  }
}