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

public final class Strings {
    private static final char[] alpha = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] alphaNumeric = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static String getRandomString(int length, boolean alphanumeric) {
        if (length < 0)
            throw new IllegalArgumentException("length = " + length);

        if (length == 0)
            return "";

        final char[] chars;
        if (alphanumeric)
            chars = alphaNumeric;
        else
            chars = alpha;

        final char[] array = new char[length];
        for (int i = 0; i < length; i++)
            array[i] = chars[(int)(Math.random() * chars.length)];

        return new String(array);
    }

    public static String getRandomAlphaNumericString(int length) {
        return getRandomString(length, true);
    }

    public static String getRandomAlphaString(int length) {
        return getRandomString(length, false);
    }

    private static String changeCase(String string, boolean upper, int beginIndex, int endIndex) {
        if (string == null || string.length() == 0)
            return string;

        if (beginIndex > endIndex)
            throw new IllegalArgumentException("start {" + beginIndex + "} > end {" + endIndex + "}");

        if (string.length() < beginIndex)
            throw new StringIndexOutOfBoundsException("start index {" + beginIndex + "} > string length {" + string.length() + "}");

        if (endIndex < 0)
            throw new StringIndexOutOfBoundsException("end index {" + endIndex + "} < 0");

        if (beginIndex == endIndex)
            return string;

        if (beginIndex == 0) {
            final String caseString = string.substring(beginIndex, endIndex).toLowerCase();
            final String endString = string.substring(endIndex);
            return upper ? caseString.toUpperCase() + endString : caseString.toLowerCase() + endString;
        }

        if (endIndex == string.length()) {
            final String beginString = string.substring(0, beginIndex);
            final String caseString = string.substring(beginIndex, endIndex).toLowerCase();
            return upper ? beginString + caseString.toUpperCase() : beginString + caseString.toLowerCase();
        }

        final String beginString = string.substring(0, beginIndex);
        final String caseString = string.substring(beginIndex, endIndex).toLowerCase();
        final String endString = string.substring(endIndex);
        return upper ? beginString + caseString.toUpperCase() + endString : beginString + caseString.toLowerCase() + endString;
    }

    public static String toLowerCase(String string, int beginIndex, int endIndex) {
        return changeCase(string, false, beginIndex, endIndex);
    }

    public static String toLowerCase(String string, int beginIndex) {
        return changeCase(string, false, beginIndex, string.length());
    }

    public static String toUpperCase(String string, int beginIndex, int endIndex) {
        return changeCase(string, true, beginIndex, endIndex);
    }

    public static String toUpperCase(String string, int beginIndex) {
        return changeCase(string, true, beginIndex, string.length());
    }

    public static String getRandomString(int length) {
        return getRandomString(length, false);
    }

    private Strings() {
    }
}
