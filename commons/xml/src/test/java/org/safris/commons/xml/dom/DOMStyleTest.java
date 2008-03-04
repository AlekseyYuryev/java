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
