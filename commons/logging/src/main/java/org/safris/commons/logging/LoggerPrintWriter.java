package org.safris.commons.logging;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.logging.Level;

/**
 * Implements a PrintWriter which allows to alternatively plug in a <code>Writer</code> or a <code>Logger</code>.
 */
public class LoggerPrintWriter extends PrintWriter {
  private final StringBuffer buffer = new StringBuffer();
  private final java.util.logging.Logger logger;
  private final Level level;

  public LoggerPrintWriter(final java.util.logging.Logger logger, final Level level) {
    super(new NullWriter());
    if (logger == null)
      throw new NullPointerException("logger == null");

    if (level == null)
      throw new NullPointerException("level == null");

    this.logger = logger;
    this.level = level;
  }

  private void flushBuffer() {
    if (buffer.length() == 0)
      return;

    logger.log(level, buffer.toString());
    buffer.setLength(0);
  }

  public void close() {
    flushBuffer();
    super.close();
  }

  public void flush() {
    flushBuffer();
    super.flush();
  }

  public void write(final int c) {
    buffer.append(c);
  }

  public void write(final char cbuf[], final int off, final int len) {
    buffer.append(cbuf, off, len);
  }

  public void write(final String str, final int off, final int len) {
    buffer.append(str.substring(off, off + len));
  }

  public void println() {
    buffer.append('\n');
    flushBuffer();
  }

  private static class NullWriter extends Writer {
    public void close() throws IOException {
    }

    public void flush() throws IOException {
    }

    public void write(final char cbuf[], final int off, final int len) throws IOException {
    }
  }
}