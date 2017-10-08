package org.libx4j.cdm.lexer;

import java.math.BigInteger;

@SuppressWarnings({"cast", "unused"})
public strictfp class DeclarationExample {
  boolean bool;
  private byte b = 0b0;
  short sh = 0xF;
  protected final int i;
  public long l;
  private float f;
  private double d;
  private final BigInteger bi = new BigInteger("7382");
  private static String string = "string";
  protected static final String finalString = "string";
  short[] shortArray = new short[] {};
  int[] intArray = new int[] {0__1, 2, 3};;
  transient long[] longArray = new long[] {'\0', (short)1, (int)2, 3l, (long)4d, new Float(5f).longValue()};
  volatile float[] floatArray = new float[3];

  {
    ++b;
    sh--;
    l += 3;
    switch (intArray[3]) {
      case 7:
        System.out.println(7);
      case 8:
        System.out.println(8);
      default:
        System.out.println(9);
    }
  }

  DeclarationExample() {
    this(7);
    System.out.println("Constructor1");
  }

  public DeclarationExample(final int i) {
    this.i = i;
    string += "xx";
    intArray[0] /= (1);
    intArray[1] *= ((intArray[intArray[0]]));
  }

  private DeclarationExample(String x) {
    this(0);
  }
}