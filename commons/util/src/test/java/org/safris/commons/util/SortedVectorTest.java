/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.commons.util;

import java.util.Arrays;
import java.util.Vector;
import org.junit.Test;

import static org.junit.Assert.*;

public class SortedVectorTest {
  public static void main(String[] args) {
    new SortedVectorTest().testSortedVector();
  }

  @Test
  public void testSortedVector() {
    final Vector<String> vector = new SortedVector<String>();
    vector.addElement("f");
    assertEquals(new String[]{"f"}, vector.toArray());
    vector.addElement("b");
    assertEquals(new String[]{"b", "f"}, vector.toArray());
    vector.addElement("g");
    assertEquals(new String[]{"b", "f", "g"}, vector.toArray());
    vector.addElement("c");
    assertEquals(new String[]{"b", "c", "f", "g"}, vector.toArray());
    vector.addElement("a");
    assertEquals(new String[]{"a", "b", "c", "f", "g"}, vector.toArray());
    vector.addElement("d");
    assertEquals(new String[]{"a", "b", "c", "d", "f", "g"}, vector.toArray());
    vector.addElement("e");
    assertEquals(new String[]{"a", "b", "c", "d", "e", "f", "g"}, vector.toArray());
    vector.remove("c");
    assertEquals(new String[]{"a", "b", "d", "e", "f", "g"}, vector.toArray());
    vector.set(0, "d");
    assertEquals(new String[]{"b", "d", "d", "e", "f", "g"}, vector.toArray());
    vector.set(4, "h");
    assertEquals(new String[]{"b", "d", "d", "e", "g", "h"}, vector.toArray());
    vector.retainAll(Arrays.<String>asList(new String[]{"a", "d", "f", "g", "h"}));
    assertEquals(new String[]{"d", "d", "g", "h"}, vector.toArray());
    vector.retainAll(Arrays.<String>asList(new String[]{"a", "d", "d", "d", "h"}));
    assertEquals(new String[]{"d", "d", "h"}, vector.toArray());
  }
}
