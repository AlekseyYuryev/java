package org.safris.commons.lang;

import java.util.Arrays;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class BytesTest {
  public static String binary(long byteValue, int typeSize) {
    String byteValueString = "";
    for (int j = 0; j <= typeSize - 1; j++) {
      int mask = 1 << j;
      if ((mask & byteValue) > 0) {
        byteValueString = "1" + byteValueString;
      }
      else {
        byteValueString = "0" + byteValueString;
      }
    }

    return byteValueString;
  }

  public static void main(String[] args) {
    final BytesTest bytesTest = new BytesTest();
    bytesTest.testByte();
    bytesTest.testShort();
    bytesTest.testInt();
    bytesTest.testLong();
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
    assertEquals(l, unsignedByte);
    byte tb = Bytes.toByte(unsignedByte);
    System.out.println("Convert.toByte: " + tb);
    assertEquals(b, tb);
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
    assertArrayEquals(new byte[] {(byte)-1, (byte)-1}, bytes);
    int unsignedShort = Bytes.toShort(bytes, 0, true, false);
    System.out.println("Convert.to[unsigned]Short: " + unsignedShort);
    assertEquals(l, unsignedShort);
    short signedShort = Bytes.toShort(bytes, 0, true);
    System.out.println("Convert.to[signed]Short: " + signedShort);
    assertEquals(s, signedShort);
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
    assertArrayEquals(new byte[] {(byte)-1, (byte)-1, (byte)-1, (byte)-1}, bytes);
    long unsignedInt = Bytes.toInt(bytes, 0, true, false);
    System.out.println("Convert.to[unsigned]Int: " + unsignedInt);
    assertEquals(l, unsignedInt);
    int signedInt = Bytes.toInt(bytes, 0, true);
    System.out.println("Convert.to[signed]Int: " + signedInt);
    assertEquals(i, signedInt);
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
    assertArrayEquals(new byte[] {127, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1}, bytes);
    long signedInt = Bytes.toLong(bytes, 0, true);
    System.out.println("Convert.to[signed]Int: " + signedInt);
    assertEquals(l, signedInt);
    System.out.println("Raw: " + l);
  }

  @Test
  @Ignore
  public void testArbitrary() {
    // TODO: Implement this!
  }
}
