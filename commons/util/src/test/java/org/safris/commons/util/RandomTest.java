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
import org.junit.Test;

import static org.junit.Assert.*;

public class RandomTest {
    private static final char[] ALPHA = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] NUMERIC = "0123456789".toCharArray();
    private static final char[] ALPHA_NUMERIC = (new String(NUMERIC) + new String(ALPHA)).toCharArray();

    public static void main(String[] args) throws Exception {
        new RandomTest().testRandom();
    }

    @Test
    public void testRandom() {
        try {
            Random.alpha(-1);
            fail("Expected IllegalArgumentException!");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Random.alphaNumeric(0);
            fail("Expected IllegalArgumentException!");
        }
        catch (IllegalArgumentException e) {
        }

        final String alpha = Random.alpha(16);
        System.out.println(alpha);
        assertEquals(16, alpha.length());
        assertInSpace(alpha.toCharArray(), ALPHA);

        final String numeric = Random.numeric(16);
        System.out.println(numeric);
        assertEquals(16, numeric.length());
        assertInSpace(numeric.toCharArray(), NUMERIC);

        final String alphaNumeric = Random.alphaNumeric(16);
        System.out.println(alphaNumeric);
        assertEquals(16, alphaNumeric.length());
        assertInSpace(alphaNumeric.toCharArray(), ALPHA_NUMERIC);
    }

    private void assertInSpace(char[] chars, char[] space) {
        for (char ch : chars)
            assertFalse(Arrays.binarySearch(space, ch) == -1);
    }
}
