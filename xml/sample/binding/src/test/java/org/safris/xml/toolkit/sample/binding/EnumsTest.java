package org.safris.xml.toolkit.sample.binding;

public class EnumsTest extends AbstractTest
{
	public static void main(String[] args)
	{
		new EnumsTest().testExample();
	}

	public void testExample()
	{
		verifyBinding(new EnumsExample().runExample());
	}
}
