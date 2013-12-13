/*  Copyright Safris Software 2006
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.commons.xml.binding;

import java.util.Arrays;
import java.util.TimeZone;

/**
 * http://www.w3.org/TR/xmlschema11-2/#gMonthDay
 */
public class MonthDay {
  public static MonthDay parseMonthDay(String string) {
    if (string == null)
      throw new NullPointerException("string == null");

    string = string.trim();
    if (!string.startsWith(PAD_FRAG) || string.length() < PAD_FRAG.length() + MONTH_DAY_FRAG_MIN_LENGTH)
      throw new IllegalArgumentException("month-day == " + string);

    final MonthDay monthDay = parseMonthDayFrag(string = string.substring(PAD_FRAG.length()));

    final TimeZone timeZone;
    if (MONTH_DAY_FRAG_MIN_LENGTH < string.length())
      timeZone = Time.parseTimeZoneFrag(string.substring(MONTH_DAY_FRAG_MIN_LENGTH));
    else
      return monthDay;

    return new MonthDay(monthDay.getMonth(), monthDay.getDay(), timeZone);
  }

  protected static MonthDay parseMonthDayFrag(String string) {
    if (string == null)
      throw new NullPointerException("string == null");

    if (string.length() < MONTH_DAY_FRAG_MIN_LENGTH)
      throw new IllegalArgumentException("month-day == " + string);

    final int month = Month.parseMonthFrag(string);
    final int day = Day.parseDayFrag(string = string.substring(Month.MONTH_FRAG_MIN_LENGTH + 1));
    if (month == 2 && 29 < day)
      throw new IllegalArgumentException("month == " + month + " day == " + day);

    if (Arrays.binarySearch(LONG_MONTHS, month) < 0 && 30 < day)
      throw new IllegalArgumentException("month == " + month + " day == " + day);

    return new MonthDay(month, day);
  }

  protected static final int MONTH_DAY_FRAG_MIN_LENGTH = Month.MONTH_FRAG_MIN_LENGTH + 1 + Day.DAY_FRAG_MIN_LENGTH;
  private static final int[] LONG_MONTHS = new int[] {1, 3, 5, 7, 8, 10, 12};
  private static final String PAD_FRAG = "--";

  private final Month month;
  private final Day day;
  private final TimeZone timeZone;

  public MonthDay(int month, int day, TimeZone timeZone) {
    this(new Month(month), new Day(day), timeZone);
  }

  protected MonthDay(Month month, Day day, TimeZone timeZone) {
    if (month == null)
      throw new NullPointerException("month == null");

    if (day == null)
      throw new NullPointerException("day == null");

    this.month = month;
    this.day = day;
    if (month.getMonth() == 2 && 29 < day.getDay())
      throw new IllegalArgumentException("month == " + month + " day == " + day);

    if (Arrays.binarySearch(LONG_MONTHS, month.getMonth()) < 0 && 30 < day.getDay())
      throw new IllegalArgumentException("month == " + month + " day == " + day);

    this.timeZone = timeZone != null ? timeZone : TimeZone.getDefault();
  }

  public MonthDay(int month, int day) {
    this(month, day, null);
  }

  public MonthDay(long time) {
    this(new Month(time), new Day(time), null);
  }

  public MonthDay() {
    this(System.currentTimeMillis());
  }

  public int getMonth() {
    return month.getMonth();
  }

  public int getDay() {
    return day.getDay();
  }

  public TimeZone getTimeZone() {
    return timeZone;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof MonthDay))
      return false;

    final MonthDay that = (MonthDay)obj;
    return (month != null ? month.equals(that.month) : that.month == null) && (day != null ? day.equals(that.day) : that.day == null) && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
  }

  public int hashCode() {
    return (month != null ? month.hashCode() : -1) + (day != null ? day.hashCode() : -1) + (timeZone != null ? timeZone.hashCode() : -1);
  }

  protected String toEmbededString() {
    final StringBuffer buffer = new StringBuffer();
    buffer.append(month.toEmbededString());
    buffer.append("-");
    if (getDay() < 10)
      buffer.append("0").append(getDay());
    else
      buffer.append(getDay());

    return buffer.toString();
  }

  public String toString() {
    return new StringBuffer(toEmbededString()).append(Time.formatTimeZone(timeZone)).toString();
  }
}