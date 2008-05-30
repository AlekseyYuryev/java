/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.commons.xml.binding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTime extends Date
{
	private static final String formatNoMillis = "yyyy-MM-dd'T'HH:mm:ss";
	private static final String formatMillis = "yyyy-MM-dd'T'HH:mm:ss.SSS";

	public static DateTime parseDateTime(String string)
	{
		final SimpleDateFormat format;
		final TimeZone timeZone;

		int dot = formatNoMillis.length() - 2;
		if(dot < string.length() && string.charAt(dot) == '.')
		{
			dot += countMillis(string.substring(dot + 1).toCharArray()) + 1;
			format = new SimpleDateFormat(formatMillis);
		}
		else
			format = new SimpleDateFormat(formatNoMillis);

		if(dot < string.length() && (string.charAt(dot) == '+' || string.charAt(dot) == '-'))
			timeZone = TimeZone.getTimeZone("GMT" + string.substring(dot, dot + 3) + string.substring(dot + 3));
		else
			timeZone = TimeZone.getTimeZone("GMT");

		try
		{
			format.setTimeZone(timeZone);
			return new DateTime(format.parse(string.substring(0, dot)).getTime(), timeZone);
		}
		catch(ParseException e)
		{
			IllegalArgumentException illegalArgumentException = new IllegalArgumentException(string);
			illegalArgumentException.setStackTrace(e.getStackTrace());
			throw illegalArgumentException;
		}
	}

	private static int countMillis(char[] value)
	{
		int i;
		for(i = 0; i < value.length; i++)
			if('9' < value[i] || value[i] < '0')
				break;

		return i;
	}

	private TimeZone timeZone = TimeZone.getTimeZone("GMT");

	public DateTime()
	{
		super();
	}

	public DateTime(TimeZone timeZone)
	{
		super();
		this.timeZone = timeZone;
	}

	protected DateTime(String s)
	{
		super(s);
	}

	public DateTime(long date, TimeZone timeZone)
	{
		super(date);
		this.timeZone = timeZone;
	}

	public DateTime(int year, int month, int date, TimeZone timeZone)
	{
		super(year - 1900, month - 1, date, 0, 0, 0);
		this.timeZone = timeZone;
	}

	public DateTime(int year, int month, int date, int hrs, int min, TimeZone timeZone)
	{
		super(year - 1900, month - 1, date, hrs, min, 0);
		this.timeZone = timeZone;
	}

	public DateTime(int year, int month, int date, int hrs, int min, int sec, TimeZone timeZone)
	{
		super(year - 1900, month - 1, date, hrs, min, sec);
		this.timeZone = timeZone;
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
		String dateFormatString = null;
		if((long)(getTime() / 1000) * 1000 == getTime())
			dateFormatString = formatNoMillis;
		else
			dateFormatString = formatMillis;

		if(TimeZone.getTimeZone("GMT").equals(timeZone))
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString + "'Z'");
			dateFormat.setTimeZone(timeZone);
			return dateFormat.format(this);
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString + "Z");
		dateFormat.setTimeZone(timeZone);
		String format = dateFormat.format(this);
		return format.substring(0, format.length() - 2) + ":" + format.substring(format.length() - 2);
	}
}
