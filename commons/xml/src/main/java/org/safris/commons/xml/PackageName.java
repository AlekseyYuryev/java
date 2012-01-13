/*  Copyright Safris Software 2006
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

package org.safris.commons.xml;

public class PackageName {
  private final String packageName;

  public PackageName(String packageName) {
  if (packageName == null)
    throw new NullPointerException("packageName == null");

    this.packageName = packageName;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof PackageName))
      return false;

    return packageName.equals(((PackageName)obj).packageName);
  }

  public int hashCode() {
    return packageName.hashCode();
  }

  public String toString() {
    return packageName;
  }
}
