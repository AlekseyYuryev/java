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

import java.util.TimeZone;
import org.junit.Test;
import org.safris.commons.xml.binding.Time;

import static org.junit.Assert.*;

public class TimeTest {
    public static void main(String[] args) {
        new TimeTest().testTime();
    }

    @Test
    public void testTime() {
        try {
            Time.parseTime(null);
            fail("Expected a NullPointerException");
        }
        catch (NullPointerException e) {
        }

        try {
            Time.parseTime("25:30:10Z");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Time.parseTime("22:60:10");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Time.parseTime("22:59:60");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Time.parseTime("22:59:60.0000Z");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Time.parseTime("2:59:59.99999Z");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Time.parseTime("23:9:59.99999Z");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Time.parseTime("23:59:9.99999Z");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Time.parseTime("23:59:59.99999-15:00");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Time.parseTime("23:59:59.99999+14:60");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Time.parseTime("23:59:59.99999+14:60.9");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        assertEquals(new Time(9, 30, 10, TimeZone.getTimeZone("GMT")), Time.parseTime("09:30:10Z"));
        assertEquals(new Time(1, 23, 45), Time.parseTime("01:23:45"));
        assertEquals(new Time(21, 23, 45.678f), Time.parseTime("21:23:45.678"));
        assertEquals(new Time(21, 23, 45.09999f), Time.parseTime("21:23:45.09999"));
        assertEquals(new Time(24, 0, 0), Time.parseTime("24:00:00.00000"));
        assertEquals(new Time(21, 23, 45.09999f, TimeZone.getTimeZone("GMT")), Time.parseTime("21:23:45.09999Z"));
        assertEquals(new Time(21, 23, 45.09999f, TimeZone.getTimeZone("GMT+2:30")), Time.parseTime("21:23:45.09999+02:30"));
        assertEquals(new Time(21, 23, 45.09999f, TimeZone.getTimeZone("GMT-14:45")), Time.parseTime("21:23:45.09999-14:45"));
    }
}
