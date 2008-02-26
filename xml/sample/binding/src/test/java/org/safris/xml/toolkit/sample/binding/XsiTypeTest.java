package org.safris.xml.toolkit.sample.binding;

import org.junit.Test;

import static org.junit.Assert.*;

public class XsiTypeTest extends AbstractTest
{
	public static void main(String[] args)
	{
		new XsiTypeTest().testExample();
	}

	@Test
	public void testExample()
	{
		assertTrue(verifyBinding(new XsiTypeExample().runExample()));
	}
}
