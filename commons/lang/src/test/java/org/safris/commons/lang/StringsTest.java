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

package org.safris.commons.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.Test;

public final class StringsTest {
  private static final String UPPER_CASE = "HELLO WORLD";
  private static final String LOWER_CASE = "hello world";

  public static void main(final String[] args) throws Exception {
    new StringsTest().testGetRandomAlphaString();
    new StringsTest().testGetRandomAlphaNumericString();
    new StringsTest().testChangeCase();
  }

  @Test
  public void testGetRandomAlphaString() {
    try {
      Strings.getRandomAlphaString(-1);
      fail("Expecting an IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    for (int length = 0; length < 100; length++) {
      final String randomAlpha = Strings.getRandomAlphaString(length);
      assertEquals(randomAlpha.length(), length);
    }
  }

  @Test
  public void testGetRandomAlphaNumericString() {
    try {
      Strings.getRandomAlphaNumericString(-1);
      fail("Expecting an IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    for (int length = 0; length < 100; length++) {
      final String randomAlpha = Strings.getRandomAlphaNumericString(length);
      assertEquals(randomAlpha.length(), length);
    }
  }

  @Test
  public void testChangeCase() throws Exception {
    try {
      Strings.toLowerCase(null, 0, 1);
    }
    catch (final Exception e) {
      assertSame(IllegalArgumentException.class, e.getClass());
    }

    try {
      Strings.toLowerCase("", 0, 0);
    }
    catch (final Exception e) {
      assertSame(IllegalArgumentException.class, e.getClass());
    }

    try {
      Strings.toLowerCase(UPPER_CASE, 10, 4);
    }
    catch (final Exception e) {
      assertSame(IllegalArgumentException.class, e.getClass());
    }

    try {
      Strings.toLowerCase(UPPER_CASE, 12, 13);
    }
    catch (final Exception e) {
      assertSame(StringIndexOutOfBoundsException.class, e.getClass());
    }

    try {
      Strings.toLowerCase(UPPER_CASE, -1, 1);
    }
    catch (final Exception e) {
      assertSame(StringIndexOutOfBoundsException.class, e.getClass());
    }

    try {
      Strings.toLowerCase(UPPER_CASE, -2, -1);
    }
    catch (final Exception e) {
      assertSame(StringIndexOutOfBoundsException.class, e.getClass());
    }

    try {
      Strings.toLowerCase(UPPER_CASE, 1, 12);
    }
    catch (final Exception e) {
      assertSame(StringIndexOutOfBoundsException.class, e.getClass());
    }

    assertEquals(UPPER_CASE, Strings.toLowerCase(UPPER_CASE, 0, 0));
    assertEquals("hELLO WORLD", Strings.toLowerCase(UPPER_CASE, 0, 1));
    assertEquals("HeLLO WORLD", Strings.toLowerCase(UPPER_CASE, 1, 2));
    assertEquals("HelLO WORLD", Strings.toLowerCase(UPPER_CASE, 1, 3));
    assertEquals("HELLO WORLd", Strings.toLowerCase(UPPER_CASE, 10, 11));
    assertEquals("HELLO WORld", Strings.toLowerCase(UPPER_CASE, 9, 11));
    assertEquals("HELLO WOrld", Strings.toLowerCase(UPPER_CASE, 8));
    assertEquals("HELLO world", Strings.toLowerCase(UPPER_CASE, 6));

    assertEquals(LOWER_CASE, Strings.toLowerCase(LOWER_CASE, 0, 0));
    assertEquals("Hello world", Strings.toUpperCase(LOWER_CASE, 0, 1));
    assertEquals("hEllo world", Strings.toUpperCase(LOWER_CASE, 1, 2));
    assertEquals("hELlo world", Strings.toUpperCase(LOWER_CASE, 1, 3));
    assertEquals("hello worlD", Strings.toUpperCase(LOWER_CASE, 10, 11));
    assertEquals("hello worLD", Strings.toUpperCase(LOWER_CASE, 9, 11));
    assertEquals("hello woRLD", Strings.toUpperCase(LOWER_CASE, 8));
    assertEquals("hello WORLD", Strings.toUpperCase(LOWER_CASE, 6));
  }
}