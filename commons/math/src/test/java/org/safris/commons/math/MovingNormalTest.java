/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.commons.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class MovingNormalTest {
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
