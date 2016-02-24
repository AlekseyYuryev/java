/* Copyright (c) 2006 Seva Safris
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

package org.safris.commons.lang.reflect;

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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClassesTest {
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
      Assert.assertEquals(Classes.getGreatestCommonSuperclass(entry.getKey()), entry.getValue());

    Assert.assertNull(Classes.getGreatestCommonSuperclass((Class<?>[])null));
  }
}