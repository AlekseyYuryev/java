package org.safris.commons.lang;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class Throwables {
  public static String toString(final Throwable t) {
    if (t == null)
      throw new NullPointerException("t == null");

    final StringWriter stringWriter = new StringWriter();
    final PrintWriter printWriter = new PrintWriter(stringWriter);
    t.printStackTrace(printWriter);
    return stringWriter.toString();
  }

  private Throwables() {
  }
}