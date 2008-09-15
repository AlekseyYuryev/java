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

public class Year
{
	public static Year parseYear(String string)
	{
		final int year = parseYearFrag(string);
		int index = string.indexOf("Z", YEAR_FRAG_MIN_LENGTH);
		if(index == -1)
			index = string.indexOf("-", YEAR_FRAG_MIN_LENGTH);

		if(index == -1)
			index = string.indexOf("+", YEAR_FRAG_MIN_LENGTH);

		final TimeZone timeZone;
		if(index != -1)
			timeZone = Time.parseTimeZoneFrag(string.substring(index));
		else
			timeZone = null;

		return new Year(year, timeZone);
	}

	protected static int parseYearFrag(String string)
	{
		if(string == null)
			throw new NullPointerException("string == null");

		if(string.length() == 0)
			throw new IllegalArgumentException(string);

		int index = string.indexOf("Z", YEAR_FRAG_MIN_LENGTH);
		if(index == -1)
			index = string.indexOf("-", YEAR_FRAG_MIN_LENGTH);

		if(index == -1)
			index = string.indexOf("+", YEAR_FRAG_MIN_LENGTH);

		if(index != -1)
			string = string.substring(0, index);

		if(string.length() < YEAR_FRAG_MIN_LENGTH)
			throw new IllegalArgumentException(string);

		try
		{
			return Integer.parseInt(string);
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException(string, e);
		}
	}

	protected static int YEAR_FRAG_MIN_LENGTH = 4;

	private final int year;
	private final TimeZone timeZone;

	public Year(int year, TimeZone timeZone)
	{
		this.year = year;
		this.timeZone = timeZone;
	}

	public Year(int year)
	{
		this(year, null);
	}

	public int getYear()
	{
		return year;
	}

	public TimeZone getTimeZone()
	{
		return timeZone;
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(!(obj instanceof Year))
			return false;

		final Year that = (Year)obj;
		return this.year == that.year && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
	}

	public int hashCode()
	{
		return year ^ 5 + (timeZone != null ? timeZone.hashCode() : -1);
	}

	public String toString()
	{
		final String string;
		if(year < 10)
			string = "000" + String.valueOf(year);
		else if(year < 100)
			string = "00" + String.valueOf(year);
		else if(year < 1000)
			string = "0" + String.valueOf(year);
		else
			string = String.valueOf(year);

		if(timeZone == null)
			return string;

		if(DateTime.GMT.equals(timeZone))
			return string + "Z";

		return string + timeZone.getID().substring(3);
	}
}
