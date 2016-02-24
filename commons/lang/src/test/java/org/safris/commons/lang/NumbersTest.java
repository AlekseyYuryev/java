/* Copyright (c) 2008 Seva Safris
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

import java.math.BigInteger;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class NumbersTest {
  public static double testLogBigInteger(final int[] factors, final int[] exponents) {
    double l1 = 0;
    BigInteger bi = BigInteger.ONE;
    for (int i = 0; i < factors.length; i++) {
      int exponent = exponents[i];
      int factor = factors[i];
      if (factor <= 1)
        continue;

      for (int n = 0; n < exponent; n++)
        bi = bi.multiply(BigInteger.valueOf(factor));

      l1 += Math.log(factor) * exponent;
    }

    final double l2 = Numbers.log2(bi);
    final double err = Math.abs((l2 - l1) / l1);
    final int decdigits = (int) (l1 / Math.log(10) + 0.5);
    System.out.printf("e=%e digitss=%d \n", err, decdigits);
    return err;
  }

  @Test
  public void testManyTries() {
    final int tries = 100;

    final int[] f = {1, 1, 1, 1, 1};
    final int[] e = {1, 1, 1, 1, 1};
    final Random r = new Random();
    double maxerr = 0;
    for (int n = 0; n < tries; n++) {
      for (int i = 0; i < f.length; i++) {
        f[i] = r.nextInt(100000) + 2;
        e[i] = r.nextInt(1000) + 1;
      }

      final double err = testLogBigInteger(f, e);
      if (err > maxerr)
        maxerr = err;
    }

    System.out.printf("Max err: %e \n", maxerr);
  }

  @Test
  public void testparseNumber() {
    Assert.assertEquals(2.5, Numbers.parseNumber("2 1/2"), 0);
    Assert.assertEquals(2.75, Numbers.parseNumber("2 3/4"), 0);
  }

  @Test
  public void testparseNumber2() {
    Assert.assertEquals(0, Numbers.parseNumber("0"), 0);
    Assert.assertEquals(299792458, Numbers.parseNumber(" 299792458"), 0);
    Assert.assertEquals(-299792458, Numbers.parseNumber("-299792458 "), 0);
    Assert.assertEquals(3.14159265, Numbers.parseNumber(" 3.14159265"), 0);
    Assert.assertEquals(-3.14159265, Numbers.parseNumber("-3.14159265"), 0);
    Assert.assertEquals(6.022E23, Numbers.parseNumber("6.022E23 "), 0);
    Assert.assertEquals(-6.022E23, Numbers.parseNumber(" -6.022E23"), 0);
    Assert.assertEquals(6.626068E-34, Numbers.parseNumber(" 6.626068E-34"), 0);
    Assert.assertEquals(-6.626068E-34, Numbers.parseNumber("-6.626068E-34 "), 0);

    Assert.assertEquals(Double.NaN, Numbers.parseNumber(null), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber(""), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber(" "), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber("not number"), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber("3.14.15"), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber("29-97924"), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber("-29-97924"), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber("2 997924"), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber("-29-979.24"), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber("-2.9-979.24"), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber("6.022 E23 "), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber(" -6.022E 23"), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber("-6.626068E--34 "), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber("-6.626068E-3-4 "), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber("-6.626068E-3.4"), 0);
    Assert.assertEquals(Double.NaN, Numbers.parseNumber("-6.626E068E-34"), 0);
  }

  @Test
  public void testIsNumber() {
    Assert.assertTrue(Numbers.isNumber("0"));
    Assert.assertTrue(Numbers.isNumber(" 299792458"));
    Assert.assertTrue(Numbers.isNumber("-299792458 "));
    Assert.assertTrue(Numbers.isNumber(" 3.14159265"));
    Assert.assertTrue(Numbers.isNumber("-3.14159265"));
    Assert.assertTrue(Numbers.isNumber("6.022E23 "));
    Assert.assertTrue(Numbers.isNumber(" -6.022E23"));
    Assert.assertTrue(Numbers.isNumber(" 6.626068E-34"));
    Assert.assertTrue(Numbers.isNumber("-6.626068E-34 "));
    Assert.assertTrue(Numbers.isNumber("-6.626068E-34 24/49"));
    Assert.assertTrue(Numbers.isNumber("3/5"));

    Assert.assertFalse(Numbers.isNumber(null));
    Assert.assertFalse(Numbers.isNumber(""));
    Assert.assertFalse(Numbers.isNumber(" "));
    Assert.assertFalse(Numbers.isNumber("not number"));
    Assert.assertFalse(Numbers.isNumber("3.14.15"));
    Assert.assertFalse(Numbers.isNumber("29-97924"));
    Assert.assertFalse(Numbers.isNumber("-29-97924"));
    Assert.assertFalse(Numbers.isNumber("2 997924"));
    Assert.assertFalse(Numbers.isNumber("-29-979.24"));
    Assert.assertFalse(Numbers.isNumber("-2.9-979.24"));
    Assert.assertFalse(Numbers.isNumber("6.022 E23 "));
    Assert.assertFalse(Numbers.isNumber(" -6.022E 23"));
    Assert.assertFalse(Numbers.isNumber("-6.626068E--34 "));
    Assert.assertFalse(Numbers.isNumber("-6.626068E-3-4 "));
    Assert.assertFalse(Numbers.isNumber("-6.626068E-3.4"));
    Assert.assertFalse(Numbers.isNumber("-6.626E068E-34"));
  }

  @Test
  public void testToString() {
    Assert.assertEquals("0.00833333333333", Numbers.toString(0.008333333333330742, 14));
    Assert.assertEquals("0.00833333333334", Numbers.toString(0.008333333333339323, 14));
    Assert.assertEquals("0.008333333333", Numbers.toString(0.008333333333000000, 14));
  }

  @Test
  public void testUnsigned() {
    Assert.assertEquals(0, Numbers.Unsigned.toSigned(Byte.MIN_VALUE));
    Assert.assertEquals(255, Numbers.Unsigned.toSigned(Byte.MAX_VALUE));
    Assert.assertEquals(0, Numbers.Unsigned.toSigned(Short.MIN_VALUE));
    Assert.assertEquals(65535, Numbers.Unsigned.toSigned(Short.MAX_VALUE));
    Assert.assertEquals(0, Numbers.Unsigned.toSigned(Integer.MIN_VALUE));
    Assert.assertEquals(4294967295L, Numbers.Unsigned.toSigned(Integer.MAX_VALUE));
    Assert.assertEquals(BigInteger.valueOf(0), Numbers.Unsigned.toSigned(Long.MIN_VALUE));
    Assert.assertEquals(new BigInteger("18446744073709551615"), Numbers.Unsigned.toSigned(Long.MAX_VALUE));
  }
}