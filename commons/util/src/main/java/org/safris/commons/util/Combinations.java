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

package org.safris.commons.util;

import java.lang.reflect.Array;

public final class Combinations {
  public static <T>T[][] combine(final T[][] arrays) {
    if (arrays == null)
      return null;
    
    int total = arrays[0].length;
    for (int i = 1; i < arrays.length; i++)
      total *= arrays[i].length;

    final Class<?> componentType = arrays[0].getClass().getComponentType();
    final T[][] combinations = (T[][])Array.newInstance(componentType, total, 0);

    // Generate this combination
    for (; total > 0; total--) {
      final T[] currentSet = (T[])Array.newInstance(componentType, arrays.length);
      int position = total;

      // Pick the required element from each list, and add it to the set.
      for (int i = 0; i < arrays.length; i++) {
        final int length = arrays[i].length;
        currentSet[i] = arrays[i][position % length];
        position /= length;
      }

      combinations[total - 1] = currentSet;
    }
    
    return combinations;
  }
  
  private Combinations() {
  }
}