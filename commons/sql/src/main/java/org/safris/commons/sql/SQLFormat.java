package org.safris.commons.sql;

import java.util.Arrays;
import java.util.StringTokenizer;

import org.safris.commons.lang.Strings;

public final class SQLFormat {
  private static final String[] reserveds = new String[] {"ALL", "AND", "BY", "DISTINCT", "FROM", "GROUP", "HAVING", "JOIN", "LEFT", "ON", "OR", "ORDER", "OUTER", "SELECT", "WHERE"};

  public static String format(final String sql) {
    final String ws = " \t\n\r\f";
    final String delims = " \t\n\r\f(),";
    final StringTokenizer tokenizer = new StringTokenizer(sql, delims, true);
    int depth = 0;
    String out = "";
    String prev = null;
    boolean lastReserved = true;
    boolean lastDelimNonWS = false;
    while (tokenizer.hasMoreTokens()) {
      final String token = tokenizer.nextToken();
      final boolean delim = token.length() == 1 && delims.contains(token);
      if (delim) {
        if (")".equals(token))
          out += "\n" + Strings.padFixed("", depth * 2, true) + token;
        else if (!lastDelimNonWS)
          out += token;

        if (",".equals(token))
          out += "\n" + Strings.padFixed("", depth * 2, true);

        if (!ws.contains(token))
          lastDelimNonWS = delim;
      }
      else {
        lastDelimNonWS = false;
        final boolean reserved = Arrays.binarySearch(reserveds, token) >= 0;
        if (reserved) {
          if (!lastReserved) {
            --depth;
            out += "\n";
          }
        }
        else if (lastReserved) {
          ++depth;
          out += "\n" + Strings.padFixed("", depth * 2, true);
        }

        lastReserved = reserved;
        out += token;
      }
    }

    return out;
  }

  private SQLFormat() {
  }
}