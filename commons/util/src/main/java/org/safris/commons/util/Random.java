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

public class Random {
    private static final char[] ALPHA = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] NUMERIC = "0123456789".toCharArray();
    private static final char[] ALPHA_NUMERIC = (new String(NUMERIC) + new String(ALPHA)).toCharArray();

    private static String random(int length, char[] chars) {
        if (length <= 0)
            throw new IllegalArgumentException("length <= 0");

        final char[] random = new char[length];
        for (int i = 0; i < length; i++)
            random[i] = chars[(int)(Math.random() * chars.length)];

        return new String(random);
    }

    public static String alpha(int length) {
        return random(length, ALPHA);
    }

    public static String numeric(int length) {
        return random(length, NUMERIC);
    }

    public static String alphaNumeric(int length) {
        return random(length, ALPHA_NUMERIC);
    }
}
