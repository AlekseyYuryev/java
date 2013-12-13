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

import java.util.TimeZone;

/**
 * http://www.w3.org/TR/xmlschema11-2/#gDay
 */
public class Day {
  public static Day parseDay(String string) {
    if (string == null)
      throw new NullPointerException("string == null");

    string = string.trim();
    if (!string.startsWith(PAD_FRAG) || string.length() < PAD_FRAG.length() + DAY_FRAG_MIN_LENGTH)
      throw new IllegalArgumentException("day == " + string);

    final int day = parseDayFrag(string.substring(PAD_FRAG.length()));
    final TimeZone timeZone = Time.parseTimeZoneFrag(string.substring(PAD_FRAG.length() + DAY_FRAG_MIN_LENGTH));
    return new Day(day, timeZone);
  }

  protected static int parseDayFrag(String string) {
    if (string == null)
      throw new NullPointerException("string == null");

    if (string.length() < DAY_FRAG_MIN_LENGTH)
      throw new IllegalArgumentException("day == " + string);

    int index = 0;
    final char ch = string.charAt(index);
    final char ch2 = string.charAt(++index);
    if (ch == '0') {
      if (ch2 < '1' || '9' < ch2)
        throw new IllegalArgumentException("day == " + string);
    }
    else if (ch == '1' || ch == '2') {
      if (ch2 < '0' || '9' < ch2)
        throw new IllegalArgumentException("day == " + string);
    }
    else if (ch == '3') {
      if (ch2 < '0' || '1' < ch2)
        throw new IllegalArgumentException("day == " + string);
    }
    else
      throw new IllegalArgumentException("day == " + string);

    final String dayString = "" + ch + ch2;
    int day;
    try {
      day = Integer.parseInt(dayString);
    }
    catch (NumberFormatException e) {
      throw new IllegalArgumentException(string, e);
    }

    return day;
  }

  protected static final int DAY_FRAG_MIN_LENGTH = 2;
  private static final String PAD_FRAG = "---";

  private final int day;
  private final TimeZone timeZone;

  public Day(int day, TimeZone timeZone) {
    this.day = day;
    if (day < 0 || 31 < day)
      throw new IllegalArgumentException("day == " + day);

    this.timeZone = timeZone != null ? timeZone : TimeZone.getDefault();
  }

  public Day(int day) {
    this(day, null);
  }

  public Day(long time) {
    final java.util.Date date = new java.util.Date(time);
    this.day = date.getDate();
    this.timeZone = TimeZone.getDefault();
  }

  public Day() {
    this(System.currentTimeMillis());
  }

  public int getDay() {
    return day;
  }

  public TimeZone getTimeZone() {
    return timeZone;
  }

  public boolean equals(Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof Day))
      return false;

    final Day that = (Day)obj;
    return this.day == that.day && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
  }

  public int hashCode() {
    return day ^ 17 + (timeZone != null ? timeZone.hashCode() : -1);
  }

  protected String toEmbededString() {
    final StringBuffer string = new StringBuffer();
    if (day < 10)
      string.append("---0").append(day);
    else
      string.append("---").append(day);

    return string.toString();
  }

  public String toString() {
    return new StringBuffer(toEmbededString()).append(Time.formatTimeZone(timeZone)).toString();
  }
}