/*  Copyright Safris Software 2006
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

import static org.junit.Assert.assertEquals;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.Test;

public final class NonBlockingInputStreamTest {
  public static void main(final String[] args) throws Exception {
    new NonBlockingInputStreamTest().testInputStream();
  }

  @Test
  public void testInputStream() throws Exception {
    final PipedOutputStream out = new PipedOutputStream();
    final NonBlockingInputStream in = new NonBlockingInputStream(new PipedInputStream(out), 7);
    final int sleepTime = 100;

    // write 1 byte, and read it
    out.write('!');
    out.flush();
    Thread.sleep(sleepTime);
    assertEquals(in.read(), '!');

    // write 5 bytes, and read 5 bytes
    out.write("\"#$%&".getBytes());
    out.flush();
    Thread.sleep(sleepTime);
    assertEquals(in.read(), '"');
    assertEquals(in.read(), '#');
    assertEquals(in.read(), '$');
    assertEquals(in.read(), '%');
    assertEquals(in.read(), '&');

    // write 9 bytes, (first 2 will be lost) then read 1 byte
    out.write("'()*+,-./".getBytes());
    out.flush();
    Thread.sleep(sleepTime);
    assertEquals(in.read(), ')');
    assertEquals(in.getLostBytesCount(), 2);

    // write 2 bytes, and read 1, loosing 1 more byte
    out.write("01".getBytes());
    out.flush();
    Thread.sleep(sleepTime);
    assertEquals(in.read(), '+');
    assertEquals(in.getLostBytesCount(), 3);

    // write 2 bytes, and read 1, loosing 1 more byte
    out.write("23".getBytes());
    out.flush();
    Thread.sleep(sleepTime);
    assertEquals(in.read(), '-');
    assertEquals(in.getLostBytesCount(), 4);

    // write 3 bytes, and read 1, loosing 2 more bytes
    out.write("456".getBytes());
    out.flush();
    Thread.sleep(sleepTime);
    assertEquals(in.read(), '0');
    assertEquals(in.getLostBytesCount(), 6);

    // write 3 bytes, and read 7 bytes, loosing 2 bytes, and further ahead of the writing stream
    out.write("789".getBytes());
    out.flush();
    Thread.sleep(sleepTime);
    assertEquals(in.read(), '3');
    assertEquals(in.read(), '4');
    assertEquals(in.read(), '5');
    assertEquals(in.read(), '6');
    assertEquals(in.read(), '7');
    assertEquals(in.read(), '8');
    assertEquals(in.read(), '9');
    assertEquals(in.read(), 0);
    assertEquals(in.read(), 0);
    assertEquals(in.read(), 0);
    assertEquals(in.read(), 0);
    assertEquals(in.read(), 0);
    assertEquals(in.getLostBytesCount(), 8);

    // wirte 1 byte, try to read 2
    out.write(":".getBytes());
    out.flush();
    Thread.sleep(sleepTime);
    assertEquals(in.read(), ':');
    assertEquals(in.read(), 0);

    // write 13 bytes, and read 8
    out.write(";<=>?@ABCDEFG".getBytes());
    out.flush();
    Thread.sleep(sleepTime);
    assertEquals(in.read(), 'A');
    assertEquals(in.read(), 'B');
    assertEquals(in.read(), 'C');
    assertEquals(in.read(), 'D');
    assertEquals(in.read(), 'E');
    assertEquals(in.read(), 'F');
    assertEquals(in.read(), 'G');
    assertEquals(in.read(), 0);
    assertEquals(in.getLostBytesCount(), 14);

    // close the stream, and read -1 byte
    out.close();
    Thread.sleep(sleepTime);
    assertEquals(in.read(), -1);
    in.close();
  }
}