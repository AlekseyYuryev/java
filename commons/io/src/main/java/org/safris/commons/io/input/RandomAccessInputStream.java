/*  Copyright Safris Software 2012
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.commons.io.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public final class RandomAccessInputStream extends InputStream {
  private final RandomAccessFile file;
  private long mark = 0;

  public RandomAccessInputStream(final File file) throws FileNotFoundException {
    this.file = new RandomAccessFile(file, "r");
  }

  public int available() throws IOException {
    final long availableBytes = file.length() - file.getFilePointer();
    if (availableBytes > 0x7fffffffl)
      return 0x7fffffff;

    return (int)availableBytes;
  }

  public void close() throws IOException {
    file.close();
  }

  public void mark(final int readlimit) {
    try {
      this.mark = file.getFilePointer();
    }
    catch (final IOException e) {
      this.mark = -1;
    }
  }

  public boolean markSupported() {
    return true;
  }

  public int read() throws IOException {
    return file.read();
  }

  public int read(final byte[] b) throws IOException {
    return file.read(b);
  }

  public int read(final byte[] b, final int off, final int len) throws IOException {
    return file.read(b, off, len);
  }

  public void reset() throws IOException {
    if (mark < 0)
      throw new IOException("Invalid mark position");

    file.seek(mark);
  }

  public long skip(final long n) throws IOException {
    final long position = file.getFilePointer();
    try {
      file.seek(n + position);
    }
    catch (final IOException e) {
      if (!"Negative seek offset".equals(e.getMessage()))
        throw e;
    }

    return file.getFilePointer() - position;
  }
}