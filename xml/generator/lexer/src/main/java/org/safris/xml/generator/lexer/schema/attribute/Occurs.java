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

import org.safris.xml.generator.lexer.schema.attribute.Occurs;

public final class Occurs {
  public static final Occurs UNBOUNDED = new Occurs(Integer.MAX_VALUE);

  public static Occurs parseOccurs(String value) {
    if ("unbounded".equals(value))
      return UNBOUNDED;

    return new Occurs(Integer.parseInt(value));
  }

  private int value = 1;

  private Occurs(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public boolean equals(Object o) {
    if (!(o instanceof Occurs))
      return false;

    return getValue() == ((Occurs)o).getValue();
  }

  public int hashCode() {
    return (getClass().getName() + toString()).hashCode();
  }

  public String toString() {
    return String.valueOf(value);
  }
}
