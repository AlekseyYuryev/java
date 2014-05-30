/*  Copyright Safris Software 2008
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

package org.safris.commons.xml.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.TimeZone;

import org.junit.Test;

public final class TimeTest {
  public static void main(final String[] args) {
    new TimeTest().testTime();
  }

  @Test
  public void testTime() {
    try {
      Time.parseTime(null);
      fail("Expected a NullPointerException");
    }
    catch (final NullPointerException e) {
    }

    try {
      Time.parseTime("25:30:10Z");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Time.parseTime("22:60:10");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Time.parseTime("22:59:60");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Time.parseTime("22:59:60.0000Z");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Time.parseTime("2:59:59.99999Z");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Time.parseTime("23:9:59.99999Z");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Time.parseTime("23:59:9.99999Z");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Time.parseTime("23:59:59.99999-15:00");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Time.parseTime("23:59:59.99999+14:60");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Time.parseTime("23:59:59.99999+14:60.9");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
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