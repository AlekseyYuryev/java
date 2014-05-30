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

package org.safris.commons.lang;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class NumbersTest {
  public static void main(final String[] args) {
    new NumbersTest().testIsNumber();
  }

  @Test
  public void testIsNumber() {
    assertTrue(Numbers.isNumber("0"));
    assertTrue(Numbers.isNumber(" 299792458"));
    assertTrue(Numbers.isNumber("-299792458 "));
    assertTrue(Numbers.isNumber(" 3.14159265"));
    assertTrue(Numbers.isNumber("-3.14159265"));
    assertTrue(Numbers.isNumber("6.022E23 "));
    assertTrue(Numbers.isNumber(" -6.022E23"));
    assertTrue(Numbers.isNumber(" 6.626068E-34"));
    assertTrue(Numbers.isNumber("-6.626068E-34 "));

    assertFalse(Numbers.isNumber(null));
    assertFalse(Numbers.isNumber(""));
    assertFalse(Numbers.isNumber(" "));
    assertFalse(Numbers.isNumber("not number"));
    assertFalse(Numbers.isNumber("3.14.15"));
    assertFalse(Numbers.isNumber("29-97924"));
    assertFalse(Numbers.isNumber("-29-97924"));
    assertFalse(Numbers.isNumber("2 997924"));
    assertFalse(Numbers.isNumber("-29-979.24"));
    assertFalse(Numbers.isNumber("-2.9-979.24"));
    assertFalse(Numbers.isNumber("6.022 E23 "));
    assertFalse(Numbers.isNumber(" -6.022E 23"));
    assertFalse(Numbers.isNumber("-6.626068E--34 "));
    assertFalse(Numbers.isNumber("-6.626068E-3-4 "));
    assertFalse(Numbers.isNumber("-6.626068E-3.4"));
    assertFalse(Numbers.isNumber("-6.626E068E-34"));
  }
}