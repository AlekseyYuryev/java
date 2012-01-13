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
import org.safris.xml.generator.lexer.schema.attribute.Form;

public final class Form {
  private static final Map<String,Form> enums = new HashMap<String,Form>();

  public static final Form QUALIFIED = new Form("qualified");
  public static final Form UNQUALIFIED = new Form("unqualified");

  public static Form parseForm(String value) {
    return enums.get(value);
  }

  private final String value;

  private Form(String value) {
    this.value = value;
    enums.put(value, this);
  }

  public String getValue() {
    return value;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof Form))
      return false;

    return getValue().equals(((Form)obj).getValue());
  }

  public int hashCode() {
    return getValue().hashCode();
  }

  public String toString() {
    return getValue();
  }
}
