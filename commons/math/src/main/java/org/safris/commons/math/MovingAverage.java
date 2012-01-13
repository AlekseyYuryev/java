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

public final class MovingAverage {
  private volatile double average = 0d;
  private volatile double count = 0d;

  public void add(final double ... value) {
    if (value == null)
      return;

    average = (average * count + Functions.sum(value)) / (count += value.length);
  }

  public double getAverage() {
    return average;
  }

  public String toString() {
    return String.valueOf(getAverage());
  }
}
