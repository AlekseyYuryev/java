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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public final class ClassesTest {
  public static void main(final String[] args) throws Exception {
    final ClassesTest classesTest = new ClassesTest();
    classesTest.setUp();
    classesTest.testGreatestCommonClass();
  }

  private final Map<Class<?>[],Class<?>> classes = new HashMap<Class<?>[],Class<?>>();

  @Before
  public void setUp() {
    classes.put(new Class[]{String.class}, String.class);
    classes.put(new Class[]{String.class, Integer.class}, Object.class);
    classes.put(new Class[]{Long.class, Integer.class}, Number.class);
    classes.put(new Class[]{ArrayList.class, LinkedList.class}, AbstractList.class);
    classes.put(new Class[]{HashSet.class, LinkedHashSet.class}, HashSet.class);
    classes.put(new Class[]{FileInputStream.class, ByteArrayInputStream.class, DataInputStream.class, FilterInputStream.class}, InputStream.class);
  }

  @Test
  public void testGreatestCommonClass() throws Exception {
    for (final Map.Entry<Class<?>[],Class<?>> entry : classes.entrySet())
      assertEquals(Classes.getGreatestCommonSuperclass(entry.getKey()), entry.getValue());

    assertNull(Classes.getGreatestCommonSuperclass(null));
  }
}