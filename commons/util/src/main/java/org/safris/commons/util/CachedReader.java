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

package org.safris.commons.util;

import java.io.IOException;
import java.io.Reader;

public class CachedReader extends Reader {
  private final Reader reader;
  private final StringBuilder builder;

  public CachedReader(final Reader reader, final StringBuilder builder) {
    this.reader = reader;
    this.builder = builder;
  }

  public String readFully() throws IOException {
    while (read() != -1);
    return builder.toString();
  }

  public String readCached() {
    return builder.toString();
  }

  @Override
  public int read() throws IOException {
    final int ch = reader.read();
    if (ch != -1)
      builder.append((char)ch);

    return ch;
  }

  @Override
  public int read(final char[] cbuf, final int off, final int len) throws IOException {
    final int read = reader.read(cbuf, off, len);
    if (read <= 0)
      return read;

    builder.append(cbuf, off, read);
    return read;
  }

  @Override
  public void close() throws IOException {
    reader.close();
  }
}