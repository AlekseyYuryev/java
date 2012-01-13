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
 * http://www.w3.org/TR/xmlschema11-2/#gYearMonth
 */
public class YearMonth {
  public static YearMonth parseYearMonth(String string) {
    if (string == null)
      throw new NullPointerException("string == null");

    string = string.trim();
    if (string.length() < YEAR_MONTH_FRAG_MIN_LENGTH)
      throw new IllegalArgumentException("year-month == " + string);

    final int year = Year.parseYearFrag(string);
    int index = string.indexOf("-", Year.YEAR_FRAG_MIN_LENGTH);
    final int month = Month.parseMonthFrag(string.substring(index + 1));
    index = string.indexOf("Z", YEAR_MONTH_FRAG_MIN_LENGTH);
    if (index == -1)
      index = string.indexOf("-", YEAR_MONTH_FRAG_MIN_LENGTH);

    if (index == -1)
      index = string.indexOf("+", YEAR_MONTH_FRAG_MIN_LENGTH);

    final TimeZone timeZone;
    if (index != -1)
      timeZone = Time.parseTimeZoneFrag(string.substring(index));
    else
      timeZone = null;

    return new YearMonth(year, month, timeZone);
  }

  protected static final int YEAR_MONTH_FRAG_MIN_LENGTH = Year.YEAR_FRAG_MIN_LENGTH + 1 + Month.MONTH_FRAG_MIN_LENGTH;

  private final Year year;
  private final Month month;
  private final TimeZone timeZone;
  private final long epochTime;

  protected YearMonth(Year year, Month month, TimeZone timeZone) {
    if (year == null)
      throw new NullPointerException("year == null");

    if (month == null)
      throw new NullPointerException("month == null");

    this.year = year;
    this.month = month;
    this.timeZone = timeZone != null ? timeZone : TimeZone.getDefault();
    epochTime = java.util.Date.UTC(year.getYear() - 1900, month.getMonth() - 1, 1, 0, 0, 0) - getTimeZone().getRawOffset() - getTimeZone().getDSTSavings();
  }

  public YearMonth(int year, int month, TimeZone timeZone) {
    this(new Year(year, timeZone), new Month(month, timeZone), timeZone);
  }

  public YearMonth(int year, int month) {
    this(year, month, null);
  }

  public YearMonth(long time, TimeZone timeZone) {
    this(new Year(time, timeZone), new Month(time), timeZone);
  }

  public YearMonth(long time) {
    this(new Year(time), new Month(time), null);
  }

  public YearMonth() {
    this(System.currentTimeMillis());
  }

  public int getYear() {
    return year.getYear();
  }

  public int getMonth() {
    return month.getMonth();
  }

  public TimeZone getTimeZone() {
    return timeZone;
  }

  public long getTime() {
    return epochTime;
  }

  public boolean equals(Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof YearMonth))
      return false;

    final YearMonth that = (YearMonth)obj;
    return (year != null ? year.equals(that.year) : that.year == null) && (month != null ? month.equals(that.month) : that.month == null) && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
  }

  public int hashCode() {
    return (year != null ? year.hashCode() : -1) + (month != null ? month.hashCode() : -1) + (timeZone != null ? timeZone.hashCode() : -1);
  }

  protected String toEmbededString() {
    final StringBuffer buffer = new StringBuffer();
    buffer.append(year.toEmbededString());
    buffer.append("-");
    if (getMonth() < 10)
      buffer.append("0").append(getMonth());
    else
      buffer.append(getMonth());

    return buffer.toString();
  }

  public String toString() {
    return new StringBuffer(toEmbededString()).append(Time.formatTimeZone(timeZone)).toString();
  }
}
