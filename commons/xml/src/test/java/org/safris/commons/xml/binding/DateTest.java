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

public class DateTest {
    public static void main(String[] args) {
        new DateTest().testDate();
    }

    @Test
    public void testDate() {
        try {
            Date.parseDate(null);
            fail("Expected a NullPointerException");
        }
        catch (NullPointerException e) {
        }

        try {
            Date.parseDate("");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("010");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("10");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("100");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("AAA");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("2227-1");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("2227-1Z");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("2227-10-1");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("2227-10-00Z");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("2227-10-1Z");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("2227-13-08-11:00");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("2227-12-08-15:00");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("2227-01-08+14:60");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("2227-00-08+10:60");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("2227-02-08+14:60.9");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("2227-01-32+12:60");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("2227-02-30+10:60");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Date.parseDate("2227-04-31+12:60.9");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        final String[] dates = new String[]
        {
            "2500-01-01",
            "1400-02-02",
            "0003-03-03",
            "0020-04-04",
            "0310-05-05",
            "1001-06-06Z",
            "2007-07-07+01:00",
            "3017-08-08-01:00",
            "4027-09-09Z",
            "1302-10-10+12:00",
            "1112-11-11-12:30"
        };

        for (String date : dates)
            assertEquals(date, Date.parseDate(date).toString());
    }
}
