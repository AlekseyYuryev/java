/*  Copyright Safris Software 2006
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

package org.safris.ant.task.xml.binding;

import org.junit.Test;

import static org.junit.Assert.*;

public class AntPropertyResolverTest {
  public static void main(String args) {
    new AntPropertyResolverTest().testAntPropertyResolver();
  }

  @Test
  public void testAntPropertyResolver() {
    final AntPropertyResolver antPropertyResolver = new AntPropertyResolver(null);
    assertNull(antPropertyResolver.resolve(null));

    String arg = "text with ${something} here";
    assertEquals(arg, antPropertyResolver.resolve(arg));

    arg = "text with something";
    assertEquals(arg, antPropertyResolver.resolve(arg));

    arg = "text with ${something";
    assertEquals(arg, antPropertyResolver.resolve(arg));
  }
}
