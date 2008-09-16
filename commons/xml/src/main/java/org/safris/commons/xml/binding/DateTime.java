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

import java.util.Date;
import java.util.TimeZone;

public class DateTime extends Date
{
	protected static final TimeZone GMT = TimeZone.getTimeZone("GMT");

	public static DateTime parseDateTime(String string)
	{
		if(string == null)
			throw new NullPointerException("string == null");

		string = string.trim();
		if(string.length() < Year.YEAR_FRAG_MIN_LENGTH + 1 + Month.MONTH_FRAG_MIN_LENGTH + 1 + Day.DAY_FRAG_MIN_LENGTH + 1 + Time.HOUR_FRAG_MIN_LENGTH + 1 + Time.MINUTE_FRAG_MIN_LENGTH + 1 + Time.SECOND_FRAG_MIN_LENGTH)
			throw new IllegalArgumentException(string);

		final int year = Year.parseYearFrag(string);
		final int month = Month.parseMonthFrag(string = string.substring(string.indexOf("-") + 1));
		final int day = Day.parseDayFrag(string = string.substring(Month.MONTH_FRAG_MIN_LENGTH + 1));
		final int hour = Time.parseHourFrag(string = string.substring(Day.DAY_FRAG_MIN_LENGTH + 1));
		final int minute = Time.parseMinuteFrag(string = string.substring(Time.HOUR_FRAG_MIN_LENGTH + 1));
		final float second = Time.parseSecondFrag(string = string.substring(Time.MINUTE_FRAG_MIN_LENGTH + 1));
		int index = string.indexOf("Z", Time.SECOND_FRAG_MIN_LENGTH);
		if(index == -1)
			index = string.indexOf("-", Time.SECOND_FRAG_MIN_LENGTH);

		if(index == -1)
			index = string.indexOf("+", Time.SECOND_FRAG_MIN_LENGTH);

		final TimeZone timeZone;
		if(index != -1)
			timeZone = Time.parseTimeZoneFrag(string.substring(index));
		else
			timeZone = null;

		return new DateTime(year, month, day, hour, minute, second, timeZone);
	}

	private static int countMillis(char[] value)
	{
		int i;
		for(i = 0; i < value.length; i++)
			if('9' < value[i] || value[i] < '0')
				break;

		return i;
	}

	private final TimeZone timeZone;

	public DateTime()
	{
		super();
		this.timeZone = null;
	}

	public DateTime(TimeZone timeZone)
	{
		super();
		this.timeZone = timeZone;
	}

	protected DateTime(String s)
	{
		super(s);
		timeZone = null;
	}

	public DateTime(long date, TimeZone timeZone)
	{
		super(date);
		this.timeZone = timeZone;
	}

	public DateTime(int year, int month, int date, TimeZone timeZone)
	{
		super(year + 1900, month + 1, date, 0, 0, 0);
		this.timeZone = timeZone;
	}

	public DateTime(int year, int month, int date, int hrs, int min, TimeZone timeZone)
	{
		super(year + 1900, month + 1, date, hrs, min, 0);
		this.timeZone = timeZone;
	}

	public DateTime(int year, int month, int date, int hrs, int min, float sec, TimeZone timeZone)
	{
		super(year + 1900, month + 1, date, hrs, min, (int)Math.floor(sec));
		this.setTime(this.getTime() + (int)Math.round(10000 * (sec - (int)sec)) / 10);
		this.timeZone = timeZone;
	}

	public int getYear()
	{
		return super.getYear() - 1900;
	}

	public int getMonth()
	{
		return super.getMonth() - 1;
	}

	protected int getMilliSeconds()
	{
		return (int)(getTime() - (int)(getTime() / 1000) * 1000);
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(!(obj instanceof DateTime))
			return false;

		final DateTime that = (DateTime)obj;
		return this.getTime() == that.getTime() && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
	}

	public int hashCode()
	{
		return (int)getTime() + (timeZone != null ? timeZone.hashCode() : -1);
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

		buffer.append("T");
		final int hours = getHours();
		final int minutes = getMinutes();
		final int seconds = getSeconds();
		final int millis =  getMilliSeconds();
		final String hoursString = hours < 9 ? "0" + hours : String.valueOf(hours);
		final String minutesString = minutes < 9 ? "0" + minutes : String.valueOf(minutes);
		String secondsString = seconds < 9 ? "0" + seconds : String.valueOf(seconds);
		if(millis != 0)
		{
			secondsString += "." + millis;
			while(secondsString.endsWith("0"))
				secondsString = secondsString.substring(0, secondsString.length() - 1);
		}

		buffer.append(hoursString).append(":");
		buffer.append(minutesString).append(":");
		buffer.append(secondsString);

		if(timeZone == null)
			return buffer.toString();

		if(DateTime.GMT.equals(timeZone))
			return buffer.append("Z").toString();

		return buffer.append(timeZone.getID().substring(3)).toString();
	}
}
