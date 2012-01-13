/*  Copyright Safris Software 2009
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

package org.safris.commons.util;

public class Hexadecimal {
  private static char[] hexChar = {
    '0', '1', '2', '3',
    '4', '5', '6', '7',
    '8', '9', 'a', 'b',
    'c', 'd', 'e', 'f'
  };
  private String hexadecimal = null;
  private byte[] bytes = null;

  public Hexadecimal(String hexadecimal) {
    this.hexadecimal = hexadecimal;
  }

  public Hexadecimal(byte[] bytes) {
    this.bytes = bytes;
  }

  public String toString() {
    if (hexadecimal == null)
      hexadecimal = bytesToHex(bytes);

    return hexadecimal;
  }

  public byte[] getBytes() {
    if (bytes == null)
      bytes = hexToBytes(hexadecimal);

    return bytes;
  }

  private static String bytesToHex(byte[] bytes) {
    if (bytes == null)
      throw new NullPointerException("bytes == null");

    final StringBuffer stringBuffer = new StringBuffer(bytes.length * 2);
    for (int i = 0; i < bytes.length; i++) {
      // look up high nibble char
      stringBuffer.append(hexChar[(bytes[i] & 0xf0) >>> 4]);

      // look up low nibble char
      stringBuffer.append(hexChar[bytes[i] & 0x0f]);
    }

    return stringBuffer.toString();
  }

  private static byte[] hexToBytes(String hexadecimal) {
    if (hexadecimal == null)
      throw new NullPointerException("hexadecimal == null");

    final int stringLength = hexadecimal.length();
    if (stringLength == 0)
      throw new IllegalArgumentException("hexadecimal.length() == 0");

    if ((stringLength & 0x1) != 0)
      throw new IllegalArgumentException("fromHexString requires an even number of hex characters");

    final byte[] bytes = new byte[stringLength / 2];
    for (int i = 0, j = 0; i < stringLength; i += 2, j++) {
      final int high = charToNibble(hexadecimal.charAt(i));
      final int low = charToNibble(hexadecimal.charAt(i + 1));
      bytes[j] = (byte)((high << 4) | low);
    }

    return bytes;
  }

  private static int charToNibble(char character) {
    if ('0' <= character && character <= '9')
      return character - '0';

    if ('a' <= character && character <= 'f')
      return character - 'a' + 0xa;

    if ('A' <= character && character <= 'F')
      return character - 'A' + 0xa;

    throw new IllegalArgumentException("Invalid hexadecimal character: " + character);
  }
}
