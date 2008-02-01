package org.safris.xml.ant.binding;

import junit.framework.TestCase;
import org.junit.Test;

public class AntPropertyResolverTest extends TestCase
{
	public static void main(String args)
	{
	}

	@Test
	public static void testAntPropertyResolver()
	{
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
