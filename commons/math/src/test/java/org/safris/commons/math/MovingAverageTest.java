/*  Copyright Safris Software 2012
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class MovingAverageTest {
  public static void main(final String[] args) {
    new MovingAverageTest().testMovingAverage();
  }

  @Test
  public void testMovingAverage() {
    final double[] vals = new double[] {1, 2, 4, 1, 2, 3, 7};
    final double[] averages = new double[] {
      1.0,
      1.5,
      2.3333333333333335,
      2.0,
      2.0,
      2.1666666666666665,
      2.857142857142857
    };

    final MovingAverage movingAverage = new MovingAverage();
    for (int i = 0; i < vals.length; i++) {
      movingAverage.add(vals[i]);
      assertEquals(averages[i], movingAverage.getAverage(), 0.0001d);
    }
  }
}