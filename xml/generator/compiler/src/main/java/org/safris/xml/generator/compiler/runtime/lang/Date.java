package org.safris.xml.generator.compiler.runtime.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.safris.xml.generator.compiler.runtime.lang.Date;

public class Date extends java.util.Date
{
	public static Date parseDate(String string)
	{
		try
		{
			return new Date(dateFormat.parse(string).getTime());
		}
		catch(ParseException e)
		{
			IllegalArgumentException illegalArgumentException = new IllegalArgumentException(string);
			illegalArgumentException.setStackTrace(e.getStackTrace());
			throw illegalArgumentException;
		}
	}
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public Date()
	{
		super();
    }
	
    protected Date(String s)
	{
		super(s);
	}
	
	public Date(long date)
	{
		super(date);
    }
	
	public Date(int year, int month, int date)
	{
		super(year - 1900, month - 1, date);
	}
	
	public int getYear()
	{
		return super.getYear() + 1900;
	}
	
	public int getMonth()
	{
		return super.getMonth() + 1;
	}
	
	public String toString()
	{
		return dateFormat.format(this);
	}
}
