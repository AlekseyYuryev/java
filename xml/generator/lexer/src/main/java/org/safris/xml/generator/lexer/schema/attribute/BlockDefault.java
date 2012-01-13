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
import org.safris.xml.generator.lexer.schema.attribute.BlockDefault;

public final class BlockDefault {
  private static final Map<String,BlockDefault> enums = new HashMap<String,BlockDefault>();

  public static final BlockDefault ALL = new BlockDefault("#all");
  public static final BlockDefault EXTENSION = new BlockDefault("extension");
  public static final BlockDefault RESTRICTION = new BlockDefault("restriction");
  public static final BlockDefault SUBSTITUTION = new BlockDefault("substitution");

  public static BlockDefault parseBlockDefault(String value) {
    return enums.get(value);
  }

  private final String value;

  private BlockDefault(String value) {
    this.value = value;
    enums.put(value, this);
  }

  public String getValue() {
    return value;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof BlockDefault))
      return false;

    return getValue().equals(((BlockDefault)obj).getValue());
  }

  public int hashCode() {
    return (getClass().getName() + toString()).hashCode();
  }

  public String toString() {
    return getValue();
  }
}
