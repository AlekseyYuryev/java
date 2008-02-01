package org.safris.xml.toolkit.sample.binding;

import org.junit.Test;

public class AnyTest extends AbstractTest
{
	public static void main(String[] args)
	{
		new AnyTest().testExample();
	}

	@Test
	public void testExample()
	{
		verifyBinding(new AnyExample().runExample());
	}
}
