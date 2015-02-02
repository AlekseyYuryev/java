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

import org.junit.Assert;
import org.junit.Test;

public final class NumbersTest {
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
}