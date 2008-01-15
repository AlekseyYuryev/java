package org.safris.xml.toolkit.sample.binding;

public class SimpleTest extends AbstractTest
{
	public static void main(String[] args)
	{
		new SimpleTest().testExample();
	}

	public void testExample()
	{
		verifyBinding(new SimpleExample().runExample());
	}
}
