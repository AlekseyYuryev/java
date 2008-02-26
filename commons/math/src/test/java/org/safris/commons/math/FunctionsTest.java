package org.safris.commons.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionsTest
{
	public static void main(String[] args)
	{
		new FunctionsTest().testLog();
	}

	@Test
	public void testLog()
	{
		assertEquals(0d, Functions.log(0, 2), 0d);
		assertEquals(0d, Functions.log(2, 1), 0d);
		assertEquals(2d, Functions.log(2, 4), 0d);
		assertEquals(Double.POSITIVE_INFINITY, Functions.log(1, 2), 0d);
		assertEquals(Double.NEGATIVE_INFINITY, Functions.log(1, 0), 0d);
	}
}
