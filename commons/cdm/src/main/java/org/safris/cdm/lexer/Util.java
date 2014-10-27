/* Copyright (c) 2014 Seva Safris
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

package org.safris.cdm.lexer;

import java.util.Arrays;

public final class Util {
  public static String pad(final int length) {
    final char[] chars = new char[length];
    Arrays.fill(chars, ' ');
    return String.valueOf(chars);
  }

  public static int binarySearch(final int[] sorted, final char key, int index) {
    int low = 0;
    int high = sorted.length - 1;

    while (low <= high) {
      int mid = (low + high) >>> 1;
      final String name = Lexer.Keyword.values()[sorted[mid]].lcname;
      char midVal = index < name.length() ? name.charAt(index) : ' ';

      if (midVal < key)
        low = mid + 1;
      else if (midVal > key)
        high = mid - 1;
      else
        return mid; // key found
    }

    return -(low + 1); // key not found.
  }

  public static String print(final int[] keywords) {
    String out = "";
    for (final int keyword : keywords) {
      final Lexer.Keyword kw = Lexer.Keyword.values()[keyword];
      out += "\n" + kw.lcname;
      for (int i = 0; i < kw.children.length; i++)
        if (kw.children[i] != null)
          for (final int child : kw.children[i])
            out += "\n " + Util.pad(i) + Lexer.Keyword.values()[child].lcname.substring(i + 1);
    }

    return out.substring(1);
  }

  private Util() {
  }
}