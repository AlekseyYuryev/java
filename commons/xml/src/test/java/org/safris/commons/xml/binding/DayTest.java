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

    final String[] days = new String[] {
      "---25Z",
      "---14Z",
      "---03Z",
      "---02Z",
      "---31Z",
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
