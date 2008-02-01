package org.safris.xml.toolkit.sample.binding;

import org.junit.Test;

public class EnumsTest extends AbstractTest
{
	public static void main(String[] args)
	{
		new EnumsTest().testExample();
	}

	@Test
	public void testExample()
	{
		verifyBinding(new EnumsExample().runExample());
	}
}
