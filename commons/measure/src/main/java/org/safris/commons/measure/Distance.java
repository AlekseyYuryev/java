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

public final class Distance extends Dimension.Scalar<Dimension.Unit> {
  /**
   * Equatorial radius of earth
   */
  public static final Distance R = new Distance(6378.137, Unit.KM);

  public static class Unit extends Dimension.Unit {
    public static final Unit M = new Unit("m", 1, null);
    public static final Unit FT = new Unit("ft", 12 * 0.0254, Unit.M);
    public static final Unit MI = new Unit("mi", 5280, Unit.FT);
    public static final Unit KM = new Unit("km", 1000, Unit.M);
    public static final Unit NM = new Unit("nm", 1852, Unit.M);

    protected Unit(final String name, final double factor, final Dimension.Unit basis) {
      super(name, factor, basis);
    }
  }

  public Distance(final double value, final Unit unit) {
    super(value, unit);
  }

  public Location locate(final Location location, final Angle bearing) {
    final double d = value(Unit.KM) / R.value(Unit.KM);
    final double bearing1 = bearing.value(Angle.Unit.RAD);
    final double lat1 = location.latitude.value(Angle.Unit.RAD);
    final double lon1 = location.longitude.value(Angle.Unit.RAD);
    
    final double lat2 = Math.asin(Math.sin(lat1) * Math.cos(d) + Math.cos(lat1) * Math.sin(d) * Math.cos(bearing1));
    double lon2 = Math.atan2(Math.sin(bearing1) * Math.sin(d) * Math.cos(lat1), Math.cos(d) - Math.sin(lat1) * Math.sin(lat2));
    lon2 = ((lon1 - lon2 + Math.PI) % (2 * Math.PI)) - Math.PI;
    return new Location(new Angle(lat2, Angle.Unit.RAD), new Angle(lon2, Angle.Unit.RAD));
  }
}