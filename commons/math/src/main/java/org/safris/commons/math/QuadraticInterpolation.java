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

/**
 * https://ccrma.stanford.edu/~jos/parshl/Peak_Detection_Steps_3.html
 * https://ccrma.stanford.edu/~jos/sasp/Quadratic_Interpolation_Spectral_Peaks.html
 */
public final class QuadraticInterpolation {
  public static double calcP(final double ym1, final double y0, final double yp1) {
    return (yp1 - ym1) / (2d * (2d * y0 - yp1 - ym1));
  }

  public static double calcY(final double ym1, final double y0, final double yp1, final double p) {
    return y0 - 0.25d * (ym1 - yp1) * p;
  }

  private QuadraticInterpolation() {
  }
}
