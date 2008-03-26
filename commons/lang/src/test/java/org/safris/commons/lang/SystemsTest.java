package org.safris.commons.lang;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class SystemsTest
{
	private static final String NAME = "VAR_DOES_NOT_EXIST";
	private static final String VALUE = "VALUE";

	public static void main(String[] args) throws Exception
	{
		new SystemsTest().testSetenv();
	}

	@Test
	@Ignore
	public void testSetenv()
	{
		assertNull(System.getenv(NAME));
		Systems.setenv(NAME, VALUE);
		assertEquals(VALUE, System.getenv(NAME));
	}
}
