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

/**
 * http://www.w3.org/TR/xmlschema11-2/#date
 */
import java.util.TimeZone;

public class Date extends java.util.Date
{
	public static Date parseDate(String string)
	{
		if(string == null)
			throw new NullPointerException("string == null");

		string = string.trim();
		final int year = Year.parseYearFrag(string);
		int index = string.indexOf("-", Year.YEAR_FRAG_MIN_LENGTH);
		final int month = Month.parseMonthFrag(string.substring(index + 1));
		index = string.indexOf("-", Year.YEAR_FRAG_MIN_LENGTH + 1 + Month.MONTH_FRAG_MIN_LENGTH);
		final int day = Day.parseDayFrag(string.substring(index + 1));
		index = string.indexOf("Z", Year.YEAR_FRAG_MIN_LENGTH + 1 + Month.MONTH_FRAG_MIN_LENGTH + 1 + Day.DAY_FRAG_MIN_LENGTH);
		if(index == -1)
			index = string.indexOf("-", Year.YEAR_FRAG_MIN_LENGTH + 1 + Month.MONTH_FRAG_MIN_LENGTH + 1 + Day.DAY_FRAG_MIN_LENGTH);

		if(index == -1)
			index = string.indexOf("+", Year.YEAR_FRAG_MIN_LENGTH + 1 + Month.MONTH_FRAG_MIN_LENGTH + 1 + Day.DAY_FRAG_MIN_LENGTH);

		final TimeZone timeZone;
		if(index != -1)
			timeZone = Time.parseTimeZoneFrag(string.substring(index));
		else
			timeZone = null;

		return new Date(year, month, day, timeZone);
	}

	private final TimeZone timeZone;

	public Date()
	{
		super();
		this.timeZone = null;
    }

    protected Date(String s)
	{
		super(s);
		this.timeZone = null;
	}

	public Date(long date)
	{
		super(date);
		this.timeZone = null;
    }

	public Date(int year, int month, int date, TimeZone timeZone)
	{
		super(year - 1900, month - 1, date);
		this.timeZone = timeZone;
	}

	public Date(int year, int month, int date)
	{
		this(year, month, date, null);
	}

	public int getYear()
	{
		return super.getYear() + 1900;
	}

	public int getMonth()
	{
		return super.getMonth() + 1;
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(!(obj instanceof Date))
			return false;

		final Date that = (Date)obj;
		return this.getYear() == that.getYear() && this.getMonth() == that.getMonth() && this.getDate() == that.getDate() && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
	}

	public int hashCode()
	{
		return getYear() ^ 5 + getMonth() ^ 13 + getDate() ^ 17 + (timeZone != null ? timeZone.hashCode() : -1);
	}

	public String toString()
	{
		final StringBuffer buffer = new StringBuffer();
		final int year = getYear();
		final int month = getMonth();
		final int date = getDate();
		if(year < 10)
			buffer.append("000").append(year);
		else if(year < 100)
			buffer.append("00").append(year);
		else if(year < 1000)
			buffer.append("0").append(year);
		else
			buffer.append(year);

		buffer.append("-");
		if(month < 10)
			buffer.append("0").append(month);
		else
			buffer.append(month);

		buffer.append("-");
		if(date < 10)
			buffer.append("0").append(date);
		else
			buffer.append(date);

		if(timeZone == null)
			return buffer.toString();

		if(DateTime.GMT.equals(timeZone))
			return buffer.append("Z").toString();

		return buffer.append(timeZone.getID().substring(3)).toString();
	}
}
