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

import org.junit.Assert;
import org.junit.Test;

public class Base64BinaryTest {
  private static void assertEquals(String data, String base64) {
    final Base64Binary base64Binary = new Base64Binary(data.getBytes());
    final String base64String = base64Binary.toString();
    Assert.assertEquals(base64, base64String);
    final Base64Binary unmarshalled = Base64Binary.parseBase64Binary(base64String);
    Assert.assertEquals(data, new String(unmarshalled.getBytes()));
  }

  public static void main(String[] args) {
    new Base64BinaryTest().testBase64Binary();
  }

  @Test
  public void testBase64Binary() {
    assertEquals("Bonjour", "Qm9uam91cg==\n");
    assertEquals("Hello World", "SGVsbG8gV29ybGQ=\n");
    assertEquals("The quick brown fox jumps over the lazy dog", "VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZw==\n");
  }
}
