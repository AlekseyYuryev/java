package org.libx4j.cdm.lexer;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class TryExample {
  public void tryExample() {
    try {
      throw new IOException();
    }
    catch (final RuntimeException | IOException e) {
    }
    finally {
    }
  }

  public void tryResource() throws MalformedURLException, IOException {
    try (final InputStream in = new URL("hello").openStream()) {
      assert(in != null);
    }
  }
}