package org.safris.commons.json;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

public class ObjectRecursiveFactory {
  public JSO decode(final InputStream in, char ch, final Class<?> clazz) throws IOException {
    try {
      final JSO value = (JSO)clazz.newInstance();
      value.decode(in, ch);
      return value;
    }
    catch (final ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }

  public JSO[] recurse(final InputStream in, final Class<?> clazz, final int depth) throws Exception {
    char ch = JSOUtil.next(in);
    final JSO value;
    if (ch != '{') {
      if (JSOUtil.isNull(ch, in))
        value = null;
      else
        throw new IllegalArgumentException("Malformed JSON");
    }
    else {
      value = (JSO)clazz.newInstance();
      value.decode(in, ch);
    }

    ch = JSOUtil.next(in);
    if (ch == ',') {
      final JSO[] array = recurse(in, clazz, depth + 1);
      array[depth] = value;
      return array;
    }

    if (ch == ']') {
      final JSO[] array = (JSO[])Array.newInstance(clazz, depth + 1);
      array[depth] = value;
      return array;
    }

    throw new IllegalArgumentException("Malformed JSON");
  }
}