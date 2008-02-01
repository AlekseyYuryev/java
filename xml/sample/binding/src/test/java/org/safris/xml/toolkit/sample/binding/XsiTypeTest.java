package org.safris.xml.toolkit.sample.binding;

import org.junit.Test;

public class XsiTypeTest extends AbstractTest
{
	public static void main(String[] args)
	{
		new XsiTypeTest().testExample();
	}

	@Test
	public void testExample()
	{
		verifyBinding(new XsiTypeExample().runExample());
	}
}
