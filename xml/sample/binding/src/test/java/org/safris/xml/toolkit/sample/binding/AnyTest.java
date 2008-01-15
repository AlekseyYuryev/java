package org.safris.xml.toolkit.sample.binding;

public class AnyTest extends AbstractTest
{
	public static void main(String[] args)
	{
		new AnyTest().testExample();
	}

	public void testExample()
	{
		verifyBinding(new AnyExample().runExample());
	}
}
