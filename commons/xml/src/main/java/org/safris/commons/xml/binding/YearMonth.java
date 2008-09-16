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
 * http://www.w3.org/TR/xmlschema11-2/#gYearMonth
 */
public class YearMonth
{
	public static YearMonth parseYearMonth(String string)
	{
		if(string == null)
			throw new NullPointerException("string == null");

		string = string.trim();
		if(string.length() < YEAR_MONTH_FRAG_MIN_LENGTH)
			throw new IllegalArgumentException("year-month == " + string);

		final int year = Year.parseYearFrag(string);
		int index = string.indexOf("-", Year.YEAR_FRAG_MIN_LENGTH);
		final int month = Month.parseMonthFrag(string.substring(index + 1));
		index = string.indexOf("Z", YEAR_MONTH_FRAG_MIN_LENGTH);
		if(index == -1)
			index = string.indexOf("-", YEAR_MONTH_FRAG_MIN_LENGTH);

		if(index == -1)
			index = string.indexOf("+", YEAR_MONTH_FRAG_MIN_LENGTH);

		final TimeZone timeZone;
		if(index != -1)
			timeZone = Time.parseTimeZoneFrag(string.substring(index));
		else
			timeZone = null;

		return new YearMonth(year, month, timeZone);
	}

	protected static final int YEAR_MONTH_FRAG_MIN_LENGTH = Year.YEAR_FRAG_MIN_LENGTH + 1 + Month.MONTH_FRAG_MIN_LENGTH;

	private final Year year;
	private final Month month;
	private final TimeZone timeZone;

	protected YearMonth(Year year, Month month)
	{
		this.year = year;
		this.month = month;
		this.timeZone = null;
	}

	public YearMonth(int year, int month, TimeZone timeZone)
	{
		this.year = new Year(year);
		this.month = new Month(month);
		this.timeZone = timeZone;
	}

	public YearMonth(int year, int month)
	{
		this(year, month, null);
	}

	public YearMonth(long time)
	{
		this(new Year(time), new Month(time));
	}

	public int getYear()
	{
		return year.getYear();
	}

	public int getMonth()
	{
		return month.getMonth();
	}

	public TimeZone getTimeZone()
	{
		return timeZone;
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(!(obj instanceof YearMonth))
			return false;

		final YearMonth that = (YearMonth)obj;
		return (year != null ? year.equals(that.year) : that.year == null) && (month != null ? month.equals(that.month) : that.month == null) && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
	}

	public int hashCode()
	{
		return (year != null ? year.hashCode() : -1) + (month != null ? month.hashCode() : -1) + (timeZone != null ? timeZone.hashCode() : -1);
	}

	public String toString()
	{
		final StringBuffer buffer = new StringBuffer();
		buffer.append(year);
		buffer.append("-");
		if(getMonth() < 10)
			buffer.append("0").append(getMonth());
		else
			buffer.append(getMonth());

		if(timeZone == null)
			return buffer.toString();

		if(DateTime.GMT.equals(timeZone))
			return buffer.append("Z").toString();

		return buffer.append(timeZone.getID().substring(3)).toString();
	}
}
