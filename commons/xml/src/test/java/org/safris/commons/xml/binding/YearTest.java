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

public final class YearTest {
  public static void main(final String[] args) {
    new YearTest().testYear();
  }

  @Test
  public void testYear() {
    try {
      Year.parseYear(null);
      fail("Expected a NullPointerException");
    }
    catch (final NullPointerException e) {
    }

    try {
      Year.parseYear("");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Year.parseYear("--010");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Year.parseYear("010");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Year.parseYear("10");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Year.parseYear("100");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Year.parseYear("AAA");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Year.parseYear("2227-15:00");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Year.parseYear("2227+14:60");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Year.parseYear("2227+14:60.9");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    final String[] years = new String[] {
    "2500Z", "1400Z", "0003Z", "0020Z", "0310Z", "1001Z", "2007+01:00", "3017-01:00", "4027Z", "1302+12:00", "1112-12:30"
    };

    for (final String year : years)
      assertEquals(year, Year.parseYear(year).toString());
  }
}