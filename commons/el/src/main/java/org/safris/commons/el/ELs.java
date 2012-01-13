/*  Copyright Safris Software 2008
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

package org.safris.commons.el;

import java.util.Map;

public final class ELs {
  public static String dereference(String string, Map<String,String> variables) throws ExpressionFormatException {
    if (string == null || string.length() == 0 || variables == null)
      return string;

    final StringBuffer buffer = new StringBuffer();
    int i = -1;
    int j = -1;
    while (true) {
      i = string.indexOf("${", i);
      if (i == -1)
        break;

      j = string.indexOf("}", i + 2);
      if (j == -1)
        throw new ExpressionFormatException("There is an error in your expression: " + string);

      final String token = string.substring(i + 2, j);
      final String variable = variables.get(token);
      buffer.append(string.substring(0, i)).append(variable);
      string = string.substring(j + 1);
    }

    buffer.append(string);

    return buffer.toString();
  }

  private ELs() {
  }
}
