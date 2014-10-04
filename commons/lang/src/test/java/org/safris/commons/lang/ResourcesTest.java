/* Copyright (c) 2008 Seva Safris
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

package org.safris.commons.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Enumeration;

import org.junit.Test;

public final class ResourcesTest {
  private static final File JAVA_HOME = new File(System.getProperty("java.home").replace(" ", "%20"));
  private static final File RT_JAR;

  static {
    try {
      if (System.getProperty("os.name").contains("Mac"))
        RT_JAR = new File(JAVA_HOME, "../jre/lib/rt.jar").getCanonicalFile();
      else
        RT_JAR = new File(JAVA_HOME, "lib/rt.jar");
    }
    catch (final Exception e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  public static void main(final String[] args) throws Exception {
    final ResourcesTest resourcesTest = new ResourcesTest();
    resourcesTest.testGetLocationBase();
    resourcesTest.testGetResource();
    resourcesTest.testGetResources();
  }

  @Test
  public void testGetLocationBase() {
    assertNull(Resources.getLocationBase(null));
    assertTrue(Resources.getLocationBase(ResourcesTest.class).isDirectory());
    assertEquals(RT_JAR, Resources.getLocationBase(String.class));
  }

  @Test
  public void testGetResource() throws Exception {
    assertNull(Resources.getResource(null));
    assertNull(Resources.getResource(""));
    assertTrue(Resources.getResource("META-INF").getURL().toString().endsWith(".jar!/META-INF"));
  }

  @Test
  public void testGetResources() throws Exception {
    assertNull(Resources.getResources(null));
    assertNull(Resources.getResources(""));
    final Enumeration<Resource> resources = Resources.getResources("META-INF");
    boolean found = false;
    while (resources.hasMoreElements()) {
      final Resource resource = resources.nextElement();
      if (!resource.getURL().toString().endsWith("!/META-INF"))
        continue;

      found = true;
      break;
    }

    assertTrue(found);
  }
}