package org.safris.commons.json;

import java.io.IOException;
import java.io.InputStream;

public class BooleanRecursiveFactory extends RecursiveFactory<Boolean> {
  protected Boolean[] newInstance(final int depth) {
    return new Boolean[depth];
  }

  public Boolean decode(final InputStream in, char ch) throws IOException {
    if (ch != 'f' && ch != 't') {
      if (JSOUtil.isNull(ch, in))
        return null;

      throw new IllegalArgumentException("Malformed JSON");
    }

    final StringBuilder value = new StringBuilder(5);
    value.append(ch);
    do
      value.append(JSOUtil.next(in));
    while ((value.length() != 4 || !"true".equals(value.toString())) && (value.length() != 5 || !"false".equals(value.toString())));

    return Boolean.parseBoolean(value.toString());
  }
}