/* Copyright (c) 2006 lib4j
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.lib4j.xml.binding;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

import org.lib4j.util.CalendarUtil;

/**
 * http://www.w3.org/TR/xmlschema11-2/#time
 */
public final class Time implements Serializable {
  private static final long serialVersionUID = -9015566323752593968L;

  public static Time parseTime(String string) {
    if (string == null)
      throw new NullPointerException("string == null");

    string = string.trim();
    if (string.length() < TIME_FRAG_MIN_LENGTH)
      throw new IllegalArgumentException("time == " + string);

    final int hour = parseHourFrag(string);
    final int minute = parseMinuteFrag(string = string.substring(HOUR_FRAG_MIN_LENGTH + 1));
    final float second = parseSecondFrag(string = string.substring(MINUTE_FRAG_MIN_LENGTH + 1));
    int index = string.indexOf("Z", SECOND_FRAG_MIN_LENGTH);
    if (index == -1)
      index = string.indexOf("-", SECOND_FRAG_MIN_LENGTH);

    if (index == -1)
      index = string.indexOf("+", SECOND_FRAG_MIN_LENGTH);

    final TimeZone timeZone = index != -1 ? parseTimeZoneFrag(string.substring(index)) : null;
    return new Time(hour, minute, second, timeZone);
  }

  protected static int parseHourFrag(final String string) {
    final int hour = Integer.parseInt(string.substring(0, HOUR_FRAG_MIN_LENGTH));
    if (hour < 0 || 24 < hour)
      throw new IllegalArgumentException("hour == " + string);

    return hour;
  }

  protected static int parseMinuteFrag(final String string) {
    int minute = Integer.parseInt(string.substring(0, MINUTE_FRAG_MIN_LENGTH));
    if (minute < 0 || 59 < minute)
      throw new IllegalArgumentException("minute == " + string);

    return minute;
  }

  protected static float parseSecondFrag(final String string) {
    if (string == null)
      throw new NullPointerException("string == null");

    if (string.length() < SECOND_FRAG_MIN_LENGTH)
      throw new IllegalArgumentException("second == " + string);

    char ch1 = string.charAt(0);
    if (ch1 < '0' || '5' < ch1)
      throw new IllegalArgumentException("second == " + string);

    char ch2 = string.charAt(1);
    if (ch2 < '0' || '9' < ch2)
      throw new IllegalArgumentException("second == " + string);

    final StringBuffer secondString = new StringBuffer();
    secondString.append(ch1);
    secondString.append(ch2);
    int index = 2;
    if (index < string.length() && string.charAt(index) == '.') {
      char ch = '.';
      secondString.append(ch);
      while (++index < string.length()) {
        ch = string.charAt(index);
        if (ch < '0' || '9' < ch)
          break;

        secondString.append(ch);
      }
    }

    final float second = Float.parseFloat(secondString.toString());
    if (second < 0 || 60 <= second)
      throw new IllegalArgumentException("second == " + string);

    return second;
  }

  protected static TimeZone parseTimeZoneFrag(final String string) {
    if (string == null)
      throw new NullPointerException("string == null");

    if (string.length() == 0)
      return null;

    int index = 0;
    final TimeZone timeZone;
    final char zPlusMinus = string.charAt(index);
    if (zPlusMinus == 'Z') {
      ++index;
      timeZone = TimeZone.getTimeZone("GMT");
    }
    else if ((zPlusMinus == '+' || zPlusMinus == '-') && 6 <= string.length()) {
      final String hourString = string.substring(++index, index += 2);
      final int hour = Integer.parseInt(hourString);
      if (14 < hour || hour < 0)
        throw new IllegalArgumentException(string);

      final String minuteString = string.substring(++index, index += 2);
      final int minute = Integer.parseInt(minuteString);
      if (59 < minute || minute < 0)
        throw new IllegalArgumentException(string);

      timeZone = TimeZone.getTimeZone("GMT" + zPlusMinus + hourString + ":" + minuteString);
    }
    else
      throw new IllegalArgumentException("timeZone == " + string);

    if (index != string.length())
      throw new IllegalArgumentException("timeZone == " + string);

    return timeZone;
  }

  protected static String formatTimeZone(final TimeZone timeZone) {
    if (timeZone == null)
      return "";

    if (DateTime.GMT.equals(timeZone))
      return "Z";

    int offset = (timeZone.getRawOffset() + timeZone.getDSTSavings()) / 60000;
    final boolean negative = offset < 0;
    offset = Math.abs(offset);
    final int hourTZ = offset / 60;
    final int minsTZ = offset - hourTZ * 60;
    return (negative ? "-" : "+") + (hourTZ < 10 ? "0" + hourTZ : String.valueOf(hourTZ)) + ":" + (minsTZ < 10 ? "0" + minsTZ : String.valueOf(minsTZ));
  }

  protected static final int HOUR_FRAG_MIN_LENGTH = 2;
  protected static final int MINUTE_FRAG_MIN_LENGTH = 2;
  protected static final int SECOND_FRAG_MIN_LENGTH = 2;
  protected static final int TIME_FRAG_MIN_LENGTH = HOUR_FRAG_MIN_LENGTH + 1 + MINUTE_FRAG_MIN_LENGTH + 1 + SECOND_FRAG_MIN_LENGTH;

  private final int hour;
  private final int minute;
  private final float second;
  private final TimeZone timeZone;

  public Time(final int hour, final int minute, float second, final TimeZone timeZone) {
    this.hour = hour;
    if (24 < hour || hour < 0)
      throw new IllegalArgumentException("hour == " + hour);

    this.minute = minute;
    if (59 < minute || minute < 0)
      throw new IllegalArgumentException("minute == " + minute);

    this.second = second;
    if (60 < second || second < 0)
      throw new IllegalArgumentException("second == " + second);

    this.timeZone = timeZone != null ? timeZone : TimeZone.getDefault();
  }

  public Time(final int hours, final int minutes, final float seconds) {
    this(hours, minutes, seconds, null);
  }

  public Time(final long time, final TimeZone timeZone) {
    this.timeZone = timeZone != null ? timeZone : TimeZone.getDefault();

    final Calendar calendar = CalendarUtil.newCalendar(time, this.timeZone);
    this.hour = calendar.get(Calendar.HOUR);
    this.minute = calendar.get(Calendar.MINUTE);
    this.second = calendar.get(Calendar.SECOND) + calendar.get(Calendar.MILLISECOND) / 1000f;
  }

  public Time(final long time) {
    this(time, null);
  }

  public Time() {
    this(System.currentTimeMillis());
  }

  public int getHour() {
    return hour;
  }

  public int getMinute() {
    return minute;
  }

  public float getSecond() {
    return second;
  }

  public TimeZone getTimeZone() {
    return timeZone;
  }

  public long getTime() {
    return (int)(second * 1000) + minute * 60000 + hour * 3600000;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof Time))
      return false;

    final Time that = (Time)obj;
    return this.hour == that.hour && this.minute == that.minute && this.second == that.second && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
  }

  @Override
  public int hashCode() {
    return hour ^ 3 + minute ^ 5 + (int)(second * 1000) ^ 7 + (timeZone != null ? timeZone.hashCode() : -1);
  }

  protected String toEmbededString() {
    final StringBuffer buffer = new StringBuffer();
    if (hour < 10)
      buffer.append("0").append(hour);
    else
      buffer.append(hour);

    buffer.append(":");
    if (minute < 10)
      buffer.append("0").append(minute);
    else
      buffer.append(minute);

    buffer.append(":");
    if (second < 10f) {
      if (second != 0f) {
        buffer.append("0").append(second);
        while (buffer.charAt(buffer.length() - 1) == '0')
          buffer.deleteCharAt(buffer.length() - 1);
      }
      else
        buffer.append("00");
    }
    else {
      buffer.append(second);
    }

    // Add trailing ".?00" to conform to XML millisecond standard
    final int lastDotIndex = buffer.lastIndexOf(".");
    if (lastDotIndex != -1) {
      if (buffer.length() - lastDotIndex == 2)
        buffer.append("00");
      else if (buffer.length() - lastDotIndex == 3)
        buffer.append("0");
    }

    return buffer.toString();
  }

  @Override
  public String toString() {
    return new StringBuffer(toEmbededString()).append(Time.formatTimeZone(timeZone)).toString();
  }
}