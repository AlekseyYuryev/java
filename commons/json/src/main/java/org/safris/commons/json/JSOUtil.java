package org.safris.commons.json;

import java.io.IOException;
import java.io.InputStream;

abstract class JSOUtil {
  protected static String pad(final int depth) {
    final StringBuilder out = new StringBuilder();
    for (int i = 0; i < depth; i++)
      out.append("  ");

    return out.toString();
  }

  protected static char next(final java.io.InputStream in) throws java.io.IOException {
    int ch;
    while (true) {
      ch = in.read();
      if (ch == -1)
        throw new java.io.IOException("EOS");

      if (ch != ' ' && ch != '\t' && ch != '\n' && ch != '\r')
        return (char)ch;
    }
  }

  protected static char nextAny(final java.io.InputStream in) throws java.io.IOException {
    int ch;
    while (true) {
      ch = in.read();
      if (ch == -1)
        throw new java.io.IOException("EOS");

      return (char)ch;
    }
  }

  protected static boolean isNull(char ch, final InputStream in) throws IOException {
    return ch == 'n' && next(in) == 'u' && next(in) == 'l' && next(in) == 'l';
  }

  protected static String format(final Number number) {
    return number == null ? null : ((double)number.intValue() == number.doubleValue() ? String.valueOf(number.intValue()) : String.valueOf(number.doubleValue()));
  }

  protected static String tokenize(final JSO[] value, final int depth) {
    if (value == null)
      return "null";

    if (value.length == 0)
      return "[]";

    final StringBuilder out = new StringBuilder();
    for (final JSO part : value)
      out.append(", ").append(part != null ? part.encode(depth) : "null");

    return "[" + out.substring(2) + "]";
  }

  protected static <T>String tokenize(final T[] value, final int depth) {
    if (value == null)
      return "null";

    if (value.length == 0)
      return "[]";

    final StringBuilder out = new StringBuilder();
    for (final T part : value)
      out.append(", \"").append(part).append("\"");

    return "[" + out.substring(2) + "]";
  }
}