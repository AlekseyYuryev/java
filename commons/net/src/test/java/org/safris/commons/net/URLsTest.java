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

package org.safris.commons.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public final class URLsTest {
  public static void main(final String[] args) throws Exception {
    final URLsTest urlsTest = new URLsTest();
    urlsTest.testIsAbsolute();
    urlsTest.testMakeUrlFromPath();
    urlsTest.testToExternalForm();
    urlsTest.testExists();
    urlsTest.testCanonicalizeURL();
    urlsTest.testGetName();
    urlsTest.testGetParent();
  }

  @Test
  public void testIsAbsolute() throws Exception {
    assertTrue(URLs.isAbsolute("c:\\Windows"));
    assertTrue(URLs.isAbsolute("file:///c:/autoexec.bat"));
    assertTrue(URLs.isAbsolute("/usr/share"));
    assertTrue(URLs.isAbsolute("file:///etc/resolv.conf"));
    assertTrue(URLs.isAbsolute("http://www.google.com/"));

    assertFalse(URLs.isAbsolute(".bashrc"));
    assertFalse(URLs.isAbsolute("Thumbs.db"));

    try {
      URLs.isAbsolute(null);
      fail("Expected a NullPointerException");
    }
    catch (final NullPointerException e) {
    }
  }

  @Test
  public void testMakeUrlFromPath() throws Exception {
    final Map<URL,String> absolute = new HashMap<URL,String>();
    final Map<URL,String[]> relative = new HashMap<URL,String[]>();
    if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
      absolute.put(new URL("file", "", "/c:/Windows"), "c:\\Windows");
      relative.put(new URL("file", "", "/c:/Windows/system32"), new String[]{"c:\\Windows", "system32"});
      relative.put(new URL("file", "", "/c:/Windows/system32"), new String[]{"c:\\Windows", "\\system32"});
      relative.put(new URL("file", "", "/c:/Windows/system32"), new String[]{"c:\\Windows\\", "system32"});
      relative.put(new URL("file", "", "/c:/Windows/system32"), new String[]{"c:\\Windows\\", "\\system32"});
      relative.put(new URL("file", "", "/c:/Windows/system32"), new String[]{"\\c:\\Windows", "system32"});
      relative.put(new URL("file", "", "/c:/Windows/system32"), new String[]{"\\c:\\Windows", "\\system32"});
      relative.put(new URL("file", "", "/c:/Windows/system32"), new String[]{"\\c:\\Windows\\", "system32"});
      relative.put(new URL("file", "", "/c:/Windows/system32"), new String[]{"\\c:\\Windows\\", "\\system32"});
    }
    else {
      absolute.put(new URL("file", "", "/etc/resolv.conf"), "/etc/resolv.conf");
      absolute.put(new URL("file", "", "/initrd.img"), "initrd.img");
      relative.put(new URL("file", "", "/etc/resolv.conf"), new String[]{"", "etc/resolv.conf"});
      relative.put(new URL("file", "", "/etc/resolv.conf"), new String[]{"", "/etc/resolv.conf"});
      relative.put(new URL("file", "", "/etc/resolv.conf"), new String[]{"etc/resolv.conf", ""});
      relative.put(new URL("file", "", "/etc/resolv.conf"), new String[]{"/etc/resolv.conf", ""});
      relative.put(new URL("file", "", "/etc/resolv.conf"), new String[]{"etc", "resolv.conf"});
      relative.put(new URL("file", "", "/etc/resolv.conf"), new String[]{"etc", "/resolv.conf"});
      relative.put(new URL("file", "", "/etc/resolv.conf"), new String[]{"etc/", "resolv.conf"});
      relative.put(new URL("file", "", "/etc/resolv.conf"), new String[]{"etc/", "/resolv.conf"});
      relative.put(new URL("file", "", "/etc/resolv.conf"), new String[]{"/etc", "resolv.conf"});
      relative.put(new URL("file", "", "/etc/resolv.conf"), new String[]{"/etc", "/resolv.conf"});
      relative.put(new URL("file", "", "/etc/resolv.conf"), new String[]{"/etc/", "resolv.conf"});
      relative.put(new URL("file", "", "/etc/resolv.conf"), new String[]{"/etc/", "/resolv.conf"});
    }

    absolute.put(new URL("http://www.google.com/webhp"), "http://www.google.com/webhp");
    relative.put(new URL("http://www.google.com/webhp"), new String[]{"", "http://www.google.com/webhp"});
    relative.put(new URL("http://www.google.com/webhp"), new String[]{"http://www.google.com/webhp", ""});
    relative.put(new URL("http://www.google.com/webhp"), new String[]{"http://www.google.com/", "webhp"});
    relative.put(new URL("http://www.google.com/webhp"), new String[]{"http://www.google.com", "webhp"});
    relative.put(new URL("http://www.google.com/webhp"), new String[]{"http://www.google.com", "/webhp"});
    relative.put(new URL("http://www.google.com/webhp"), new String[]{"http://www.google.com/", "/webhp"});
    for (final Map.Entry<URL,String> entry : absolute.entrySet())
      assertEquals(entry.getKey(), URLs.makeUrlFromPath(entry.getValue()));

    for (final Map.Entry<URL,String[]> entry : relative.entrySet())
      assertEquals(entry.getKey(), URLs.makeUrlFromPath(entry.getValue()[0], entry.getValue()[1]));

    assertNull(URLs.makeUrlFromPath(null));
    assertNull(URLs.makeUrlFromPath((String)null, null));
  }

  @Test
  public void testToExternalForm() throws Exception {
    assertEquals(URLs.toExternalForm(new URL("http://www.google.com/webhp")), "http://www.google.com/webhp");
    try {
      URLs.toExternalForm(new URL("fbiy384ehd"));
      fail("Expected a MalformedURLException");
    }
    catch (final MalformedURLException e) {
    }
  }

  @Test
  public void testExists() throws Exception {
    if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
      assertTrue(URLs.exists(new URL("file", "", "/c:/")));
    else
      assertTrue(URLs.exists(new URL("file", "", "/usr")));

    // FIXME: Some machines may not be connected to the web!
//      assertTrue(URLs.exists(new URL("http://www.google.com/")));

    assertFalse(URLs.exists(new URL("file", "", "/ngfodbbgfid")));
    assertFalse(URLs.exists(new URL("http://fndos.grnoe.dfsn/")));
  }

  @Test
  public void testCanonicalizeURL() throws Exception {
    final Map<URL,URL> map = new HashMap<URL,URL>();
    map.put(new URL("file:///usr/share"), new URL("file:///usr/share/../share"));
    map.put(new URL("file:///usr/lib"), new URL("file:///usr/share/../share/../lib"));
    map.put(new URL("file:///var"), new URL("file:///usr/share/../share/../lib/../../var"));

    for (final Map.Entry<URL,URL> entry : map.entrySet())
      assertEquals(entry.getKey(), URLs.canonicalizeURL(entry.getValue()));

    assertNull(URLs.canonicalizeURL(null));
  }

  @Test
  public void testGetName() throws Exception {
    final Map<String,URL> urls = new HashMap<String,URL>();
    urls.put("share", new URL("file:///usr/share/../share"));
    urls.put("lib", new URL("file:///usr/share/../share/../lib"));
    urls.put("var", new URL("file:///usr/share/../share/../lib/../../var"));
    urls.put("resolv.conf", new URL("file:///etc/resolv.conf"));

    for (final Map.Entry<String,URL> entry : urls.entrySet())
      assertEquals(entry.getKey(), URLs.getName(entry.getValue()));

    assertNull(URLs.canonicalizeURL(null));
  }

  @Test
  public void testGetParent() throws Exception {
    final Map<URL,URL> urls = new HashMap<URL,URL>();
    urls.put(new URL("file:///usr"), new URL("file:///usr/share/../share"));
    urls.put(new URL("file:///usr/local"), new URL("file:///usr/local/bin/../lib/../bin"));

    for (final Map.Entry<URL,URL> entry : urls.entrySet())
      assertEquals(entry.getKey(), URLs.getParent((entry.getValue())));

    assertNull(URLs.getParent(null));
  }
}