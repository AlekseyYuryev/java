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
		final int year = Year.parseYearFrag(string);
		int index = string.indexOf("-", Year.YEAR_FRAG_MIN_LENGTH);
		final int month = Month.parseMonthFrag(string.substring(index + 1));
		index = string.indexOf("Z", Year.YEAR_FRAG_MIN_LENGTH + 1 + Month.MONTH_FRAG_MIN_LENGTH);
		if(index == -1)
			index = string.indexOf("-", Year.YEAR_FRAG_MIN_LENGTH + 1 + Month.MONTH_FRAG_MIN_LENGTH);

		if(index == -1)
			index = string.indexOf("+", Year.YEAR_FRAG_MIN_LENGTH + 1 + Month.MONTH_FRAG_MIN_LENGTH);

		final TimeZone timeZone;
		if(index != -1)
			timeZone = Time.parseTimeZoneFrag(string.substring(index));
		else
			timeZone = null;

		return new YearMonth(year, month, timeZone);
	}

	private final int year;
	private final int month;
	private final TimeZone timeZone;

	public YearMonth(int year, int month, TimeZone timeZone)
	{
		this.year = year;
		this.month = month;
		this.timeZone = timeZone;
	}

	public YearMonth(int year, int month)
	{
		this(year, month, null);
	}

	public int getYear()
	{
		return year;
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
		if(obj == this)
			return true;

		if(!(obj instanceof YearMonth))
			return false;

		final YearMonth that = (YearMonth)obj;
		return this.year == that.year && this.month == that.month && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
	}

	public int hashCode()
	{
		return year ^ 5 + month ^ 13 + (timeZone != null ? timeZone.hashCode() : -1);
	}

	public String toString()
	{
		final StringBuffer buffer = new StringBuffer();
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

		if(timeZone == null)
			return buffer.toString();

		if(DateTime.GMT.equals(timeZone))
			return buffer.append("Z").toString();

		return buffer.append(timeZone.getID().substring(3)).toString();
	}
}
