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
