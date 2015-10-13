package org.safris.commons.json;

import java.io.IOException;
import java.io.InputStream;

public abstract class RecursiveFactory<T> {
  protected abstract T[] newInstance(final int depth);
  public abstract T decode(final InputStream in, char ch) throws IOException;
  public final T[] recurse(final InputStream in, final int depth) throws Exception {
    final T value = decode(in, JSOUtil.next(in));
    char ch = JSOUtil.next(in);
    if (ch == ',') {
      final T[] array = recurse(in, depth + 1);
      array[depth] = value;
      return array;
    }

    if (ch == ']') {
      final T[] array = newInstance(depth + 1);
      array[depth] = value;
      return array;
    }

    throw new IllegalArgumentException("Malformed JSON");
  }
}
