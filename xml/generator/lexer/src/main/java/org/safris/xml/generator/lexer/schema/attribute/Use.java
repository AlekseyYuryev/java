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

package org.safris.xml.generator.lexer.schema.attribute;

import java.util.HashMap;
import java.util.Map;

public final class Use {
  private static final Map<String,Use> enums = new HashMap<String,Use>();

  public static final Use OPTIONAL = new Use("optional");
  public static final Use PROHIBITED = new Use("prohibited");
  public static final Use REQUIRED = new Use("required");

  public static Use parseUse(final String value) {
    return enums.get(value);
  }

  private final String value;

  private Use(final String value) {
    this.value = value;
    enums.put(value, this);
  }

  public String getValue() {
    return value;
  }

  public boolean equals(final Object obj) {
    if (!(obj instanceof Use))
      return false;

    return getValue().equals(((Use)obj).getValue());
  }

  public int hashCode() {
    return (getClass().getName() + toString()).hashCode();
  }

  public String toString() {
    return getValue();
  }
}