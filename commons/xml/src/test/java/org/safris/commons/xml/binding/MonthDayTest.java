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

public final class MonthDayTest {
  public static void main(final String[] args) {
    new MonthDayTest().testMonthDay();
  }

  @Test
  public void testMonthDay() {
    try {
      MonthDay.parseMonthDay(null);
      fail("Expected a NullPointerException");
    }
    catch (final NullPointerException e) {
    }

    try {
      MonthDay.parseMonthDay("");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("---5");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("-5-30");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--A");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--00");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--13");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--4");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--04-32");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--04-00");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--04-7");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--02-30");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--04-31");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--06-31");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--09-31");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--11Z-");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--12-30-15:00");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--07-12+14:60");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      MonthDay.parseMonthDay("--02-01+14:60.9");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    final String[] monthDays = new String[] {
    "--12-31Z", "--04-30Z", "--03-31Z", "--02-29Z", "--01-31Z", "--01-12Z", "--07-02+01:00", "--09-12-01:00", "--10-11Z", "--11-15+12:00", "--12-17-12:30"
    };

    for (final String monthDay : monthDays)
      assertEquals(monthDay, MonthDay.parseMonthDay(monthDay).toString());
  }
}