/* Copyright (c) 2006 lib4j
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

package org.lib4j.xml.binding;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * http://www.w3.org/TR/xmlschema11-2/#hexBinary
 */
public final class HexBinary implements Serializable {
  private static final long serialVersionUID = 3972638444033283159L;

  public static HexBinary parseHexBinary(final String string) {
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
    return value >= 10 ? (char)(value - 10 + 'A') : (char)(value + '0');
  }

  private final byte[] bytes;
  private final String encoded;

  public HexBinary(final byte[] bytes) {
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

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof Base64Binary))
      return false;

    final HexBinary that = (HexBinary)obj;
    return bytes != null ? Arrays.equals(bytes, that.bytes) : that.bytes == null;
  }

  @Override
  public int hashCode() {
    return bytes != null ? Arrays.hashCode(bytes) : -1;
  }

  /**
   * Returns the hex string representation of this object's byte[] data.
   *
   * @return  The hex string.
   */
  @Override
  public String toString() {
    return encoded;
  }
}