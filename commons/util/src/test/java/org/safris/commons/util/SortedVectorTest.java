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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Vector;

import org.junit.Test;

public final class SortedVectorTest {
  public static void main(final String[] args) {
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