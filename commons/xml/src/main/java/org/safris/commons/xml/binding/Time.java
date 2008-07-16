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

/**
 * http://www.w3.org/TR/xmlschema11-2/#time
 */
public class Time
{
	public static Time parseTime(String string)
	{
		if(string == null)
			throw new NullPointerException("string == null");

		string = string.trim();
		if(string.length() < 6)
			throw new IllegalArgumentException(string);

		try
		{
			int index = 0;
			int hour = Integer.parseInt(string.substring(index, index += 2));
			if(24 < hour || hour < 0)
				throw new IllegalArgumentException(string);

			int minute = Integer.parseInt(string.substring(++index, index += 2));
			if(59 < minute || minute < 0)
				throw new IllegalArgumentException(string);

			final StringBuffer secondString = new StringBuffer(string.substring(++index, index += 2));
			final float second;
			if(string.length() == index)
			{
				second = Float.parseFloat(secondString.toString());
				if(59 < second || second < 0)
					throw new IllegalArgumentException(string);

				return new Time(hour, minute, second);
			}

			if(string.charAt(index) == '.')
			{
				char ch = '.';
				secondString.append(ch);
				while(++index < string.length())
				{
					ch = string.charAt(index);
					if('9' < ch || ch < '0')
						break;

					secondString.append(ch);
				}
			}

			second = Float.parseFloat(secondString.toString());
			if(60 <= second || second < 0)
				throw new IllegalArgumentException(string);

			if(string.length() == index)
				return new Time(hour, minute, second);

			TimeZone timeZone = null;
			final char zPlusMinus = string.charAt(index);
			if(zPlusMinus == 'Z')
			{
				++index;
				timeZone = TimeZone.getTimeZone("GMT");
			}
			else if(zPlusMinus == '+' || zPlusMinus == '-')
			{
				final String tzHr = string.substring(++index, index += 2);
				final int tzHour = Integer.parseInt(tzHr);
				if(14 < tzHour || tzHour < 0)
					throw new IllegalArgumentException(string);

				final String tzMn = string.substring(++index, index += 2);
				final int tzMinute = Integer.parseInt(tzMn);
				if(59 < tzMinute || tzMinute < 0)
					throw new IllegalArgumentException(string);

				timeZone = TimeZone.getTimeZone("GMT" + zPlusMinus + tzHr + ":" + tzMn);
			}

			if(string.length() == index)
				return new Time(hour, minute, second, timeZone);

			throw new IllegalArgumentException(string);
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	private final TimeZone timeZone;
	private final int hours;
	private final int minutes;
	private final float seconds;

	public Time()
	{
		this.timeZone = null;
		hours = 0;
		minutes = 0;
		seconds = 0;
    }

	public Time(TimeZone timeZone)
	{
		this.timeZone = timeZone;
		hours = 0;
		minutes = 0;
		seconds = 0;
	}

    public Time(String s)
	{
		this(new Date(s).getTime(), TimeZone.getTimeZone("GMT"));
	}

	public Time(long date, TimeZone timeZone)
	{
		final Date temp = new Date(date);
		hours = temp.getHours();
		minutes = temp.getMinutes();
		final String string = String.valueOf(date);
		seconds = Float.parseFloat(string.substring(string.length() - 5, string.length() - 3) + "." + string.substring(string.length() - 3, string.length()));
		this.timeZone = timeZone;
    }

	public Time(int hours, int minutes, int seconds)
	{
		this(hours, minutes, (float)seconds, null);
	}

	public Time(int hours, int minutes, int seconds, TimeZone timeZone)
	{
		this(hours, minutes, (float)seconds, timeZone);
	}

	public Time(int hours, int minutes, float seconds)
	{
		this(hours, minutes, seconds, null);
	}

	public Time(int hours, int minutes, float seconds, TimeZone timeZone)
	{
		this.hours = hours;
		if(24 < hours || hours < 0)
			throw new IllegalArgumentException("hours out of range: " + hours);

		this.minutes = minutes;
		if(59 < minutes || minutes < 0)
			throw new IllegalArgumentException("minutes out of range: " + minutes);

		this.seconds = seconds;
		if(60 < seconds || seconds < 0)
			throw new IllegalArgumentException("seconds out of range: " + seconds);

		this.timeZone = timeZone;
	}

	public TimeZone getTimeZone()
	{
		return timeZone;
	}

	public int getHours()
	{
		return hours;
	}

	public int getMinutes()
	{
		return minutes;
	}

	public float getSeconds()
	{
		return seconds;
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(!(obj instanceof Time))
			return false;

		final Time that = (Time)obj;
		return this.hours == that.hours && this.minutes == that.minutes && this.seconds == that.seconds && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
	}

	public int hashCode()
	{
		return (timeZone != null ? timeZone.hashCode() : -1) + hours ^ 3 + minutes ^ 5 + (int)(seconds * 1000) ^ 7;
	}

	public String toString()
	{
		final String hoursString = hours < 9 ? "0" + hours : String.valueOf(hours);
		final String minutesString = minutes < 9 ? "0" + minutes : String.valueOf(minutes);
		final String secondsString = seconds < 9 ? "0" + seconds : String.valueOf(seconds);
		final String string = hoursString + ":" + minutesString + ":" + (secondsString.endsWith(".0") ? secondsString.substring(0, secondsString.length() - 2) : secondsString);
		if(timeZone == null)
			return string;

		if(DateTime.GMT.equals(timeZone))
			return string + "Z";

		return string + timeZone.getID().substring(3);
	}
}
