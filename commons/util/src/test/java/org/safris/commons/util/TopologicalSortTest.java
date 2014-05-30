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

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public final class TopologicalSortTest {
  public static void main(final String[] args) {
    new TopologicalSortTest().testSort();
  }

  @Test
  public void testSort() {
    final Map<String,Set<String>> graph = new HashMap<String,Set<String>>();
    graph.put("a", new HashSet<String>(Arrays.asList(new String[]{"b"})));
    graph.put("b", new HashSet<String>(Arrays.asList(new String[]{"c", "d"})));
    graph.put("c", new HashSet<String>(Arrays.asList(new String[]{"d", "e"})));
    graph.put("d", new HashSet<String>(Arrays.asList(new String[]{"e", "f"})));
    graph.put("e", new HashSet<String>(Arrays.asList(new String[]{"g", "i"})));
    graph.put("f", new HashSet<String>(Arrays.asList(new String[]{"g", "h", "i"})));
    graph.put("g", null);
    graph.put("h", null);
    graph.put("i", null);
    final List<String> sorted = TopologicalSort.sort(graph);
    assertArrayEquals(new String[]{"g", "h", "i", "f", "e", "d", "c", "b", "a"}, sorted.toArray());
  }
}