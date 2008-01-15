package org.safris.xml.toolkit.sample.binding;

public class XsiTypeTest extends AbstractTest
{
	public static void main(String[] args)
	{
		new XsiTypeTest().testExample();
	}

	public void testExample()
	{
		verifyBinding(new XsiTypeExample().runExample());
	}
}
