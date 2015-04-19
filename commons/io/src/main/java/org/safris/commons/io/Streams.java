/* Copyright (c) 2006 Seva Safris
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.safris.commons.io.output.TeeOutputStream;

public final class Streams {
  /**
   * Reads all bytes from the input stream and returns the resulting buffer
   * array. This method blocks until all contents have been read, end of
   * file is detected, or an exception is thrown.
   *
   * <p> If the InputStream <code>in</code> is <code>null</code>, then null
   * is returned; otherwise, a byte[] of at least size 0 will be returned.
   *
   * @param      in   the input stream to read from.
   * @return     the byte[] containing all bytes that were read from the
   *             InputStream <code>in</code> until an end of file is detected.
   * @exception  IOException  If the first byte cannot be read for any reason
   * other than the end of the file, if the input stream has been closed, or
   * if some other I/O error occurs.
   * @see        java.io.InputStream#read(byte[])
   */
  public static byte[] getBytes(final InputStream in) throws IOException {
    if (in == null)
      return null;

    final int bufferSize = 1024;
    final ByteArrayOutputStream buffer = new ByteArrayOutputStream(bufferSize);
    final byte[] data = new byte[bufferSize];
    int length = -1;
    while ((length = in.read(data)) != -1)
      buffer.write(data, 0, length);

    return buffer.toByteArray();
  }

  public static OutputStream tee(final OutputStream src, final InputStream snkIn, final OutputStream snkOut) throws IOException {
    pipe(snkIn, src, false, true);
    return new TeeOutputStream(src, snkOut);
  }

  public static OutputStream teeAsync(final OutputStream src, final InputStream snkIn, final OutputStream snkOut) throws IOException {
    pipe(snkIn, src, false, false);
    return new TeeOutputStream(src, snkOut);
  }

  public static InputStream tee(final InputStream src, final OutputStream snk) throws IOException {
    return pipe(src, snk, true, true);
  }

  public static InputStream teeAsync(final InputStream src, final OutputStream snk) throws IOException {
    return pipe(src, snk, true, false);
  }

  public static void pipe(final OutputStream src, final InputStream snk) throws IOException {
    pipe(snk, src, false, true);
  }

  public static void pipeAsync(final OutputStream src, final InputStream snk) throws IOException {
    pipe(snk, src, false, false);
  }

  public static void pipe(final InputStream src, final OutputStream snk) throws IOException {
    pipe(src, snk, false, true);
  }

  public static void pipeAsync(final InputStream src, final OutputStream snk) throws IOException {
    pipe(src, snk, false, false);
  }

  private static InputStream pipe(final InputStream src, final OutputStream snk, final boolean tee, final boolean sync) throws IOException {
    final PipedOutputStream pipedOut;
    final InputStream pipedIn;
    if (tee) {
      pipedOut = new PipedOutputStream();
      pipedIn = new PipedInputStream(pipedOut);
    }
    else {
      pipedOut = null;
      pipedIn = null;
    }

    final int bufferSize = 1024;
    if (sync)
      Streams.run(src, snk, pipedOut, tee, bufferSize);
    else
      new Thread(tee ? "tee" : "pipe") {
        public void run() {
          Streams.run(src, snk, pipedOut, tee, bufferSize);
        }
      }.start();

    return pipedIn;
  }

  private static void run(final InputStream src, final OutputStream snk, final PipedOutputStream pipedOut, final boolean tee, final int bufferSize) {
    int length = 0;
    final byte[] bytes = new byte[bufferSize];
    try {
      while ((length = src.read(bytes)) != -1) {
        if (tee) {
          pipedOut.write(bytes, 0, length);
          pipedOut.flush();
        }

        snk.write(bytes, 0, length);
        snk.flush();
      }
    }
    catch (final IOException e) {
      if ("Write end dead".equals(e.getMessage()) || "Broken pipe".equals(e.getMessage()) || "Pipe broken".equals(e.getMessage()) || "Stream closed".equals(e.getMessage()) || "Pipe closed".equals(e.getMessage()) || "Bad file number".equals(e.getMessage()))
        return;

      e.printStackTrace();
    }
  }

  private Streams() {
  }
}