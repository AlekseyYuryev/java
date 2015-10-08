/* Copyright (c) 2012 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.commons.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public final class TopologicalSortTest {
  @Test
  public void testSort() {
    final Map<String,Set<String>> graph = new HashMap<String,Set<String>>();
    graph.put("a", new HashSet<String>(Arrays.asList(new String[] {"b"})));
    graph.put("b", new HashSet<String>(Arrays.asList(new String[] {"c", "d"})));
    graph.put("c", new HashSet<String>(Arrays.asList(new String[] {"d", "e"})));
    graph.put("d", new HashSet<String>(Arrays.asList(new String[] {"e"})));
    graph.put("e", new HashSet<String>(Arrays.asList(new String[] {"f", "g", "h"})));
    graph.put("f", new HashSet<String>(Arrays.asList(new String[] {"h"})));
    graph.put("g", null);
    graph.put("h", null);
    final List<String> sorted = TopologicalSort.sort(graph);
    Assert.assertArrayEquals(new String[] {"g", "h", "f", "e", "d", "c", "b", "a"}, sorted.toArray());
  }
}