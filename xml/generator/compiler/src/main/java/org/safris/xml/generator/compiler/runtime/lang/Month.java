package org.safris.xml.generator.compiler.runtime.lang;

import java.util.StringTokenizer;
import org.safris.xml.generator.compiler.runtime.lang.Month;

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
