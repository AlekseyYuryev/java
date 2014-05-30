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

package org.safris.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public final class TeeOutputStream extends OutputStream {
  private final OutputStream out1;
  private final OutputStream out2;

  public TeeOutputStream(final OutputStream out1, final OutputStream out2) {
    this.out1 = out1;
    this.out2 = out2;
  }

  public void write(final int b) throws IOException {
    out1.write(b);
    out2.write(b);
    if ((char)b == '\n') {
      out1.flush();
      out2.flush();
    }
  }
}