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
 * http://www.w3.org/TR/xmlschema11-2/#gMonth
 */
public class Month
{
	public static Month parseMonth(String string)
	{
		if(string == null)
			throw new NullPointerException("string == null");

		string = string.trim();
		if(!string.startsWith(PAD_FRAG) || string.length() < PAD_FRAG.length() + MONTH_FRAG_MIN_LENGTH)
			throw new IllegalArgumentException(string);

		final int month = parseMonthFrag(string.substring(PAD_FRAG.length()));
		final TimeZone timeZone = Time.parseTimeZoneFrag(string.substring(PAD_FRAG.length() + MONTH_FRAG_MIN_LENGTH));
		return new Month(month, timeZone);
	}

	protected static int parseMonthFrag(String string)
	{
		if(string == null)
			throw new NullPointerException("string == null");

		if(string.length() < MONTH_FRAG_MIN_LENGTH)
			throw new IllegalArgumentException("month == " + string);

		int index = 0;
		final char ch = string.charAt(index);
		final char ch2 = string.charAt(++index);
		if(ch == '0')
		{
			if(ch2 < '1' || '9' < ch2)
				throw new IllegalArgumentException("month == " + string);
		}
		else if(ch == '1')
		{
			if(ch2 < '0' || '2' < ch2)
				throw new IllegalArgumentException("month == " + string);
		}
		else
			throw new IllegalArgumentException("month == " + string);


		final String monthString = "" + ch + ch2;
		int month;
		try
		{
			month = Integer.parseInt(monthString);
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException(e);
		}

		return month;
	}

	protected static int MONTH_FRAG_MIN_LENGTH = 2;
	private static final String PAD_FRAG = "--";

	private final int month;
	private final TimeZone timeZone;

	public Month(int month, TimeZone timeZone)
	{
		this.month = month;
		if(month < 0 || 12 < month)
			throw new IllegalArgumentException("month = " + month);

		this.timeZone = timeZone;
	}

	public Month(int month)
	{
		this(month, null);
	}

	public int getMonth()
	{
		return month;
	}

	public TimeZone getTimeZone()
	{
		return timeZone;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof Month))
			return false;

		final Month that = (Month)obj;
		return this.month == that.month && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
	}

	public int hashCode()
	{
		return month ^ 13 + (timeZone != null ? timeZone.hashCode() : -1);
	}

	public String toString()
	{
		final String string;
		if(month < 10)
			string = "--0" + month;
		else
			string = "--" + month;

		if(timeZone == null)
			return string;

		if(DateTime.GMT.equals(timeZone))
			return string + "Z";

		return string + timeZone.getID().substring(3);
	}
}
