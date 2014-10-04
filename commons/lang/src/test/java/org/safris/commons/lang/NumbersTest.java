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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class NumbersTest {
  public static void main(final String[] args) {
    new NumbersTest().testIsNumber();
  }

  @Test
  public void testIsNumber() {
    assertTrue(Numbers.isNumber("0"));
    assertTrue(Numbers.isNumber(" 299792458"));
    assertTrue(Numbers.isNumber("-299792458 "));
    assertTrue(Numbers.isNumber(" 3.14159265"));
    assertTrue(Numbers.isNumber("-3.14159265"));
    assertTrue(Numbers.isNumber("6.022E23 "));
    assertTrue(Numbers.isNumber(" -6.022E23"));
    assertTrue(Numbers.isNumber(" 6.626068E-34"));
    assertTrue(Numbers.isNumber("-6.626068E-34 "));

    assertFalse(Numbers.isNumber(null));
    assertFalse(Numbers.isNumber(""));
    assertFalse(Numbers.isNumber(" "));
    assertFalse(Numbers.isNumber("not number"));
    assertFalse(Numbers.isNumber("3.14.15"));
    assertFalse(Numbers.isNumber("29-97924"));
    assertFalse(Numbers.isNumber("-29-97924"));
    assertFalse(Numbers.isNumber("2 997924"));
    assertFalse(Numbers.isNumber("-29-979.24"));
    assertFalse(Numbers.isNumber("-2.9-979.24"));
    assertFalse(Numbers.isNumber("6.022 E23 "));
    assertFalse(Numbers.isNumber(" -6.022E 23"));
    assertFalse(Numbers.isNumber("-6.626068E--34 "));
    assertFalse(Numbers.isNumber("-6.626068E-3-4 "));
    assertFalse(Numbers.isNumber("-6.626068E-3.4"));
    assertFalse(Numbers.isNumber("-6.626E068E-34"));
  }
}