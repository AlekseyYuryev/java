/*  Copyright Safris Software 2008
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

package org.safris.commons.xml.binding;

import org.junit.Assert;
import org.junit.Test;

public final class HexBinaryTest {
  private static void assertEquals(final String data, final String base64) {
    final HexBinary hexBinary = new HexBinary(data.getBytes());
    final String hexString = hexBinary.toString();
    Assert.assertEquals(base64, hexString);
    final HexBinary unmarshalled = HexBinary.parseHexBinary(hexString);
    Assert.assertEquals(data, new String(unmarshalled.getBytes()));
  }

  public static void main(final String[] args) {
    new HexBinaryTest().testHexBinary();
  }

  @Test
  public void testHexBinary() {
    assertEquals("Bonjour", "426F6E6A6F7572");
    assertEquals("Hello World", "48656C6C6F20576F726C64");
    assertEquals("The quick brown fox jumps over the lazy dog", "54686520717569636B2062726F776E20666F78206A756D7073206F76657220746865206C617A7920646F67");
  }
}