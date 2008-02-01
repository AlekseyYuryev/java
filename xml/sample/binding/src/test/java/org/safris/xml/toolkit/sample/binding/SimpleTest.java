package org.safris.xml.toolkit.sample.binding;

import org.junit.Test;

public class SimpleTest extends AbstractTest
{
	public static void main(String[] args)
	{
		new SimpleTest().testExample();
	}

	@Test
	public void testExample()
	{
		verifyBinding(new SimpleExample().runExample());
	}
}
