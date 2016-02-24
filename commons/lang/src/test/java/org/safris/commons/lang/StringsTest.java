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

package org.safris.commons.lang;

import org.junit.Assert;
import org.junit.Test;

public class StringsTest {
  private static final String UPPER_CASE = "HELLO WORLD";
  private static final String LOWER_CASE = "hello world";

  @Test
  public void testGetRandomAlphaString() {
    try {
      Strings.getRandomAlphaString(-1);
      Assert.fail("Expecting an IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    for (int length = 0; length < 100; length++) {
      final String randomAlpha = Strings.getRandomAlphaString(length);
      Assert.assertEquals(randomAlpha.length(), length);
    }
  }

  @Test
  public void testGetRandomAlphaNumericString() {
    try {
      Strings.getRandomAlphaNumericString(-1);
      Assert.fail("Expecting an IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    for (int length = 0; length < 100; length++) {
      final String randomAlpha = Strings.getRandomAlphaNumericString(length);
      Assert.assertEquals(randomAlpha.length(), length);
    }
  }

  @Test
  public void testChangeCase() throws Exception {
    try {
      Strings.toLowerCase(null, 0, 1);
    }
    catch (final Exception e) {
      Assert.assertSame(IllegalArgumentException.class, e.getClass());
    }

    try {
      Strings.toLowerCase("", 0, 0);
    }
    catch (final Exception e) {
      Assert.assertSame(IllegalArgumentException.class, e.getClass());
    }

    try {
      Strings.toLowerCase(UPPER_CASE, 10, 4);
    }
    catch (final Exception e) {
      Assert.assertSame(IllegalArgumentException.class, e.getClass());
    }

    try {
      Strings.toLowerCase(UPPER_CASE, 12, 13);
    }
    catch (final Exception e) {
      Assert.assertSame(StringIndexOutOfBoundsException.class, e.getClass());
    }

    try {
      Strings.toLowerCase(UPPER_CASE, -1, 1);
    }
    catch (final Exception e) {
      Assert.assertSame(StringIndexOutOfBoundsException.class, e.getClass());
    }

    try {
      Strings.toLowerCase(UPPER_CASE, -2, -1);
    }
    catch (final Exception e) {
      Assert.assertSame(StringIndexOutOfBoundsException.class, e.getClass());
    }

    try {
      Strings.toLowerCase(UPPER_CASE, 1, 12);
    }
    catch (final Exception e) {
      Assert.assertSame(StringIndexOutOfBoundsException.class, e.getClass());
    }

    Assert.assertEquals(UPPER_CASE, Strings.toLowerCase(UPPER_CASE, 0, 0));
    Assert.assertEquals("hELLO WORLD", Strings.toLowerCase(UPPER_CASE, 0, 1));
    Assert.assertEquals("HeLLO WORLD", Strings.toLowerCase(UPPER_CASE, 1, 2));
    Assert.assertEquals("HelLO WORLD", Strings.toLowerCase(UPPER_CASE, 1, 3));
    Assert.assertEquals("HELLO WORLd", Strings.toLowerCase(UPPER_CASE, 10, 11));
    Assert.assertEquals("HELLO WORld", Strings.toLowerCase(UPPER_CASE, 9, 11));
    Assert.assertEquals("HELLO WOrld", Strings.toLowerCase(UPPER_CASE, 8));
    Assert.assertEquals("HELLO world", Strings.toLowerCase(UPPER_CASE, 6));

    Assert.assertEquals(LOWER_CASE, Strings.toLowerCase(LOWER_CASE, 0, 0));
    Assert.assertEquals("Hello world", Strings.toUpperCase(LOWER_CASE, 0, 1));
    Assert.assertEquals("hEllo world", Strings.toUpperCase(LOWER_CASE, 1, 2));
    Assert.assertEquals("hELlo world", Strings.toUpperCase(LOWER_CASE, 1, 3));
    Assert.assertEquals("hello worlD", Strings.toUpperCase(LOWER_CASE, 10, 11));
    Assert.assertEquals("hello worLD", Strings.toUpperCase(LOWER_CASE, 9, 11));
    Assert.assertEquals("hello woRLD", Strings.toUpperCase(LOWER_CASE, 8));
    Assert.assertEquals("hello WORLD", Strings.toUpperCase(LOWER_CASE, 6));
  }
}