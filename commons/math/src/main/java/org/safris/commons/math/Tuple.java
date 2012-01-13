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

import java.util.Comparator;

public class Tuple<X extends Number,Y extends Number> {
  public static final Comparator<Tuple> comparatorX = new Comparator<Tuple>() {
    public int compare(Tuple o1, Tuple o2) {
      return Double.compare(o1.x.doubleValue(), o2.x.doubleValue());
    }
  };

  public static final Comparator<Tuple> comparatorY = new Comparator<Tuple>() {
    public int compare(Tuple o1, Tuple o2) {
      return Double.compare(o1.y.doubleValue(), o2.y.doubleValue());
    }
  };

  public final X x;
  public final Y y;

  public Tuple(X x, Y y) {
    this.x = x;
    this.y = y;
  }
}
