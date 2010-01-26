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

public class DayTest {
    public static void main(String[] args) {
        new DayTest().testDay();
    }

    @Test
    public void testDay() {
        try {
            Day.parseDay(null);
            fail("Expected a NullPointerException");
        }
        catch (NullPointerException e) {
        }

        try {
            Day.parseDay("");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Day.parseDay("--5");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Day.parseDay("---A");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Day.parseDay("----4");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Day.parseDay("----4");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Day.parseDay("---0");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Day.parseDay("---32");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Day.parseDay("---00");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Day.parseDay("---31Z-");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Day.parseDay("---1Z-");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Day.parseDay("---27-15:00");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Day.parseDay("---27+14:60");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            Day.parseDay("---27+14:60.9");
            fail("Expected a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
        }

        final String[] days = new String[]
        {
            "---25",
            "---14",
            "---03",
            "---02",
            "---31",
            "---01Z",
            "---07+01:00",
            "---17-01:00",
            "---27Z",
            "---02+12:00",
            "---12-12:30"
        };

        for (String day : days)
            assertEquals(day, Day.parseDay(day).toString());
    }
}
