package org.safris.ant.task.xml.binding;

import org.junit.Test;

import static org.junit.Assert.*;

public class AntPropertyResolverTest
{
	public static void main(String args)
	{
		new AntPropertyResolverTest().testAntPropertyResolver();
	}

	@Test
	public void testAntPropertyResolver()
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
