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

public class MovingAverageTest {
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
