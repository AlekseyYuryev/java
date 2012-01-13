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

public class DeclarationModule extends FormatModule {
  String format(String formated, String token) {
    if (token.trim().lastIndexOf(";") == token.trim().length() - 1) {
      if (token.indexOf("package") == -1 && token.indexOf("import") == -1 && token.indexOf("public") == -1 && token.indexOf("protected") == -1 && token.indexOf("private") == -1) {
        for (int i = 0; i < getDepth(); i++)
          token = "\t" + token;

        if (getLastModule() instanceof CloseBracketModule)
          token = "\n\n" + token;
        else if (getLastModule() instanceof StatementModule)
          token = "\n\t" + token + "\n";
        else
          token = "\n" + token;
      }
    }

    return token;
  }
}
