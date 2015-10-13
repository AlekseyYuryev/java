package org.safris.commons.json;

import java.io.IOException;
import java.io.InputStream;

public class StringRecursiveFactory extends RecursiveFactory<String> {
  protected String[] newInstance(final int depth) {
    return new String[depth];
  }

  public String decode(final InputStream in, char ch) throws IOException {
    if (ch != '"') {
      if (JSOUtil.isNull(ch, in))
        return null;

      throw new IllegalArgumentException("Malformed JSON");
    }

    final StringBuilder value = new StringBuilder();
    while ((ch = JSOUtil.nextAny(in)) != '"')
      value.append(ch);

    return value.toString();
  }
}