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

package org.safris.commons.lang;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringsTest {
    private static final String UPPER_CASE = "HELLO WORLD";
    private static final String LOWER_CASE = "hello world";

    public static void main(String[] args) throws Exception {
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
        catch (IllegalArgumentException e) {
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
        catch (IllegalArgumentException e) {
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
        catch (Exception e) {
            assertSame(IllegalArgumentException.class, e.getClass());
        }

        try {
            Strings.toLowerCase("", 0, 0);
        }
        catch (Exception e) {
            assertSame(IllegalArgumentException.class, e.getClass());
        }

        try {
            Strings.toLowerCase(UPPER_CASE, 10, 4);
        }
        catch (Exception e) {
            assertSame(IllegalArgumentException.class, e.getClass());
        }

        try {
            Strings.toLowerCase(UPPER_CASE, 12, 13);
        }
        catch (Exception e) {
            assertSame(StringIndexOutOfBoundsException.class, e.getClass());
        }

        try {
            Strings.toLowerCase(UPPER_CASE, -1, 1);
        }
        catch (Exception e) {
            assertSame(StringIndexOutOfBoundsException.class, e.getClass());
        }

        try {
            Strings.toLowerCase(UPPER_CASE, -2, -1);
        }
        catch (Exception e) {
            assertSame(StringIndexOutOfBoundsException.class, e.getClass());
        }

        try {
            Strings.toLowerCase(UPPER_CASE, 1, 12);
        }
        catch (Exception e) {
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
