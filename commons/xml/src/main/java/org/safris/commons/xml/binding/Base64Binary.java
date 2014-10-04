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

package org.safris.commons.xml.binding;

import java.io.IOException;
import java.util.Arrays;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * http://www.w3.org/TR/xmlschema11-2/#base64Binary
 */
public final class Base64Binary {
  public static Base64Binary parseBase64Binary(final String string) {
    if (string == null)
      return null;

    byte[] bytes = null;
    try {
      bytes = new BASE64Decoder().decodeBuffer(string);
    }
    catch (final IOException e) {
      final IllegalArgumentException illegalArgumentException = new IllegalArgumentException("unable to decode");
      illegalArgumentException.setStackTrace(e.getStackTrace());
      throw illegalArgumentException;
    }

    return new Base64Binary(bytes);
  }

  private final byte[] bytes;
  private String encoded = null;

  public Base64Binary(final byte[] bytes) {
    this.bytes = bytes;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public boolean equals(final Object obj) {
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
    return encoded == null ? encoded = new BASE64Encoder().encodeBuffer(bytes) : encoded;
  }
}