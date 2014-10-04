/* Copyright (c) 2008 Seva Safris
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
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