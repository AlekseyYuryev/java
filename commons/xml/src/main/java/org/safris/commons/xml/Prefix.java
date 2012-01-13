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

package org.safris.commons.xml;

import java.util.HashMap;
import java.util.Map;

public final class Prefix {
  private static final Map<String,Prefix> instances = new HashMap<String,Prefix>();

  public static Prefix getInstance(String prefix) {
    Prefix value = instances.get(prefix);
    if (value == null)
      instances.put(prefix, value = new Prefix(prefix));

    return value;
  }

  public static final Prefix EMPTY = new Prefix("");

  private final String prefix;
  private final String title;
  private final String lower;

  private Prefix(String prefix) {
    this.prefix = prefix;
    if (prefix.equals(prefix.toUpperCase())) {
      title = prefix;
      lower = prefix.toLowerCase();
    }
    else if (64 < prefix.charAt(0) && prefix.charAt(0) < 91) {
      title = prefix;
      lower = title.substring(0, 1).toLowerCase() + title.substring(1);
    }
    else {
      title = prefix.substring(0, 1).toUpperCase() + prefix.substring(1);
      lower = prefix;
    }
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof Prefix))
      return false;

    return prefix.equals(((Prefix)obj).prefix);
  }

  public int hashCode() {
    return prefix.hashCode();
  }

  public String toString() {
    return prefix;
  }

//  public String toStringTitleCase()
//  {
//      return title;
//  }
//
//  public String toStringLowerCase()
//  {
//      return lower;
//  }
}
