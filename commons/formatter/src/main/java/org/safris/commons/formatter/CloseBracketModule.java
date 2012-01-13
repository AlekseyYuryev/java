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

package org.safris.commons.formatter;

public class CloseBracketModule extends FormatModule {
  String format(String formated, String token) {
    if (token.trim().lastIndexOf(";") != token.trim().length() - 1 || "};".equals(token.trim())) {
      if (token.trim().indexOf("}") == 0) {
        decreaseDepth();
        for (int i = 0; i < getDepth(); i++)
          token = "\t" + token;

        if (!formated.endsWith("\n"))
          token = "\n" + token;
      }
    }

    return token;
  }
}
