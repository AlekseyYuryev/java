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

import java.io.IOException;
import java.util.Arrays;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * http://www.w3.org/TR/xmlschema11-2/#base64Binary
 */
public final class Base64Binary {
  public static Base64Binary parseBase64Binary(String string) {
    if (string == null)
      return null;

    byte[] bytes = null;
    try {
      bytes = new BASE64Decoder().decodeBuffer(string);
    }
    catch (IOException e) {
      final IllegalArgumentException illegalArgumentException = new IllegalArgumentException("unable to decode");
      illegalArgumentException.setStackTrace(e.getStackTrace());
      throw illegalArgumentException;
    }

    return new Base64Binary(bytes);
  }

  private final byte[] bytes;
  private String encoded = null;

  public Base64Binary(byte[] bytes) {
    this.bytes = bytes;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof Base64Binary))
      return false;

    final Base64Binary that = (Base64Binary)obj;
    return bytes != null ? Arrays.equals(bytes, that.bytes) : that.bytes == null;
  }

  public int hashCode() {
    return bytes != null ? Arrays.hashCode(bytes) : -1;
  }

  /**
   * Returns the base64 string representation of this object's byte[] data.
   *
   * @return  The base64 string.
   */
  public String toString() {
    if (encoded == null)
      encoded = new BASE64Encoder().encodeBuffer(bytes);

    return encoded;
  }
}
