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

public final class MovingNormalTest {
  public static void main(final String[] args) {
    new MovingNormalTest().testMovingNormal();
  }

  @Test
  public void testMovingNormal() {
    final double[] vals = new double[] {1, 2, 4, 1, 2, 3, 4, 2};
    final double[] normals = new double[] {
      -1.0,
      1.0,
      1.6329931618554523,
      -0.8164965809277261,
      -0.15617376188860588,
      0.7808688094430303,
      1.462614271203831,
      -0.3375263702778072
    };

    Functions.normalize(vals);
    final MovingNormal movingNormal = new MovingNormal();
    movingNormal.normalize(vals, 0, 2);
    movingNormal.normalize(vals, 2, 4);
    movingNormal.normalize(vals, 4, 6);
    movingNormal.normalize(vals, 6, 8);
    for (int i = 0; i < vals.length; i++)
      assertEquals(normals[i], vals[i], .00001);
  }
}