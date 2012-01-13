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

public final class FinalDefault {
  private static final Map<String,FinalDefault> enums = new HashMap<String,FinalDefault>();

  public static final FinalDefault ALL = new FinalDefault("#all");
  public static final FinalDefault EXTENSION = new FinalDefault("extension");
  public static final FinalDefault RESTRICTION = new FinalDefault("restriction");
  public static final FinalDefault SUBSTITUTION = new FinalDefault("substitution");

  public static FinalDefault parseFinalDefault(String value) {
    return enums.get(value);
  }

  private String value;

  private FinalDefault(String value) {
    this.value = value;
    enums.put(value, this);
  }

  public String getValue() {
    return value;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof FinalDefault))
      return false;

    return getValue().equals(((FinalDefault)obj).getValue());
  }

  public int hashCode() {
    return (getClass().getName() + toString()).hashCode();
  }

  public String toString() {
    return getValue();
  }
}
