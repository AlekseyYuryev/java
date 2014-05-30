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

public final class YearMonthTest {
  public static void main(final String[] args) {
    new YearMonthTest().testYearMonth();
  }

  @Test
  public void testYearMonth() {
    try {
      YearMonth.parseYearMonth(null);
      fail("Expected a NullPointerException");
    }
    catch (final NullPointerException e) {
    }

    try {
      YearMonth.parseYearMonth("");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      YearMonth.parseYearMonth("010");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      YearMonth.parseYearMonth("10");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      YearMonth.parseYearMonth("100");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      YearMonth.parseYearMonth("AAA");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      YearMonth.parseYearMonth("2227-1");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      YearMonth.parseYearMonth("2227-1Z");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      YearMonth.parseYearMonth("2227-13-11:00");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      YearMonth.parseYearMonth("2227-12-15:00");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      YearMonth.parseYearMonth("2227-01+14:60");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      YearMonth.parseYearMonth("2227-00+10:60");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      YearMonth.parseYearMonth("2227-02+14:60.9");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    final String[] yearMonths = new String[] {
    "2500-01Z", "1400-02Z", "0003-03Z", "0020-04Z", "0310-05Z", "1001-06Z", "2007-07+01:00", "3017-08-01:00", "4027-09Z", "1302-10+12:00", "1112-11-12:30"
    };

    for (final String yearMonth : yearMonths)
      assertEquals(yearMonth, YearMonth.parseYearMonth(yearMonth).toString());
  }
}