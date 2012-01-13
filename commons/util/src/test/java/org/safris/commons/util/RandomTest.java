/*  Copyright Safris Software 2009
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.commons.util;

import java.util.Arrays;
import org.junit.Test;

import static org.junit.Assert.*;

public class RandomTest {
  private static final char[] ALPHA = "abcdefghijklmnopqrstuvwxyz".toCharArray();
  private static final char[] NUMERIC = "0123456789".toCharArray();
  private static final char[] ALPHA_NUMERIC = (new String(NUMERIC) + new String(ALPHA)).toCharArray();

  public static void main(String[] args) {
    new RandomTest().testRandom();
  }

  @Test
  public void testRandom() {
    try {
      Random.alpha(-1);
      fail("Expected IllegalArgumentException!");
    }
    catch (IllegalArgumentException e) {
    }

    try {
      Random.alphaNumeric(0);
      fail("Expected IllegalArgumentException!");
    }
    catch (IllegalArgumentException e) {
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

  private void assertInSpace(char[] chars, char[] space) {
    for (char ch : chars)
      assertFalse(Arrays.binarySearch(space, ch) == -1);
  }
}
