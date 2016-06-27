package org.safris.commons.util;

import java.io.IOException;
import java.io.Reader;

public class StringBuilderReader extends Reader {
  private final Reader reader;
  private final StringBuilder builder;

  public StringBuilderReader(final Reader reader, final StringBuilder builder) {
    this.reader = reader;
    this.builder = builder;
  }

  public StringBuilder getStringBuilder() {
    return builder;
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