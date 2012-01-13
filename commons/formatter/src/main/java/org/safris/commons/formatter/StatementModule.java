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

public class StatementModule extends FormatModule {
  String format(String formated, String token) {
    if (token.trim().lastIndexOf(";") == token.trim().length() - 1 || token.trim().lastIndexOf(")") == token.trim().length() - 1 || (token.trim().indexOf("else") == 0 && token.trim().indexOf("(") == -1)) {
      if (token.trim().indexOf("else") == 0) {
        for (int i = 0; i < getDepth(); i++) {
          token = "\t" + token;
        }

        token = "\n" + token;
      }
      else if (token.trim().lastIndexOf("try") == token.trim().length() - 1 || token.trim().indexOf("catch") == 0 || token.trim().lastIndexOf("finally") == token.trim().length() - 1 || token.trim().lastIndexOf("do") == token.trim().length() - 1 || token.trim().indexOf("if") == 0 || token.trim().indexOf("for") == 0 || token.trim().indexOf("while") == 0 || token.trim().lastIndexOf(";") == token.trim().length() - 1) {
        for (int i = 0; i < getDepth(); i++) {
          token = "\t" + token;
        }

        if (getLastModule() instanceof CloseBracketModule || getLastModule() instanceof FieldModule) {
          token = "\n\n" + token;
        }
        else {
          token = "\n" + token;
        }
      }
    }

    return token;
  }
}
