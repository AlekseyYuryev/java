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
