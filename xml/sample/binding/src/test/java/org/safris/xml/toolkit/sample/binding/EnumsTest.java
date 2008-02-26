package org.safris.xml.toolkit.sample.binding;

import org.junit.Test;

import static org.junit.Assert.*;

public class EnumsTest extends AbstractTest
{
	public static void main(String[] args)
	{
		new EnumsTest().testExample();
	}

	@Test
	public void testExample()
	{
		assertTrue(verifyBinding(new EnumsExample().runExample()));
	}
}
