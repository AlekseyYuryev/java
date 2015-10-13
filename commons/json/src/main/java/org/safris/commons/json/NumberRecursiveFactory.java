package org.safris.commons.json;

import java.io.IOException;
import java.io.InputStream;

public class NumberRecursiveFactory extends RecursiveFactory<Number> {
  protected Number[] newInstance(final int depth) {
    return new Double[depth];
  }

  public Number decode(final InputStream in, char ch) throws IOException {
    if (('0' > ch || ch > '9') && ch != '-') {
      if (JSOUtil.isNull(ch, in))
        return null;

      throw new IllegalArgumentException("Malformed JSON");
    }

    final StringBuilder value = new StringBuilder();
    do {
      in.mark(24);
      value.append(ch);
    }
    while ('0' <= (ch = JSOUtil.nextAny(in)) && ch <= '9' || ch == '.' || ch == 'e' || ch == 'E' || ch == '+' || ch == '+');

    in.reset();
    final String number = value.toString();
    return number.contains(".") || number.contains("e") || number.contains("E") ? Double.parseDouble(number) : Integer.parseInt(number);
  }
}