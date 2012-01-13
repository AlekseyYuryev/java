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
import org.safris.xml.generator.lexer.schema.attribute.ProcessContents;

public final class ProcessContents {
  private static final Map<String,ProcessContents> enums = new HashMap<String,ProcessContents>();

  public static final ProcessContents LAX = new ProcessContents("lax");
  public static final ProcessContents SKIP = new ProcessContents("skip");
  public static final ProcessContents STRICT = new ProcessContents("strict");

  public static ProcessContents parseProcessContents(String value) {
    return enums.get(value);
  }

  private final String value;

  private ProcessContents(String value) {
    this.value = value;
    enums.put(value, this);
  }

  public String getValue() {
    return value;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof ProcessContents))
      return false;

    return getValue().equals(((ProcessContents)obj).getValue());
  }

  public int hashCode() {
    return (getClass().getName() + toString()).hashCode();
  }

  public String toString() {
    return getValue();
  }
}
