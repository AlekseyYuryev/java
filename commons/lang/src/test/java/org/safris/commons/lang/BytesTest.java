/* Copyright (c) 2012 Seva Safris
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

package org.safris.commons.lang;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BytesTest {
  private static final Logger logger = LoggerFactory.getLogger(BytesTest.class);

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
  public void testShort() {
    long l = 65535l;
    short s = (short)l;
    String binary = binary(l, Short.SIZE);
    logger.info("Binary: " + binary);
    logger.info("From binary: " + Integer.parseInt(binary(l, Short.SIZE), 2));
    byte[] bytes = new byte[Short.SIZE / 8];
    Bytes.toBytes(s, bytes, 0, true);
    logger.info("Convert.toBytes: " + Arrays.toString(bytes));
    Assert.assertArrayEquals(new byte[] {(byte)-1, (byte)-1}, bytes);
    int unsignedShort = Bytes.toShort(bytes, 0, true, false);
    logger.info("Convert.to[unsigned]Short: " + unsignedShort);
    Assert.assertEquals(l, unsignedShort);
    short signedShort = Bytes.toShort(bytes, 0, true);
    logger.info("Convert.to[signed]Short: " + signedShort);
    Assert.assertEquals(s, signedShort);
    logger.info("Raw: " + s);
  }

  @Test
  public void testInt() {
    long l = 4294967295l;
    int i = (int)l;
    String binary = binary(l, Integer.SIZE);
    logger.info("Binary: " + binary);
    logger.info("From binary: " + Long.parseLong(binary(l, Integer.SIZE), 2));
    byte[] bytes = new byte[Integer.SIZE / 8];
    Bytes.toBytes(i, bytes, 0, true);
    logger.info("Convert.toBytes: " + Arrays.toString(bytes));
    Assert.assertArrayEquals(new byte[] {(byte)-1, (byte)-1, (byte)-1, (byte)-1}, bytes);
    long unsignedInt = Bytes.toInt(bytes, 0, true, false);
    logger.info("Convert.to[unsigned]Int: " + unsignedInt);
    Assert.assertEquals(l, unsignedInt);
    int signedInt = Bytes.toInt(bytes, 0, true);
    logger.info("Convert.to[signed]Int: " + signedInt);
    Assert.assertEquals(i, signedInt);
    logger.info("Raw: " + i);
  }

  @Test
  public void testLong() {
    long l = 9223372036854775807l;
    String binary = binary(l, Long.SIZE);
    logger.info("Binary: " + binary);
    //log("From binary: " + Long.parseLong(binary(l, Long.SIZE), 2));
    byte[] bytes = new byte[Long.SIZE / 8];
    Bytes.toBytes(l, bytes, 0, true);
    logger.info("Convert.toBytes: " + Arrays.toString(bytes));
    Assert.assertArrayEquals(new byte[] {127, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1}, bytes);
    long signedInt = Bytes.toLong(bytes, 0, true);
    logger.info("Convert.to[signed]Int: " + signedInt);
    Assert.assertEquals(l, signedInt);
    logger.info("Raw: " + l);
  }

  @Test
  @Ignore("Implement this")
  public void testArbitrary() {
    // TODO: Implement this!
  }

  @Test
  public void testToOctal() {
    for (byte i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++)
      Assert.assertEquals(Integer.toString(i, 8), String.valueOf(Bytes.toOctal(i)));
  }
}