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

package org.safris.commons.xml.dom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class DOMStyleTest {
  public static void main(final String[] args) throws Exception {
    new DOMStyleTest().testConsolidate();
  }

  @Test
  public void testConsolidate() {
    assertNull(DOMStyle.consolidate(null));

    // Condition: default
    DOMStyle option = DOMStyle.consolidate();
    assertFalse(option.isIndent());
    assertFalse(option.isIgnoreNamespaces());

    // Condition: indent
    option = DOMStyle.consolidate(DOMStyle.INDENT);
    assertTrue(option.isIndent());
    assertFalse(option.isIgnoreNamespaces());

    // Condition: ignoreNamespases
    option = DOMStyle.consolidate(DOMStyle.IGNORE_NAMESPACES);
    assertTrue(option.isIgnoreNamespaces());
    assertFalse(option.isIndent());

    // Condition: indent & ignoreNamespases
    option = DOMStyle.consolidate(DOMStyle.INDENT, DOMStyle.IGNORE_NAMESPACES);
    assertTrue(option.isIgnoreNamespaces());
    assertTrue(option.isIndent());
  }
}