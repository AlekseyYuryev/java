package org.safris.xml.toolkit.sample.binding;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleTest extends AbstractTest
{
	public static void main(String[] args)
	{
		new SimpleTest().testExample();
	}

	@Test
	public void testExample()
	{
		assertTrue(verifyBinding(new SimpleExample().runExample()));
	}
}
