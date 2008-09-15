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

import java.util.Arrays;
import java.util.TimeZone;

/**
 * http://www.w3.org/TR/xmlschema11-2/#gMonthDay
 */
public class MonthDay
{
	public static MonthDay parseMonthDay(String string)
	{
		if(string == null)
			throw new NullPointerException();

		string = string.trim();
		if(!string.startsWith(PAD_FRAG) || string.length() < MONTH_DAY_FRAG_MIN_LENGTH)
			throw new IllegalArgumentException(string);

		final int month = Month.parseMonthFrag(string.substring(PAD_FRAG.length()));
		final int day = Day.parseDayFrag(string.substring(PAD_FRAG.length() + Month.MONTH_FRAG_MIN_LENGTH + 1));
		if(month == 2 && 29 < day)
			throw new IllegalArgumentException(string);

		if(Arrays.binarySearch(LONG_MONTHS, month) < 0 && 30 < day)
			throw new IllegalArgumentException(string);

		final TimeZone timeZone;
		if(MONTH_DAY_FRAG_MIN_LENGTH < string.length())
			timeZone = Time.parseTimeZoneFrag(string.substring(MONTH_DAY_FRAG_MIN_LENGTH));
		else
			timeZone = null;

		return new MonthDay(month, day, timeZone);
	}

	private static int[] LONG_MONTHS = new int[] {1, 3, 5, 7, 8, 10, 12};
	private static String PAD_FRAG = "--";
	private static final int MONTH_DAY_FRAG_MIN_LENGTH = PAD_FRAG.length() + Month.MONTH_FRAG_MIN_LENGTH + 1 + Day.DAY_FRAG_MIN_LENGTH;

	private final int month;
	private final int day;
	private final TimeZone timeZone;

	public MonthDay(int month, int day, TimeZone timeZone)
	{
		this.month = month;
		if(month < 0 || 12 < month)
			throw new IllegalArgumentException("month = " + month);

		this.day = day;
		if(day < 0 || 31 < day)
			throw new IllegalArgumentException("day = " + day);

		this.timeZone = timeZone;
	}

	public MonthDay(int month, int day)
	{
		this(month, day, null);
	}

	public int getMonth()
	{
		return month;
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
		if(this == obj)
			return true;

		if(!(obj instanceof MonthDay))
			return false;

		final MonthDay that = (MonthDay)obj;
		return this.month == that.month && this.day == that.day && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
	}

	public int hashCode()
	{
		return month ^ 13 + day ^ 17 + (timeZone != null ? timeZone.hashCode() : -1);
	}

	public String toString()
	{
		final StringBuffer buffer = new StringBuffer("--");
		if(month < 10)
			buffer.append("0").append(month);
		else
			buffer.append(month);

		buffer.append("-");
		if(day < 10)
			buffer.append("0").append(day);
		else
			buffer.append(day);

		if(timeZone == null)
			return buffer.toString();

		if(DateTime.GMT.equals(timeZone))
			return buffer.toString() + "Z";

		return buffer.toString() + timeZone.getID().substring(3);
	}
}
