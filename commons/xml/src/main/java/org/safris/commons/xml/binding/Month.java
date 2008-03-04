package org.safris.commons.xml.binding;

import java.util.StringTokenizer;

public class Month
{
	public static Month parseMonth(String string)
	{
		if(string == null || string.length() == 0)
			return null;

		int month = 1;
		StringTokenizer tokenizer = new StringTokenizer(string.substring(2), "-");
		if(tokenizer.hasMoreTokens())
			month = Integer.parseInt(tokenizer.nextToken());

		return new Month(month);
	}

	private final int month;

	public Month(int month)
	{
		this.month = month;
	}
}
