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

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class SourceFormat {
  public static SourceFormat getDefaultFormat() {
    return new SourceFormat();
  }

  private List<FormatModule> modules = new LinkedList<FormatModule>();

  private SourceFormat() {
    addModule(new PackageModule());
    addModule(new DocumentationModule());
    addModule(new ImportModule());
    addModule(new MethodModule());
    addModule(new FieldModule());
    addModule(new ClassModule());
    addModule(new OpenBracketModule());
    addModule(new CloseBracketModule());
    addModule(new DeclarationModule());
    addModule(new StatementModule());
  }

  public void addModule(FormatModule module) {
    modules.add(module);
  }

  public String format(String unformated) {
    if (unformated == null)
      return "";

    FormatModule.restetDepth();

    String formated = "";
    try {
      StringTokenizer stringTokenizer = new StringTokenizer(unformated, "\t\n\r\f");
      String token = null;
      while (stringTokenizer.hasMoreTokens()) {
        token = stringTokenizer.nextToken();
        formated = modules(formated, token);
      }
    }
    catch (Exception e) {
      throw new FormatError(e);
    }

    return formated;
  }

  private String modules(String formated, String token) {
    String formatedToken = token;
    for (int i = 0; i < modules.size(); i++) {
      FormatModule module = modules.get(i);
      token = module.format(formated, token);

      /*          if(FormatModule.getLastModule() instanceof OpenBracketModule && !formated.endsWith("\n") && !token.startsWith("\n"))
       {
       for(int j = 0; j < module.getDepth(); j++)
       {
       token = "\t" + token;
       }
       token = "\n" + token;
       }*/

      if (!formatedToken.equals(token)) {
        FormatModule.setLastModule(module);
        break;
      }

      formatedToken = token;
    }

    return formated += token;
  }
}
