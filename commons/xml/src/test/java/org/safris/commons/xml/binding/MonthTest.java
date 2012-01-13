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

import org.junit.Test;

import static org.junit.Assert.*;

public class MonthTest {
  public static void main(String[] args) {
    new MonthTest().testMonth();
  }

  @Test
  public void testMonth() {
    try {
      Month.parseMonth(null);
      fail("Expected a NullPointerException");
    }
    catch (NullPointerException e) {
    }

    try {
      Month.parseMonth("");
      fail("Expected a IllegalArgumentException");
    }
    catch (IllegalArgumentException e) {
    }

    try {
      Month.parseMonth("---5");
      fail("Expected a IllegalArgumentException");
    }
    catch (IllegalArgumentException e) {
    }

    try {
      Month.parseMonth("-5");
      fail("Expected a IllegalArgumentException");
    }
    catch (IllegalArgumentException e) {
    }

    try {
      Month.parseMonth("--A");
      fail("Expected a IllegalArgumentException");
    }
    catch (IllegalArgumentException e) {
    }

    try {
      Month.parseMonth("--00");
      fail("Expected a IllegalArgumentException");
    }
    catch (IllegalArgumentException e) {
    }

    try {
      Month.parseMonth("--13");
      fail("Expected a IllegalArgumentException");
    }
    catch (IllegalArgumentException e) {
    }

    try {
      Month.parseMonth("--4");
      fail("Expected a IllegalArgumentException");
    }
    catch (IllegalArgumentException e) {
    }

    try {
      Month.parseMonth("--11Z-");
      fail("Expected a IllegalArgumentException");
    }
    catch (IllegalArgumentException e) {
    }

    try {
      Month.parseMonth("--12-15:00");
      fail("Expected a IllegalArgumentException");
    }
    catch (IllegalArgumentException e) {
    }

    try {
      Month.parseMonth("--07+14:60");
      fail("Expected a IllegalArgumentException");
    }
    catch (IllegalArgumentException e) {
    }

    try {
      Month.parseMonth("--02+14:60.9");
      fail("Expected a IllegalArgumentException");
    }
    catch (IllegalArgumentException e) {
    }

    final String[] months = new String[] {
      "--12Z",
      "--04Z",
      "--03Z",
      "--02Z",
      "--01Z",
      "--07+01:00",
      "--09-01:00",
      "--10Z",
      "--11+12:00",
      "--12-12:30"
    };

    for (String month : months)
      assertEquals(month, Month.parseMonth(month).toString());
  }
}
