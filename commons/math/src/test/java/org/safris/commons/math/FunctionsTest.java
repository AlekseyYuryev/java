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

package org.safris.commons.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class FunctionsTest {
  public static void main(final String[] args) {
    new FunctionsTest().testLog();
  }

  @Test
  public void testBinaryClosestSearch() {
    final int[] sorted = new int[]{
      1, 3, 5, 9
    };

    assertEquals(0, Functions.binaryClosestSearch(sorted, 0, 0, sorted.length));
    assertEquals(1, Functions.binaryClosestSearch(sorted, 2, 0, sorted.length));
    assertEquals(2, Functions.binaryClosestSearch(sorted, 4, 0, sorted.length));
    assertEquals(3, Functions.binaryClosestSearch(sorted, 6, 0, sorted.length));
    assertEquals(3, Functions.binaryClosestSearch(sorted, 9, 0, sorted.length));
    assertEquals(4, Functions.binaryClosestSearch(sorted, 10, 0, sorted.length));
  }

  @Test
  public void testLog() {
    assertEquals(0d, Functions.log(0, 2), 0d);
    assertEquals(0d, Functions.log(2, 1), 0d);
    assertEquals(2d, Functions.log(2, 4), 0d);
    assertEquals(Double.POSITIVE_INFINITY, Functions.log(1, 2), 0d);
    assertEquals(Double.NEGATIVE_INFINITY, Functions.log(1, 0), 0d);
  }
}