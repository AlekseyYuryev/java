package org.safris.xml.toolkit.sample.binding;

import org.junit.Test;

import static org.junit.Assert.*;

public class AnyTest extends AbstractTest
{
	public static void main(String[] args)
	{
		new AnyTest().testExample();
	}

	@Test
	public void testExample()
	{
		assertTrue(verifyBinding(new AnyExample().runExample()));
	}
}
