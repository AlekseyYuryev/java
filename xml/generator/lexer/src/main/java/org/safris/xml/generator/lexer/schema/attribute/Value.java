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

public final class Value {
  private static final Map<String,Value> enums = new HashMap<String,Value>();

  public static final Value COLLAPSE = new Value("collapse");
  public static final Value PRESERVE = new Value("preserve");
  public static final Value REPLACE = new Value("replace");

  public static Value parseValue(final String value) {
    return enums.get(value);
  }

  private final String value;

  private Value(final String value) {
    this.value = value;
    enums.put(value, this);
  }

  public String getValue() {
    return value;
  }

  public boolean equals(final Object obj) {
    if (!(obj instanceof Value))
      return false;

    return getValue().equals(((Value)obj).getValue());
  }

  public int hashCode() {
    return (getClass().getName() + toString()).hashCode();
  }

  public String toString() {
    return getValue();
  }
}