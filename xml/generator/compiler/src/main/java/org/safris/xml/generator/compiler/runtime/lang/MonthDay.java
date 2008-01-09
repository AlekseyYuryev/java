package org.safris.xml.generator.compiler.runtime.lang;

import java.util.StringTokenizer;
import org.safris.xml.generator.compiler.runtime.lang.MonthDay;

public class MonthDay
{
	public static MonthDay parseMonthDay(String string)
	{
		if(string == null || string.length() == 0)
			return null;
		
		int month = 1;
		int day = 1;
		StringTokenizer tokenizer = new StringTokenizer(string.substring(2), "-Z");
		if(tokenizer.hasMoreTokens())
			month = Integer.parseInt(tokenizer.nextToken());
		
		if(tokenizer.hasMoreTokens())
			day = Integer.parseInt(tokenizer.nextToken());
		
		return new MonthDay(month, day);
	}
	
	private final int month;
	private final int day;
	
	public MonthDay(int month, int day)
	{
		this.month = month;
		this.day = day;
	}
}
