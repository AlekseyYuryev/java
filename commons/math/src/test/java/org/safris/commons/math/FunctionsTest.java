/*  Copyright Safris Software 2008
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

package org.safris.commons.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionsTest {
  public static void main(String[] args) {
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
