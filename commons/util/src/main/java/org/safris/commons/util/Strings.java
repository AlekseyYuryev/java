package org.safris.commons.util;

public final class Strings
{
	// FIXME: This is not as nice as it could be!
	private static final String changeCase(String string, boolean upper, int beginIndex, int endIndex)
	{
		if(string == null)
			throw new IllegalArgumentException("string is null");

		if(string.length() == 0)
			throw new IllegalArgumentException("string is empty");

		if(beginIndex > endIndex)
			throw new IllegalArgumentException("start (" + beginIndex + ") > end (" + endIndex + ")");

		if(string.length() < beginIndex)
			throw new StringIndexOutOfBoundsException("start index (" + beginIndex + ") > string length (" + string.length() + ")");

		if(endIndex < 0)
			throw new StringIndexOutOfBoundsException("end index (" + endIndex + ") < 0");

		if(beginIndex == endIndex)
			return string;

		if(beginIndex == 0)
		{
			final String caseString = string.substring(beginIndex, endIndex).toLowerCase();
			final String endString = string.substring(endIndex);
			return upper ? caseString.toUpperCase() + endString : caseString.toLowerCase() + endString;
		}

		if(endIndex == string.length())
		{
			final String beginString = string.substring(0, beginIndex);
			final String caseString = string.substring(beginIndex, endIndex).toLowerCase();
			return upper ? beginString + caseString.toUpperCase() : beginString + caseString.toLowerCase();
		}

		final String beginString = string.substring(0, beginIndex);
		final String caseString = string.substring(beginIndex, endIndex).toLowerCase();
		final String endString = string.substring(endIndex);
		return upper ? beginString + caseString.toUpperCase() + endString : beginString + caseString.toLowerCase() + endString;
	}

	public static final String toLowerCase(String string, int beginIndex, int endIndex)
	{
		return changeCase(string, false, beginIndex, endIndex);
	}

	public static final String toLowerCase(String string, int beginIndex)
	{
		return changeCase(string, false, beginIndex, string.length());
	}

	public static final String toUpperCase(String string, int beginIndex, int endIndex)
	{
		return changeCase(string, true, beginIndex, endIndex);
	}

	public static final String toUpperCase(String string, int beginIndex)
	{
		return changeCase(string, true, beginIndex, string.length());
	}

	private Strings()
	{
	}
}
