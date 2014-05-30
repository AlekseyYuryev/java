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

public final class MethodModule extends FormatModule {
  String format(final String formated, String token) {
    if (token.trim().lastIndexOf(";") != token.trim().length() - 1) {
      if (token.trim().indexOf("else") == -1 && token.trim().indexOf("else ") == -1 && ((token.trim().indexOf("static") == 0 && token.trim().length() == 6) || (token.trim().indexOf(" ") != -1 && token.trim().indexOf(" ") < token.trim().indexOf("(") - 1 && (token.trim().lastIndexOf(")") == token.length() - 1 || token.trim().lastIndexOf(")") < token.trim().lastIndexOf("throws"))))) {
        for (int i = 0; i < getDepth(); i++)
          token = "\t" + token;

        if (getLastModule() instanceof CloseBracketModule || getLastModule() instanceof FieldModule)
          token = "\n\n" + token;
        else
          token = "\n" + token;
      }
    }

    return token;
  }
}