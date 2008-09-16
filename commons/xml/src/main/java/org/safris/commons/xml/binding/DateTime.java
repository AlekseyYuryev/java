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

import java.util.TimeZone;

public class DateTime
{
	protected static final TimeZone GMT = TimeZone.getTimeZone("GMT");

	public static DateTime parseDateTime(String string)
	{
		if(string == null)
			throw new NullPointerException("string == null");

		string = string.trim();
		if(string.length() < Year.YEAR_FRAG_MIN_LENGTH + 1 + Month.MONTH_FRAG_MIN_LENGTH + 1 + Day.DAY_FRAG_MIN_LENGTH + 1 + Time.HOUR_FRAG_MIN_LENGTH + 1 + Time.MINUTE_FRAG_MIN_LENGTH + 1 + Time.SECOND_FRAG_MIN_LENGTH)
			throw new IllegalArgumentException(string);

		final Date date = Date.parseDateFrag(string);
		final int index = string.indexOf("T", Date.DATE_FRAG_MIN_LENGTH);
		if(index == -1)
			throw new IllegalArgumentException("dateTime == " + string);

		final Time time = Time.parseTime(string.substring(index + 1));
		return new DateTime(date, time);
	}

	private static int countMillis(char[] value)
	{
		int i;
		for(i = 0; i < value.length; i++)
			if('9' < value[i] || value[i] < '0')
				break;

		return i;
	}

	private final Date date;
	private final Time time;

	protected DateTime(Date date, Time time)
	{
		this.date = date;
		this.time = time;
	}

	public DateTime(int year, int month, int day, int hour, int minute, float second, TimeZone timeZone)
	{
		date = new Date(year, month, day);
		time = new Time(hour, minute, second, timeZone);
	}

	public DateTime(int year, int month, int day, int hour, int minute, float second)
	{
		this(year, month, day, hour, minute, second, null);
	}

	public DateTime(long time)
	{
		this(new Date(time), new Time(time));
	}

	public int getYear()
	{
		return date.getYear();
	}

	public int getMonth()
	{
		return date.getMonth();
	}

	public int getDay()
	{
		return date.getDay();
	}

	public int getHour()
	{
		return time.getHour();
	}

	public int getMinute()
	{
		return time.getMinute();
	}

	public float getSecond()
	{
		return time.getSecond();
	}

	public TimeZone getTimeZone()
	{
		return time.getTimeZone();
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(!(obj instanceof DateTime))
			return false;

		final DateTime that = (DateTime)obj;
		return (date != null ? date.equals(that.date) : that.date == null) && (time != null ? time.equals(that.time) : that.time == null);
	}

	public int hashCode()
	{
		return (date != null ? date.hashCode() : -1) + (time != null ? time.hashCode() : -1);
	}

	public String toString()
	{
		final StringBuffer buffer = new StringBuffer();
		if(date != null)
			buffer.append(date);

		buffer.append("T");
		if(time != null)
			buffer.append(time);

		return buffer.toString();
	}
}
