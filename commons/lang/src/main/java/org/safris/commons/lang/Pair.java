/*  Copyright Safris Software 2008
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

package org.safris.commons.lang;

public class Pair<A,B> {
  public final A a;
  public final B b;

  public Pair(final A a, final B b) {
    this.a = a;
    this.b = b;
  }

  public boolean equals(final Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof Pair))
      return false;

    final Pair that = (Pair)obj;
    return (a != null ? a.equals(that.a) : that.a == null) && (b != null ? b.equals(that.b) : that.b == null);
  }

  public int hashCode() {
    return (a != null ? a.hashCode() : 0) + (b != null ? b.hashCode() : 0);
  }
}