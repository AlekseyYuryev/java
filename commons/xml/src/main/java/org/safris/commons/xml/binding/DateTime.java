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

public final class DateTime {
  public static DateTime parseDateTime(String string) {
    if (string == null)
      throw new NullPointerException("string == null");

    string = string.trim();
    if (string.length() < Year.YEAR_FRAG_MIN_LENGTH + 1 + Month.MONTH_FRAG_MIN_LENGTH + 1 + Day.DAY_FRAG_MIN_LENGTH + 1 + Time.HOUR_FRAG_MIN_LENGTH + 1 + Time.MINUTE_FRAG_MIN_LENGTH + 1 + Time.SECOND_FRAG_MIN_LENGTH)
      throw new IllegalArgumentException(string);

    final Date date = Date.parseDateFrag(string);
    final int index = string.indexOf("T", Date.DATE_FRAG_MIN_LENGTH);
    if (index == -1)
      throw new IllegalArgumentException("dateTime == " + string);

    final Time time = Time.parseTime(string.substring(index + 1));
    return new DateTime(date, time);
  }

  protected static final TimeZone GMT = TimeZone.getTimeZone("GMT");

  private final Date date;
  private final Time time;
  private final long epochTime;

  protected DateTime(final Date date, final Time time) {
    if (date == null)
      throw new NullPointerException("date == null");

    if (time == null)
      throw new NullPointerException("time == null");

    this.date = date;
    this.time = time;
    epochTime = java.util.Date.UTC(date.getYear() - 1900, date.getMonth() - 1, date.getDay(), time.getHour(), time.getMinute(), (int)time.getSecond()) + ((int)(time.getSecond() * 1000) - (int)time.getSecond() * 1000) - getTimeZone().getRawOffset() - getTimeZone().getDSTSavings();
  }

  public DateTime(final int year, final int month, int day, final int hour, int minute, final float second, final TimeZone timeZone) {
    this(new Date(year, month, day, timeZone), new Time(hour, minute, second, timeZone));
  }

  public DateTime(final int year, final int month, int day, final int hour, int minute, final float second) {
    this(year, month, day, hour, minute, second, null);
  }

  public DateTime(final long time, final TimeZone timeZone) {
    this(new Date(time, timeZone), new Time(time, timeZone));
  }

  public DateTime(final long time) {
    this(new Date(time), new Time(time));
  }

  public DateTime() {
    this(System.currentTimeMillis());
  }

  public int getYear() {
    return date.getYear();
  }

  public int getMonth() {
    return date.getMonth();
  }

  public int getDay() {
    return date.getDay();
  }

  public int getHour() {
    return time.getHour();
  }

  public int getMinute() {
    return time.getMinute();
  }

  public float getSecond() {
    return time.getSecond();
  }

  public TimeZone getTimeZone() {
    return time.getTimeZone();
  }

  public long getTime() {
    return epochTime;
  }

  public boolean equals(final Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof DateTime))
      return false;

    final DateTime that = (DateTime)obj;
    return (date != null ? date.equals(that.date) : that.date == null) && (time != null ? time.equals(that.time) : that.time == null);
  }

  public int hashCode() {
    return (date != null ? date.hashCode() : -1) + (time != null ? time.hashCode() : -1);
  }

  protected String toEmbededString() {
    final StringBuffer buffer = new StringBuffer();
    if (date != null)
      buffer.append(date.toEmbededString());

    buffer.append("T");
    if (time != null)
      buffer.append(time.toEmbededString());

    return buffer.toString();
  }

  public String toString() {
    return new StringBuffer(toEmbededString()).append(Time.formatTimeZone(getTimeZone())).toString();
  }
}