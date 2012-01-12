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

public final class MovingNormal {
  private volatile double mean = 0d;
  private volatile double sum = 0d;
  private volatile double sumSq = 0d;
  private volatile double scale = 1d;
  private volatile double count = 0d;

  public void normalize(final double[] values, final int start, final int end) {
    for (int i = start; i < end; i++, count++) {
      sum += values[i];
      sumSq += values[i] * values[i];
    }

    mean = sum / count;
    scale = StrictMath.sqrt((sumSq - sum * mean) / count);
    if (scale == 0d)
      scale = 1d;

    for (int i = start; i < end; i++)
      values[i] = (values[i] - mean) / scale;
  }

  public double getMean() {
    return mean;
  }

  public double getScale() {
    return scale;
  }
}
