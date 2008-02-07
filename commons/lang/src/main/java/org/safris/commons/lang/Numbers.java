package org.safris.commons.lang;

import java.util.Arrays;

public final class Numbers
{
	private static final char[] validChars = "0123456789".toCharArray();

	// FIXME: This is not an efficient algorithm.
	public static boolean isNumber(String s)
	{
		s = s.trim();
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if(Arrays.binarySearch(validChars, c) < 0)
				if(c != '-' || i != 0)
					if(c != '.' || i != s.lastIndexOf('.'))
						return false;
		}

		return true;
	}

	private Numbers()
	{
	}
}
