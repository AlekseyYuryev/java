package org.safris.commons.lang;

public final class Bytes {
  public static byte toByte(final short signedByte) {
    if (256 < signedByte || signedByte < 0)
      throw new IllegalArgumentException("256 < signedByte || signedByte < 0");

    return (byte)(signedByte & 0xff);
  }

  public static void toBytes(final short data, final byte[] bytes, final int offset, final boolean isBigEndian) {
    if (isBigEndian) {
      bytes[offset] = (byte)((data >> 8) & 0xff);
      bytes[offset + 1] = (byte)(data & 0xff);
    }
    else {
      bytes[offset] = (byte)(data & 0xff);
      bytes[offset + 1] = (byte)((data >> 8) & 0xff);
    }
  }

  public static void toBytes(final int data, final byte[] bytes, final int offset, final boolean isBigEndian) {
    if (isBigEndian) {
      bytes[offset] = (byte)((data >> 24) & 0xff);
      bytes[offset + 1] = (byte)((data >> 16) & 0xff);
      bytes[offset + 2] = (byte)((data >> 8) & 0xff);
      bytes[offset + 3] = (byte)(data & 0xff);
    }
    else {
      bytes[offset] = (byte)(data & 0xff);
      bytes[offset + 1] = (byte)((data >> 8) & 0xff);
      bytes[offset + 2] = (byte)((data >> 16) & 0xff);
      bytes[offset + 3] = (byte)((data >> 24) & 0xff);
    }
  }

  public static void toBytes(final long data, final byte[] bytes, final int offset, final boolean isBigEndian) {
    if (isBigEndian) {
      bytes[offset] = (byte)((data >> 56) & 0xff);
      bytes[offset + 1] = (byte)((data >> 48) & 0xff);
      bytes[offset + 2] = (byte)((data >> 40) & 0xff);
      bytes[offset + 3] = (byte)((data >> 32) & 0xff);
      bytes[offset + 4] = (byte)((data >> 24) & 0xff);
      bytes[offset + 5] = (byte)((data >> 16) & 0xff);
      bytes[offset + 6] = (byte)((data >> 8) & 0xff);
      bytes[offset + 7] = (byte)(data & 0xff);
    }
    else {
      bytes[offset] = (byte)(data & 0xff);
      bytes[offset + 1] = (byte)((data >> 8) & 0xff);
      bytes[offset + 2] = (byte)((data >> 16) & 0xff);
      bytes[offset + 3] = (byte)((data >> 24) & 0xff);
      bytes[offset + 4] = (byte)((data >> 32) & 0xff);
      bytes[offset + 5] = (byte)((data >> 40) & 0xff);
      bytes[offset + 6] = (byte)((data >> 48) & 0xff);
      bytes[offset + 7] = (byte)((data >> 56) & 0xff);
    }
  }

  public static void toBytes(final int byteLength, final long data, final byte[] bytes, final int offset, final boolean isBigEndian) {
    if (byteLength == Short.SIZE / 8)
      toBytes((short)data, bytes, offset, isBigEndian);
    else if (byteLength == Integer.SIZE / 8)
      toBytes((int)data, bytes, offset, isBigEndian);
    else if (byteLength == Long.SIZE / 8)
      toBytes(data, bytes, offset, isBigEndian);
  }

  public static short toUnsignedByte(final byte b) {
    return (short)(b & 0xff);
  }

  /**
   * Build a Java short from a 2-byte signed binary representation.
   * Depending on machine type, byte orders are
   * Big Endian (AS/400, Unix, System/390 byte-order) for signed binary representations, and
   * Little Endian (Intel 80/86 reversed byte-order) for signed binary representations.
   * @exception IllegalArgumentException if the specified byte order is not recognized.
   */
  public static short toShort(final byte[] bytes, final int offset, final boolean isBigEndian) {
    return (short)toShort(bytes, offset, isBigEndian, true);
  }

  public static int toShort(final byte[] bytes, final int offset, final boolean isBigEndian, final boolean signed) {
    final int value = isBigEndian ? ((bytes[offset] & 0xff) << 8) | (bytes[offset + 1] & 0xff) : (bytes[offset] & 0xff) | ((bytes[offset + 1] & 0xff) << 8);
    return signed ? (short)value : value;
  }

  /**
   * Build a Java int from a 4-byte signed binary representation.
   * Depending on machine type, byte orders are
   * Big Endian (AS/400, Unix, System/390 byte-order) for signed binary representations, and
   * Little Endian (Intel 80/86 reversed byte-order) for signed binary representations.
   * @exception IllegalArgumentException if the specified byte order is not recognized.
   */
  public static int toInt(final byte[] bytes, final int offset, final boolean isBigEndian) {
    return (int)toInt(bytes, offset, isBigEndian, true);
  }

  public static long toInt(final byte[] bytes, final int offset, final boolean isBigEndian, final boolean signed) {
    final long value = isBigEndian ? ((bytes[offset] & 0xffl) << 24) | ((bytes[offset + 1] & 0xffl) << 16) | ((bytes[offset + 2] & 0xffl) << 8) | (bytes[offset + 3] & 0xffl) : (bytes[offset] & 0xffl) | ((bytes[offset + 1] & 0xffl) << 8) | ((bytes[offset + 2] & 0xffl) << 16) | ((bytes[offset + 3] & 0xffl) << 24);
    return signed ? (int)value : value;
  }

  /**
   * Build a Java long from an 8-byte signed binary representation.
   * Depending on machine type, byte orders are
   * Big Endian (AS/400, Unix, System/390 byte-order) for signed binary representations, and
   * Little Endian (Intel 80/86 reversed byte-order) for signed binary representations.
   * @exception IllegalArgumentException if the specified byte order is not recognized.
   */
  // FIXME: Support unsigned
  public static long toLong(final byte[] bytes, final int offset, final boolean isBigEndian) {
    return isBigEndian ? ((bytes[offset] & 0xffl) << 56) | ((bytes[offset + 1] & 0xffl) << 48) | ((bytes[offset + 2] & 0xffl) << 40) | ((bytes[offset + 3] & 0xffl) << 32) | ((bytes[offset + 4] & 0xffl) << 24) | ((bytes[offset + 5] & 0xffl) << 16) | ((bytes[offset + 6] & 0xffl) << 8) | (bytes[offset + 7] & 0xffl) : (bytes[offset] & 0xffl) | ((bytes[offset + 1] & 0xffl) << 8) | ((bytes[offset + 2] & 0xffl) << 16) | ((bytes[offset + 3] & 0xffl) << 24) | ((bytes[offset + 4] & 0xffl) << 32) | ((bytes[offset + 5] & 0xffl) << 40) | ((bytes[offset + 6] & 0xffl) << 48) | ((bytes[offset + 7] & 0xffl) << 56);
  }

  public static long toArbitraryType(final int byteLength, final byte[] bytes, final int offset, final boolean isBigEndian) {
    return toArbitraryType(byteLength, bytes, offset, isBigEndian, true);
  }

  public static long toArbitraryType(final int byteLength, final byte[] bytes, final int offset, final boolean isBigEndian, final boolean signed) {
    if (byteLength == Byte.SIZE / 8)
      return bytes[offset];

    if (byteLength == Short.SIZE / 8)
      return toShort(bytes, offset, isBigEndian, signed);

    if (byteLength == Integer.SIZE / 8)
      return toInt(bytes, offset, isBigEndian, signed);

    if (byteLength == Long.SIZE / 8) {
      if (signed)
        return toLong(bytes, offset, isBigEndian);

      throw new UnsupportedOperationException("Unsigned long is not currently supported");
    }

    throw new UnsupportedOperationException(byteLength + " is not supported");
  }

  private Bytes() {
  }
}
