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

/**
 * http://www.w3.org/TR/xmlschema11-2/#gDay
 */
public class Day
{
	public static Day parseDay(String string)
	{
		if(string == null)
			throw new NullPointerException();

		if(!string.startsWith("---") || string.length() < 5)
			throw new IllegalArgumentException(string);


		int index = 3;
		final char ch = string.charAt(index);
		final char ch2 = string.charAt(++index);
		if(ch == '0')
		{
			if(ch2 < '1' || '9' < ch2)
				throw new IllegalArgumentException(string);
		}
		else if(ch == '1' || ch == '2')
		{
			if(ch2 < '0' || '9' < ch2)
				throw new IllegalArgumentException(string);
		}
		else if(ch == '3')
		{
			if(ch2 < '0' || '1' < ch2)
				throw new IllegalArgumentException(string);
		}
		else
			throw new IllegalArgumentException(string);

		final String dayString = "" + ch + ch2;
		int day;
		try
		{
			day = Integer.parseInt(dayString);
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException(e);
		}

		if(day < 1 || 31 < day)
			throw new IllegalArgumentException(string);

		final TimeZone timeZone;
		if(index < string.length())
			timeZone = Time.parseTimeZone(string.substring(++index));
		else
			timeZone = null;

		return new Day(day, timeZone);
	}

	private final int day;
	private final TimeZone timeZone;

	public Day(int day, TimeZone timeZone)
	{
		this.day = day;
		this.timeZone = timeZone;
	}

	public Day(int day)
	{
		this(day, null);
	}

	public int getDay()
	{
		return day;
	}

	public TimeZone getTimeZone()
	{
		return timeZone;
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(!(obj instanceof Day))
			return false;

		final Day that = (Day)obj;
		return this.day == that.day && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
	}

	public int hashCode()
	{
		return day ^ 17 + (timeZone != null ? timeZone.hashCode() : -1);
	}

	public String toString()
	{
		final String string;
		if(day < 10)
			string = "---0" + day;
		else
			string = "---" + day;

		if(timeZone == null)
			return string;

		if(DateTime.GMT.equals(timeZone))
			return string + "Z";

		return string + timeZone.getID().substring(3);
	}
}
