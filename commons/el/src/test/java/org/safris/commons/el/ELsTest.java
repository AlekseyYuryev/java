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

package org.safris.commons.el;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.*;

public class ELsTest {
  public static void main(String[] args) {
    new ELsTest().testDereference();
  }

  @Test
  public void testDereference() {
    final Map<String,String> variables = new HashMap<String,String>();
    variables.put("right", "RIGHT");
    variables.put("left", "LEFT");
    variables.put("middle", "MIDDLE");

    final Map<String,String> map = new HashMap<String,String>();
    map.put("this string has a token on the ${right}", "this string has a token on the RIGHT");
    map.put("${left} token here", "LEFT token here");
    map.put("something in the ${middle} of this string", "something in the MIDDLE of this string");

    for (Map.Entry<String,String> entry : map.entrySet())
      assertEquals(ELs.dereference(entry.getKey(), variables), entry.getValue());

    assertNull(ELs.dereference(null, variables));
    assertNull(ELs.dereference(null, null));
    final String same = "string with ${a} variable";
    assertEquals(ELs.dereference(same, null), same);

    try {
      ELs.dereference("expect an ${exception here", variables);
      fail("Expected a ExpressionFormatException");
    }
    catch (ExpressionFormatException e) {
    }
  }
}
