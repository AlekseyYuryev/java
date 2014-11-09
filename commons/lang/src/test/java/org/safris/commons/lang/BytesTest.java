package org.safris.commons.lang;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public final class BytesTest {
  public static String binary(final long byteValue, final int typeSize) {
    String byteValueString = "";
    for (int j = 0; j <= typeSize - 1; j++) {
      final int mask = 1 << j;
      byteValueString = ((mask & byteValue) > 0 ? "1" : "0") + byteValueString;
    }

    return byteValueString;
  }

  @Test
  public void testIndexOf() {
    final byte[] bytes = new byte[] {1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7};

    Assert.assertEquals(0, Bytes.Byte.indexOf(bytes, new byte[] {(byte)1}));
    Assert.assertEquals(2, Bytes.Byte.indexOf(bytes, (byte)-1, (byte)3));
    Assert.assertEquals(6, Bytes.Byte.indexOf(bytes, (byte)9, (byte)11, (byte)7));
    Assert.assertEquals(-1, Bytes.Byte.indexOf(bytes, (byte)9, (byte)11, (byte)13, (byte)8));

    Assert.assertEquals(7, Bytes.Byte.indexOf(bytes, 1, (byte)1));
    Assert.assertEquals(9, Bytes.Byte.indexOf(bytes, 3, (byte)3));
    Assert.assertEquals(13, Bytes.Byte.indexOf(bytes, 7, (byte)7));
    Assert.assertEquals(-1, Bytes.Byte.indexOf(bytes, 7, (byte)8));

    Assert.assertEquals(0, Bytes.indexOf(bytes, new byte[] {1, 2, 3}));
    Assert.assertEquals(2, Bytes.indexOf(bytes, new byte[] {0, 4, 5}, new byte[] {3, 4, 5}));
    Assert.assertEquals(4, Bytes.indexOf(bytes, new byte[] {5, 6, 7}));
    Assert.assertEquals(-1, Bytes.indexOf(bytes, new byte[] {6, 7, 8}));

    Assert.assertEquals(7, Bytes.indexOf(bytes, 1, new byte[] {1, 2, 3}));
    Assert.assertEquals(9, Bytes.indexOf(bytes, 3, new byte[] {3, 4, 5}));
    Assert.assertEquals(11, Bytes.indexOf(bytes, 5, new byte[] {5, 6, 7}));
    Assert.assertEquals(-1, Bytes.indexOf(bytes, 7, new byte[] {6, 7, 8}));
  }

  @Test
  public void testReplaceAll() {
    byte[] bytes = new byte[] {1, 2, 3, 4, 5, 6, 7};
    Bytes.replaceAll(bytes, new byte[] {1, 2, 3}, new byte[] {0, 0, 0});
    Assert.assertArrayEquals(new byte[] {0, 0, 0, 4, 5, 6, 7}, bytes);

    bytes = new byte[] {1, 2, 3, 4, 5, 6, 7};
    Bytes.replaceAll(bytes, new byte[] {2, 3, 4}, new byte[] {1, 1, 1});
    Assert.assertArrayEquals(new byte[] {1, 1, 1, 1, 5, 6, 7}, bytes);

    bytes = new byte[] {1, 2, 3, 4, 5, 6, 7};
    Bytes.replaceAll(bytes, new byte[] {5, 6, 7}, new byte[] {0, 0, 0});
    Assert.assertArrayEquals(new byte[] {1, 2, 3, 4, 0, 0, 0}, bytes);
  }

  @Test
  public void testByte() {
    long l = 255l;
    byte b = (byte)l;
    String binary = binary(l, Byte.SIZE);
    System.out.println("Binary: " + binary);
    System.out.println("From binary: " + Integer.parseInt(binary(l, Byte.SIZE), 2));
    short unsignedByte = Bytes.toUnsignedByte(b);
    System.out.println("Convert.toUnsignedByte: " + unsignedByte);
    Assert.assertEquals(l, unsignedByte);
    byte tb = Bytes.toByte(unsignedByte);
    System.out.println("Convert.toByte: " + tb);
    Assert.assertEquals(b, tb);
    System.out.println("Raw: " + b);
  }

  @Test
  public void testShort() {
    long l = 65535l;
    short s = (short)l;
    String binary = binary(l, Short.SIZE);
    System.out.println("Binary: " + binary);
    System.out.println("From binary: " + Integer.parseInt(binary(l, Short.SIZE), 2));
    byte[] bytes = new byte[Short.SIZE / 8];
    Bytes.toBytes(s, bytes, 0, true);
    System.out.println("Convert.toBytes: " + Arrays.toString(bytes));
    Assert.assertArrayEquals(new byte[] {(byte)-1, (byte)-1}, bytes);
    int unsignedShort = Bytes.toShort(bytes, 0, true, false);
    System.out.println("Convert.to[unsigned]Short: " + unsignedShort);
    Assert.assertEquals(l, unsignedShort);
    short signedShort = Bytes.toShort(bytes, 0, true);
    System.out.println("Convert.to[signed]Short: " + signedShort);
    Assert.assertEquals(s, signedShort);
    System.out.println("Raw: " + s);
  }

  @Test
  public void testInt() {
    long l = 4294967295l;
    int i = (int)l;
    String binary = binary(l, Integer.SIZE);
    System.out.println("Binary: " + binary);
    System.out.println("From binary: " + Long.parseLong(binary(l, Integer.SIZE), 2));
    byte[] bytes = new byte[Integer.SIZE / 8];
    Bytes.toBytes(i, bytes, 0, true);
    System.out.println("Convert.toBytes: " + Arrays.toString(bytes));
    Assert.assertArrayEquals(new byte[] {(byte)-1, (byte)-1, (byte)-1, (byte)-1}, bytes);
    long unsignedInt = Bytes.toInt(bytes, 0, true, false);
    System.out.println("Convert.to[unsigned]Int: " + unsignedInt);
    Assert.assertEquals(l, unsignedInt);
    int signedInt = Bytes.toInt(bytes, 0, true);
    System.out.println("Convert.to[signed]Int: " + signedInt);
    Assert.assertEquals(i, signedInt);
    System.out.println("Raw: " + i);
  }

  @Test
  public void testLong() {
    long l = 9223372036854775807l;
    String binary = binary(l, Long.SIZE);
    System.out.println("Binary: " + binary);
    //System.out.println("From binary: " + Long.parseLong(binary(l, Long.SIZE), 2));
    byte[] bytes = new byte[Long.SIZE / 8];
    Bytes.toBytes(l, bytes, 0, true);
    System.out.println("Convert.toBytes: " + Arrays.toString(bytes));
    Assert.assertArrayEquals(new byte[] {127, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1}, bytes);
    long signedInt = Bytes.toLong(bytes, 0, true);
    System.out.println("Convert.to[signed]Int: " + signedInt);
    Assert.assertEquals(l, signedInt);
    System.out.println("Raw: " + l);
  }

  @Test
  @Ignore
  public void testArbitrary() {
    // TODO: Implement this!
  }
}