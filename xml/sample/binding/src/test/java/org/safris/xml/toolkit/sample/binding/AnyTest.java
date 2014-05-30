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

package org.safris.xml.toolkit.sample.binding;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AnyTest extends AbstractTest {
  public static void main(final String[] args) {
    new AnyTest().testExample();
  }

  @Test
  public void testExample() {
    assertTrue(verifyBinding(new AnyExample().runExample()));
  }
}