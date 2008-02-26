package org.safris.commons.lang;

import org.junit.Test;

import static org.junit.Assert.*;

public class NumbersTest
{
	public static void main(String[] args)
	{
		new NumbersTest().testIsNumber();
	}

	@Test
	public void testIsNumber()
	{
		assertTrue(Numbers.isNumber("0"));
		assertTrue(Numbers.isNumber(" 299792458"));
		assertTrue(Numbers.isNumber("-299792458 "));
		assertTrue(Numbers.isNumber(" 3.14159265"));
		assertTrue(Numbers.isNumber("-3.14159265"));
		assertTrue(Numbers.isNumber("6.022E23 "));
		assertTrue(Numbers.isNumber(" -6.022E23"));
		assertTrue(Numbers.isNumber(" 6.626068E-34"));
		assertTrue(Numbers.isNumber("-6.626068E-34 "));

		assertFalse(Numbers.isNumber(null));
		assertFalse(Numbers.isNumber(""));
		assertFalse(Numbers.isNumber(" "));
		assertFalse(Numbers.isNumber("not number"));
		assertFalse(Numbers.isNumber("3.14.15"));
		assertFalse(Numbers.isNumber("29-97924"));
		assertFalse(Numbers.isNumber("-29-97924"));
		assertFalse(Numbers.isNumber("2 997924"));
		assertFalse(Numbers.isNumber("-29-979.24"));
		assertFalse(Numbers.isNumber("-2.9-979.24"));
		assertFalse(Numbers.isNumber("6.022 E23 "));
		assertFalse(Numbers.isNumber(" -6.022E 23"));
		assertFalse(Numbers.isNumber("-6.626068E--34 "));
		assertFalse(Numbers.isNumber("-6.626068E-3-4 "));
		assertFalse(Numbers.isNumber("-6.626068E-3.4"));
		assertFalse(Numbers.isNumber("-6.626E068E-34"));
	}
}
