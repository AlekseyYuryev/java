package org.safris.commons.xml.binding;

public class Day
{
	public static Day parseDay(String string)
	{
		return new Day(Integer.parseInt(string.substring(3)));
	}

	private final int day;

	public Day(int day)
	{
		this.day = day;
	}
}
