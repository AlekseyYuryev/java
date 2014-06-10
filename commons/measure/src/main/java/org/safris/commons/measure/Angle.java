/*  Copyright Safris Software 2014
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

package org.safris.commons.measure;

public final class Angle extends Dimension.Measurement<Dimension.Unit> {
  public static class Unit extends Dimension.Unit {
    public static final Unit RAD = new Unit("rad", 1, null);
    public static final Unit DEG = new Unit("deg", Math.PI / 180, Unit.RAD);
    
    protected Unit(final String name, final double factor, final Dimension.Unit basis) {
      super(name, factor, basis);
    }
  }
  
  public Angle(final double value, final Unit unit) {
    super(value, unit);
  }
}