package org.safris.xml.generator.compiler.runtime.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.safris.xml.generator.compiler.runtime.lang.Time;

public class Time extends Date
{
	public static Time parseTime(String string)
	{
		try
		{
			return new Time(dateFormat.parse(string).getTime());
		}
		catch(ParseException e)
		{
			IllegalArgumentException illegalArgumentException = new IllegalArgumentException(string);
			illegalArgumentException.setStackTrace(e.getStackTrace());
			throw illegalArgumentException;
		}
	}
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss'Z'");
	
	static
	{
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
	
	public Time()
	{
		super();
    }
	
    public Time(String s)
	{
		super(s);
	}
	
	public Time(long date)
	{
		super(date);
    }
	
	public Time(int hour, int minute, int second)
	{
		super(0, 0, 0, hour, minute, second);
	}
}
