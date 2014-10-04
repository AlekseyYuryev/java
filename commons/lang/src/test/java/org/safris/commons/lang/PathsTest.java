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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public final class PathsTest {
  public static void main(final String[] args) throws Exception {
    final PathsTest pathsTest = new PathsTest();
    pathsTest.testGetName();
    pathsTest.testGetParent();
  }

  @Test
  public void testGetName() throws Exception {
    final Map<String,String> paths = new HashMap<String,String>();
    paths.put("share", "file:///usr/share/../share");
    paths.put("lib", "file:///usr/share/../share/../lib");
    paths.put("var", "/usr/share/../share/../lib/../../var");
    paths.put("var", "/usr/share/../share/../lib/../../var/");
    paths.put("resolv.conf", "/etc/resolv.conf");
    paths.put("name", "name");

    for (final Map.Entry<String,String> entry : paths.entrySet())
      assertEquals(entry.getKey(), Paths.getName(entry.getValue()));

    assertNull(Paths.getName(null));
  }

  @Test
  public void testGetParent() throws Exception {
    final Map<String,String> urls = new HashMap<String,String>();
    urls.put("file:///usr", "file:///usr/share/../share");
    urls.put("/usr", "/usr/share/../share/..");
    urls.put("/", "/usr/share/../share/../../");
    urls.put("file:///usr/local", "file:///usr/local/bin/../lib/../bin");

    for (final Map.Entry<String,String> entry : urls.entrySet())
      assertEquals(entry.getKey(), Paths.getParent((entry.getValue())));

    assertNull(Paths.getParent(null));
  }
}