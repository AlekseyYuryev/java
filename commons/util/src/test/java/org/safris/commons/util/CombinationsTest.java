/*  Copyright Safris Software 2014
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

package org.safris.commons.util;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class CombinationsTest {
  @Test
  public void test() {
    final String[][] in = new String[][] {{"km", "m", "ft"}, {"sec", "min", "hr"}, {"kg", "lb"}};
    final String[][] out = Combinations.<String>combine(in);
    for (final String[] combination : out)
      System.out.println(Arrays.toString(combination));
  }
}