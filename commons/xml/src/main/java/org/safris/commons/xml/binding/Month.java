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
 * http://www.w3.org/TR/xmlschema11-2/#gMonth
 */
public class Month {
  public static Month parseMonth(String string) {
    if (string == null)
      throw new NullPointerException("string == null");

    string = string.trim();
    if (!string.startsWith(PAD_FRAG) || string.length() < PAD_FRAG.length() + MONTH_FRAG_MIN_LENGTH)
      throw new IllegalArgumentException(string);

    final int month = parseMonthFrag(string.substring(PAD_FRAG.length()));
    final TimeZone timeZone = Time.parseTimeZoneFrag(string.substring(PAD_FRAG.length() + MONTH_FRAG_MIN_LENGTH));
    return new Month(month, timeZone);
  }

  protected static int parseMonthFrag(String string) {
    if (string == null)
      throw new NullPointerException("string == null");

    if (string.length() < MONTH_FRAG_MIN_LENGTH)
      throw new IllegalArgumentException("month == " + string);

    int index = 0;
    final char ch = string.charAt(index);
    final char ch2 = string.charAt(++index);
    if (ch == '0') {
      if (ch2 < '1' || '9' < ch2)
        throw new IllegalArgumentException("month == " + string);
    }
    else if (ch == '1') {
      if (ch2 < '0' || '2' < ch2)
        throw new IllegalArgumentException("month == " + string);
    }
    else
      throw new IllegalArgumentException("month == " + string);


    final String monthString = "" + ch + ch2;
    int month;
    try {
      month = Integer.parseInt(monthString);
    }
    catch (NumberFormatException e) {
      throw new IllegalArgumentException("month == " + string, e);
    }

    return month;
  }

  protected static final int MONTH_FRAG_MIN_LENGTH = 2;
  private static final String PAD_FRAG = "--";

  private final int month;
  private final TimeZone timeZone;

  public Month(int month, TimeZone timeZone) {
    this.month = month;
    if (month < 0 || 12 < month)
      throw new IllegalArgumentException("month == " + month);

    this.timeZone = timeZone != null ? timeZone : TimeZone.getDefault();
  }

  public Month(int month) {
    this(month, null);
  }

  public Month(long time) {
    final java.util.Date date = new java.util.Date(time);
    this.month = date.getMonth() + 1;
    this.timeZone = TimeZone.getDefault();
  }

  public Month() {
    this(System.currentTimeMillis());
  }

  public int getMonth() {
    return month;
  }

  public TimeZone getTimeZone() {
    return timeZone;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof Month))
      return false;

    final Month that = (Month)obj;
    return this.month == that.month && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
  }

  public int hashCode() {
    return month ^ 13 + (timeZone != null ? timeZone.hashCode() : -1);
  }

  protected String toEmbededString() {
    final StringBuffer buffer = new StringBuffer();
    if (month < 10)
      buffer.append("--0").append(month);
    else
      buffer.append("--").append(month);

    return buffer.toString();
  }

  public String toString() {
    return new StringBuffer(toEmbededString()).append(Time.formatTimeZone(timeZone)).toString();
  }
}
