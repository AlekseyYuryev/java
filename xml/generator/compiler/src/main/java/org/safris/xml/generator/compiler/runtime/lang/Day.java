package org.safris.xml.generator.compiler.runtime.lang;

import org.safris.xml.generator.compiler.runtime.lang.Day;

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
