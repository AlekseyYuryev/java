/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.commons.xml.dom;

import org.junit.Test;

import static org.junit.Assert.*;

public class DOMStyleTest
{
	public static void main(String[] args) throws Exception
	{
		new DOMStyleTest().testConsolidate();
	}

	@Test
	public void testConsolidate()
	{
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
