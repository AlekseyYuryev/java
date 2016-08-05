/* Copyright (c) 2016 Seva Safris
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

package org.safris.commons.io;

import java.io.IOException;
import java.io.Reader;

public final class Readers {
  public static String readFully(final Reader reader) throws IOException {
    final StringBuilder string = new StringBuilder();
    int ch;
    while ((ch = reader.read()) != -1)
      string.append((char)ch);

    return string.toString();
  }

  public static String readFully(final Reader reader, final int bufferSize) throws IOException {
    final StringBuilder string = new StringBuilder();
    final char[] buffer = new char[bufferSize];
    int size;
    while ((size = reader.read(buffer)) == bufferSize)
      string.append(buffer);

    string.append(buffer, 0, size);
    return string.toString();
  }

  private Readers() {
  }
}
