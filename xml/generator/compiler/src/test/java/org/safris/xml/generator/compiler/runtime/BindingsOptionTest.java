package org.safris.xml.generator.compiler.runtime;

import junit.framework.TestCase;
import org.junit.Test;

public class BindingsOptionTest extends TestCase
{
	public static void main(String[] args) throws Exception
	{
		new BindingsOptionTest().testConsolidate();
	}

	@Test
	public static void testConsolidate()
	{
		assertNull(BindingsOption.consolidate(null));

		// Condition: default
		BindingsOption option = BindingsOption.consolidate();
		assertFalse(option.isIndent());
		assertFalse(option.isIgnoreNamespaces());

		// Condition: indent
		option = BindingsOption.consolidate(BindingsOption.INDENT);
		assertTrue(option.isIndent());
		assertFalse(option.isIgnoreNamespaces());

		// Condition: ignoreNamespases
		option = BindingsOption.consolidate(BindingsOption.IGNORE_NAMESPACES);
		assertTrue(option.isIgnoreNamespaces());
		assertFalse(option.isIndent());

		// Condition: indent & ignoreNamespases
		option = BindingsOption.consolidate(BindingsOption.INDENT, BindingsOption.IGNORE_NAMESPACES);
		assertTrue(option.isIgnoreNamespaces());
		assertTrue(option.isIndent());
	}
}
