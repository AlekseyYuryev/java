package org.safris.commons.io;

import java.io.IOException;
import java.io.Reader;

public final class Readers {
  public static void readFully(final Reader reader) throws IOException {
    while (reader.read() != -1);
  }

  public static void readFully(final Reader reader, final int bufferSize) throws IOException {
    final char[] buffer = new char[bufferSize];
    while (reader.read(buffer) == bufferSize);
  }

  private Readers() {
  }
}
