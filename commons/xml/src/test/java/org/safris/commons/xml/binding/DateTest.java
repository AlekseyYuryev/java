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

import org.junit.Test;

public final class DateTest {
  public static void main(final String[] args) {
    new DateTest().testDate();
  }

  @Test
  public void testDate() {
    try {
      Date.parseDate(null);
      fail("Expected a NullPointerException");
    }
    catch (final NullPointerException e) {
    }

    try {
      Date.parseDate("");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("010");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("10");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("100");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("AAA");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("2227-1");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("2227-1Z");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("2227-10-1");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("2227-10-00Z");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("2227-10-1Z");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("2227-13-08-11:00");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("2227-12-08-15:00");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("2227-01-08+14:60");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("2227-00-08+10:60");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("2227-02-08+14:60.9");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("2227-01-32+12:60");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("2227-02-30+10:60");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Date.parseDate("2227-04-31+12:60.9");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    final String[] dates = new String[] {
    "2500-01-01Z", "1400-02-02Z", "0003-03-03Z", "0020-04-04Z", "0310-05-05Z", "1001-06-06Z", "2007-07-07+01:00", "3017-08-08-01:00", "4027-09-09Z", "1302-10-10+12:00", "1112-11-11-12:30"
    };

    for (final String date : dates)
      assertEquals(date, Date.parseDate(date).toString());
  }
}