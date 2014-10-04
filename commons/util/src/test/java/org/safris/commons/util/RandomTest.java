/* Copyright (c) 2009 Seva Safris
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

package org.safris.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

public final class RandomTest {
  private static final char[] ALPHA = "abcdefghijklmnopqrstuvwxyz".toCharArray();
  private static final char[] NUMERIC = "0123456789".toCharArray();
  private static final char[] ALPHA_NUMERIC = (new String(NUMERIC) + new String(ALPHA)).toCharArray();

  public static void main(final String[] args) {
    new RandomTest().testRandom();
  }

  @Test
  public void testRandom() {
    try {
      Random.alpha(-1);
      fail("Expected IllegalArgumentException!");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Random.alphaNumeric(0);
      fail("Expected IllegalArgumentException!");
    }
    catch (final IllegalArgumentException e) {
    }

    final String alpha = Random.alpha(16);
    System.out.println(alpha);
    assertEquals(16, alpha.length());
    assertInSpace(alpha.toCharArray(), ALPHA);

    final String numeric = Random.numeric(16);
    System.out.println(numeric);
    assertEquals(16, numeric.length());
    assertInSpace(numeric.toCharArray(), NUMERIC);

    final String alphaNumeric = Random.alphaNumeric(16);
    System.out.println(alphaNumeric);
    assertEquals(16, alphaNumeric.length());
    assertInSpace(alphaNumeric.toCharArray(), ALPHA_NUMERIC);
  }

  private void assertInSpace(final char[] chars, final char[] space) {
    for (final char ch : chars)
      assertFalse(Arrays.binarySearch(space, ch) == -1);
  }
}