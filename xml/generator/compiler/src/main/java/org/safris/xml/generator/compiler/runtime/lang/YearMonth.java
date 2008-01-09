package org.safris.xml.generator.compiler.runtime.lang;

import java.util.StringTokenizer;
import org.safris.xml.generator.compiler.runtime.lang.YearMonth;

public class YearMonth
{
	public static YearMonth parseYearMonth(String string)
	{
		if(string == null || string.length() == 0)
			return null;
		
		int year = 1;
		int month = 1;
		StringTokenizer tokenizer = new StringTokenizer(string, "-");
		if(tokenizer.hasMoreTokens())
			year = Integer.parseInt(tokenizer.nextToken());
		
		if(tokenizer.hasMoreTokens())
			month = Integer.parseInt(tokenizer.nextToken());
		
		return new YearMonth(year, month);
	}
	
	private final int year;
	private final int month;
	
	public YearMonth(int year, int month)
	{
		this.year = year;
		this.month = month;
	}
}
