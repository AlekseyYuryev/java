/* Copyright (c) 2006 Seva Safris
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.commons.formatter;

public final class StatementModule extends FormatModule {
  String format(final String formated, String token) {
    if (token.trim().lastIndexOf(";") == token.trim().length() - 1 || token.trim().lastIndexOf(")") == token.trim().length() - 1 || (token.trim().indexOf("else") == 0 && token.trim().indexOf("(") == -1)) {
      if (token.trim().indexOf("else") == 0) {
        for (int i = 0; i < getDepth(); i++)
          token = "\t" + token;

        token = "\n" + token;
      }
      else if (token.trim().lastIndexOf("try") == token.trim().length() - 1 || token.trim().indexOf("catch") == 0 || token.trim().lastIndexOf("finally") == token.trim().length() - 1 || token.trim().lastIndexOf("do") == token.trim().length() - 1 || token.trim().indexOf("if") == 0 || token.trim().indexOf("for") == 0 || token.trim().indexOf("while") == 0 || token.trim().lastIndexOf(";") == token.trim().length() - 1) {
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