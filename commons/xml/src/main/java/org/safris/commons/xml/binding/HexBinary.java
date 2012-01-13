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

package org.safris.commons.xml.binding;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * http://www.w3.org/TR/xmlschema11-2/#hexBinary
 */
public class HexBinary {
  public static HexBinary parseHexBinary(String string) {
    if (string == null)
      return null;

    if (string.length() % 2 != 0)
      throw new IllegalArgumentException("odd length of hex string");

    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    for (int i = 0; i < string.length(); i += 2) {
      final char c1 = string.charAt(i);
      if (i + 1 >= string.length())
        throw new IllegalArgumentException();

      final char c2 = string.charAt(i + 1);
      byte b = 0;
      if ('0' <= c1 && c1 <= '9')
        b += ((c1 - '0') * 16);
      else if ('a' <= c1 && c1 <= 'f')
        b += ((c1 - 'a' + 10) * 16);
      else if ('A' <= c1 && c1 <= 'F')
        b += ((c1 - 'A' + 10) * 16);
      else
        throw new IllegalArgumentException("bad characted in hex string");

      if ('0' <= c2 && c2 <= '9')
        b += (c2 - '0');
      else if ('a' <= c2 && c2 <= 'f')
        b += (c2 - 'a' + 10);
      else if ('A' <= c2 && c2 <= 'F')
        b += (c2 - 'A' + 10);
      else
        throw new IllegalArgumentException("bad characted in hex string");

      out.write(b);
    }

    return new HexBinary(out.toByteArray());
  }

  private static char convertDigit(int value) {
    value &= 0x0f;
    if (value >= 10)
      return ((char)(value - 10 + 'A'));
    else
      return ((char)(value + '0'));
  }

  private final byte[] bytes;
  private final String encoded;

  public HexBinary(byte[] bytes) {
    this.bytes = bytes;
    final StringBuffer buffer = new StringBuffer(bytes.length * 2);
    for (int i = 0; i < bytes.length; i++) {
      buffer.append(convertDigit(bytes[i] >> 4));
      buffer.append(convertDigit(bytes[i] & 0x0f));
    }

    this.encoded = buffer.toString();
  }

  public byte[] getBytes() {
    return bytes;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof Base64Binary))
      return false;

    final HexBinary that = (HexBinary)obj;
    return bytes != null ? Arrays.equals(bytes, that.bytes) : that.bytes == null;
  }

  public int hashCode() {
    return bytes != null ? Arrays.hashCode(bytes) : -1;
  }

  /**
   * Returns the hex string representation of this object's byte[] data.
   *
   * @return  The hex string.
   */
  public String toString() {
    return encoded;
  }
}
