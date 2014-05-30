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