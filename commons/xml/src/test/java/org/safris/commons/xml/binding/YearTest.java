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

package org.safris.commons.xml.binding;

import org.junit.Test;

import static org.junit.Assert.*;

public class YearTest {
    public static void main(String[] args) {
        new YearTest().testYear();
    }

    @Test
    public void testYear() {
        try {
            Year.parseYear(null);
            fail("Expected a NullPointerException");
        }
        catch (NullPointerException e) {
        }

        try {
            Year.parseYear("");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Year.parseYear("--010");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Year.parseYear("010");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Year.parseYear("10");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Year.parseYear("100");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Year.parseYear("AAA");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Year.parseYear("2227-15:00");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Year.parseYear("2227+14:60");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Year.parseYear("2227+14:60.9");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        final String[] years = new String[]
        {
            "2500",
            "1400",
            "0003",
            "0020",
            "0310",
            "1001Z",
            "2007+01:00",
            "3017-01:00",
            "4027Z",
            "1302+12:00",
            "1112-12:30"
        };

        for (String year : years)
            assertEquals(year, Year.parseYear(year).toString());
    }
}
