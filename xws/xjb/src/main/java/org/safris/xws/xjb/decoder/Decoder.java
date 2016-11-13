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

package org.safris.xws.xjb.decoder;

import java.io.IOException;
import java.io.Reader;

import org.safris.xws.xjb.JSObjectUtil;

public abstract class Decoder<T> extends JSObjectUtil {
  protected abstract T[] newInstance(final int depth);

  public abstract T decode(final Reader reader, char ch) throws IOException;

  public final T[] recurse(final Reader reader, final int depth) throws IOException {
    char ch = JSObjectUtil.next(reader);
    if (ch == ']')
      return newInstance(depth);

    if (ch == ',')
      return recurse(reader, depth);

    final T value = decode(reader, ch);
    final T[] array = recurse(reader, depth + 1);
    array[depth] = value;
    return array;
  }
}