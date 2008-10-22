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
 * http://www.w3.org/TR/xmlschema11-2/#time
 */
public class Time
{
	public static Time parseTime(String string)
	{
		if(string == null)
			throw new NullPointerException("string == null");

		string = string.trim();
		if(string.length() < TIME_FRAG_MIN_LENGTH)
			throw new IllegalArgumentException("time == " + string);

		final int hour = parseHourFrag(string);
		final int minute = parseMinuteFrag(string = string.substring(HOUR_FRAG_MIN_LENGTH + 1));
		final float second = parseSecondFrag(string = string.substring(MINUTE_FRAG_MIN_LENGTH + 1));
		int index = string.indexOf("Z", SECOND_FRAG_MIN_LENGTH);
		if(index == -1)
			index = string.indexOf("-", SECOND_FRAG_MIN_LENGTH);

		if(index == -1)
			index = string.indexOf("+", SECOND_FRAG_MIN_LENGTH);

		final TimeZone timeZone;
		if(index != -1)
			timeZone = parseTimeZoneFrag(string.substring(index));
		else
			timeZone = null;

		return new Time(hour, minute, second, timeZone);
	}

	protected static int parseHourFrag(String string)
	{
		final int hour = Integer.parseInt(string.substring(0, HOUR_FRAG_MIN_LENGTH));
		if(hour < 0 || 24 < hour)
			throw new IllegalArgumentException("hour == " + string);

		return hour;
	}

	protected static int parseMinuteFrag(String string)
	{
		int minute = Integer.parseInt(string.substring(0, MINUTE_FRAG_MIN_LENGTH));
		if(minute < 0 || 59 < minute)
			throw new IllegalArgumentException("minute == " + string);

		return minute;
	}

	protected static float parseSecondFrag(String string)
	{
		if(string == null)
			throw new NullPointerException("string == null");

		if(string.length() < SECOND_FRAG_MIN_LENGTH)
			throw new IllegalArgumentException("second == " + string);

		char ch1 = string.charAt(0);
		if(ch1 < '0' || '5' < ch1)
			throw new IllegalArgumentException("second == " + string);

		char ch2 = string.charAt(1);
		if(ch2 < '0' || '9' < ch2)
			throw new IllegalArgumentException("second == " + string);

		final StringBuffer secondString = new StringBuffer();
		secondString.append(ch1);
		secondString.append(ch2);
		int index = 2;
		if(index < string.length() && string.charAt(index) == '.')
		{
			char ch = '.';
			secondString.append(ch);
			while(++index < string.length())
			{
				ch = string.charAt(index);
				if(ch < '0' || '9' < ch)
					break;

				secondString.append(ch);
			}
		}

		final float second = Float.parseFloat(secondString.toString());
		if(second < 0 || 60 <= second)
			throw new IllegalArgumentException("second == " + string);

		return second;
	}

	protected static TimeZone parseTimeZoneFrag(String string)
	{
		if(string == null)
			throw new NullPointerException("string == null");

		if(string.length() == 0)
			return null;

		int index = 0;
		final TimeZone timeZone;
		final char zPlusMinus = string.charAt(index);
		if(zPlusMinus == 'Z')
		{
			++index;
			timeZone = TimeZone.getTimeZone("GMT");
		}
		else if((zPlusMinus == '+' || zPlusMinus == '-') && 6 <= string.length())
		{
			final String hourString = string.substring(++index, index += 2);
			final int hour = Integer.parseInt(hourString);
			if(14 < hour || hour < 0)
				throw new IllegalArgumentException(string);

			final String minuteString = string.substring(++index, index += 2);
			final int minute = Integer.parseInt(minuteString);
			if(59 < minute || minute < 0)
				throw new IllegalArgumentException(string);

			timeZone = TimeZone.getTimeZone("GMT" + zPlusMinus + hourString + ":" + minuteString);
		}
		else
			throw new IllegalArgumentException("timeZone == " + string);

		if(index != string.length())
			throw new IllegalArgumentException("timeZone == " + string);

		return timeZone;
	}

	protected static final int HOUR_FRAG_MIN_LENGTH = 2;
	protected static final int MINUTE_FRAG_MIN_LENGTH = 2;
	protected static final int SECOND_FRAG_MIN_LENGTH = 2;
	protected static final int TIME_FRAG_MIN_LENGTH = HOUR_FRAG_MIN_LENGTH + 1 + MINUTE_FRAG_MIN_LENGTH + 1 + SECOND_FRAG_MIN_LENGTH;

	private final int hour;
	private final int minute;
	private final float second;
	private final TimeZone timeZone;

	public Time(int hour, int minute, float second, TimeZone timeZone)
	{
		this.hour = hour;
		if(24 < hour || hour < 0)
			throw new IllegalArgumentException("hour == " + hour);

		this.minute = minute;
		if(59 < minute || minute < 0)
			throw new IllegalArgumentException("minute == " + minute);

		this.second = second;
		if(60 < second || second < 0)
			throw new IllegalArgumentException("second == " + second);

		this.timeZone = timeZone;
	}

	public Time(int hours, int minutes, float seconds)
	{
		this(hours, minutes, seconds, null);
	}

	public Time(long time)
	{
		final java.util.Date date = new java.util.Date(time);
		this.hour = date.getHours();
		this.minute = date.getMinutes();
		this.second = date.getSeconds() + (float)((time - (int)(time / 1000) * 1000) / 1000);
		this.timeZone = null;
	}

	public Time()
	{
		this(System.currentTimeMillis());
	}

	public int getHour()
	{
		return hour;
	}

	public int getMinute()
	{
		return minute;
	}

	public float getSecond()
	{
		return second;
	}

	public TimeZone getTimeZone()
	{
		return timeZone;
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(!(obj instanceof Time))
			return false;

		final Time that = (Time)obj;
		return this.hour == that.hour && this.minute == that.minute && this.second == that.second && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
	}

	public int hashCode()
	{
		return hour ^ 3 + minute ^ 5 + (int)(second * 1000) ^ 7 + (timeZone != null ? timeZone.hashCode() : -1);
	}

	public String toString()
	{
		final StringBuffer buffer = new StringBuffer();
		if(hour < 10)
			buffer.append("0").append(hour);
		else
			buffer.append(hour);

		buffer.append(":");
		if(minute < 10)
			buffer.append("0").append(minute);
		else
			buffer.append(minute);

		buffer.append(":");
		if(second < 10f)
		{
			if(second != 0f)
			{
				buffer.append("0").append(second);
				while(buffer.charAt(buffer.length() - 1) == '0')
					buffer.deleteCharAt(buffer.length() - 1);
			}
			else
				buffer.append("00");
		}
		else
			buffer.append(second);

		if(timeZone == null)
			return buffer.toString();

		if(DateTime.GMT.equals(timeZone))
			return buffer.append("Z").toString();

		return buffer.append(timeZone.getID().substring(3)).toString();
	}
}
