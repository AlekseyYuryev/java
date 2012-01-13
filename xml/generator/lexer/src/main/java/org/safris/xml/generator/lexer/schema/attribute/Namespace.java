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
import org.safris.xml.generator.lexer.schema.attribute.Namespace;

public final class Namespace {
  private static final Map<String,Namespace> enums = new HashMap<String,Namespace>();

  public static final Namespace ANY = new Namespace("##any");
  public static final Namespace LOCAL = new Namespace("##local");
  public static final Namespace OTHER = new Namespace("##other");
  public static final Namespace TARGETNAMESPACE = new Namespace("##targetNamespace");

  public static Namespace parseNamespace(String value) {
    final Namespace namespace = enums.get(value);
    if (namespace != null)
      return namespace;

    return new Namespace(value);
  }

  private final String value;

  public Namespace(String value) {
    this.value = value;
    enums.put(value, this);
  }

  public String getValue() {
    return value;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof Namespace))
      return false;

    return getValue().equals(((Namespace)obj).getValue());
  }

  public int hashCode() {
    return (getClass().getName() + toString()).hashCode();
  }

  public String toString() {
    return getValue();
  }
}
