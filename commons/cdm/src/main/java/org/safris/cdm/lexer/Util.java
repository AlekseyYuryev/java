package org.safris.cdm.lexer;

import java.util.Arrays;

public final class Util {
  public static String pad(final int length) {
    final char[] chars = new char[length];
    Arrays.fill(chars, ' ');
    return String.valueOf(chars);
  }

  public static int binarySearch(final int[] sorted, final char key, final int from, final int to, int index) {
    int low = from;
    int high = to - 1;

    while (low <= high) {
      int mid = (low + high) >>> 1;
      final String name = KeywordLexer.KEYWORDS[sorted[mid]].lcname;
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
      final KeywordLexer.Keyword kw = KeywordLexer.KEYWORDS[keyword];
      out += "\n" + kw.lcname;
      for (int i = 0; i < kw.children.length; i++)
        if (kw.children[i] != null)
          for (final int child : kw.children[i])
            out += "\n " + Util.pad(i) + KeywordLexer.KEYWORDS[child].lcname.substring(i + 1);
    }
    
    return out.substring(1);
  }

  private Util() {
  }
}