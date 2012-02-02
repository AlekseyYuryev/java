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

package org.safris.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TopologicalSort<T> {
  public static <T>List<T> sort(final Map<T,Set<T>> graph) {
    final List<T> sorted = new ArrayList<T>();
    while (true) {
      T key = null;
      for (Map.Entry<T,Set<T>> entry : graph.entrySet()) {
        if (entry.getValue() == null || entry.getValue().size() == 0) {
          key = entry.getKey();
          break;
        }
      }

      if (key == null)
        return sorted;

      sorted.add(key);
      graph.remove(key);
      for (Set<T> value : graph.values())
        if (value != null)
          value.remove(key);
    }
  }
}
