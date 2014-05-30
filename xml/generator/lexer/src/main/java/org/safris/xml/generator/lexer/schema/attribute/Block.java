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

public final class Block {
  private static final Map<String,Block> enums = new HashMap<String,Block>();

  public static final Block ALL = new Block("#all");
  public static final Block EXTENSION = new Block("extension");
  public static final Block RESTRICTION = new Block("restriction");
  public static final Block SUBSTITUTION = new Block("substitution");

  public static Block parseBlock(final String value) {
    return enums.get(value);
  }

  private final String value;

  private Block(final String value) {
    this.value = value;
    enums.put(value, this);
  }

  public String getValue() {
    return value;
  }

  public boolean equals(final Object obj) {
    if (!(obj instanceof Block))
      return false;

    return getValue().equals(((Block)obj).getValue());
  }

  public int hashCode() {
    return (getClass().getName() + toString()).hashCode();
  }

  public String toString() {
    return getValue();
  }
}