/* Copyright (c) 2015 Seva Safris
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