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

package org.safris.commons.lang.reflect;

public final class Classes {
  public static Class<?> getGreatestCommonSuperclass(Class<?> ... classes) {
    if (classes == null || classes.length == 0)
      return null;

    if (classes.length == 1)
      return classes[0];

    Class<?> gcc = getGreatestCommonSuperclass(classes[0], classes[1]);
    for (int i = 2; i < classes.length && gcc != null; i++)
      gcc = getGreatestCommonSuperclass(gcc, classes[i]);

    return gcc;
  }

  private static Class<?> getGreatestCommonSuperclass(Class<?> class1, Class<?> class2) {
    Class<?> super1 = class1;
    do
    {
      Class<?> super2 = class2;
      do
      {
        if (super1.isAssignableFrom(super2))
          return super1;
      }
      while((super2 = super2.getSuperclass()) != null);
    }
    while((super1 = super1.getSuperclass()) != null);

    return null;
  }

  private Classes() {
  }
}
