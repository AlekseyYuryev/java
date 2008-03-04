package org.safris.commons.xml;

import org.junit.Test;

import static org.junit.Assert.*;

public class FormatOptionTest
{
	public static void main(String[] args) throws Exception
	{
		new FormatOptionTest().testConsolidate();
	}

	@Test
	public void testConsolidate()
	{
		assertNull(FormatOption.consolidate(null));

		// Condition: default
		FormatOption option = FormatOption.consolidate();
		assertFalse(option.isIndent());
		assertFalse(option.isIgnoreNamespaces());

		// Condition: indent
		option = FormatOption.consolidate(FormatOption.INDENT);
		assertTrue(option.isIndent());
		assertFalse(option.isIgnoreNamespaces());

		// Condition: ignoreNamespases
		option = FormatOption.consolidate(FormatOption.IGNORE_NAMESPACES);
		assertTrue(option.isIgnoreNamespaces());
		assertFalse(option.isIndent());

		// Condition: indent & ignoreNamespases
		option = FormatOption.consolidate(FormatOption.INDENT, FormatOption.IGNORE_NAMESPACES);
		assertTrue(option.isIgnoreNamespaces());
		assertTrue(option.isIndent());
	}
}
