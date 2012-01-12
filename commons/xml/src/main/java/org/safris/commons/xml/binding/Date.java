/*  Copyright 2010 Safris Technologies Inc.
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

/**
 * http://www.w3.org/TR/xmlschema11-2/#date
 */
import java.util.TimeZone;

public class Date {
    public static Date parseDate(String string) {
        if (string == null)
            throw new NullPointerException("string == null");

        string = string.trim();
        if (string.length() < DATE_FRAG_MIN_LENGTH)
            throw new IllegalArgumentException("date == " + string);

        final Date date = parseDateFrag(string);
        int index = string.indexOf("Z", DATE_FRAG_MIN_LENGTH);
        if (index == -1)
            index = string.indexOf("-", DATE_FRAG_MIN_LENGTH);

        if (index == -1)
            index = string.indexOf("+", DATE_FRAG_MIN_LENGTH);

        final TimeZone timeZone;
        if (index != -1)
            timeZone = Time.parseTimeZoneFrag(string.substring(index));
        else
            timeZone = null;

        return new Date(date.getYear(), date.getMonth(), date.getDay(), timeZone);
    }

    protected static Date parseDateFrag(String string) {
        if (string == null)
            throw new NullPointerException("string == null");

        if (string.length() < DATE_FRAG_MIN_LENGTH)
            throw new IllegalArgumentException("date == " + string);

        final int year = Year.parseYearFrag(string);
        final int index = string.indexOf("-", Year.YEAR_FRAG_MIN_LENGTH);
        final MonthDay monthDay = MonthDay.parseMonthDayFrag(string = string.substring(index + 1));
        if (year % 4 != 0 && monthDay.getMonth() == 2 && monthDay.getDay() == 29)
            throw new IllegalArgumentException("year == " + year + " month == " + monthDay.getMonth() + " day == " + monthDay.getDay());

        return new Date(year, monthDay.getMonth(), monthDay.getDay());
    }

    protected static final int DATE_FRAG_MIN_LENGTH = Year.YEAR_FRAG_MIN_LENGTH + 1 + Month.MONTH_FRAG_MIN_LENGTH + 1 + Day.DAY_FRAG_MIN_LENGTH;
    private final YearMonth yearMonth;
    private final Day day;
    private final TimeZone timeZone;
    private final long epochTime;

    protected Date(YearMonth yearMonth, Day day, TimeZone timeZone) {
        if (yearMonth == null)
            throw new NullPointerException("yearMonth == null");

        if (day == null)
            throw new NullPointerException("day == null");

        new MonthDay(yearMonth.getMonth(), day.getDay());
        this.yearMonth = yearMonth;
        this.day = day;
        this.timeZone = timeZone != null ? timeZone : TimeZone.getDefault();
        epochTime = java.util.Date.UTC(yearMonth.getYear() - 1900, yearMonth.getMonth() - 1, day.getDay(), 0, 0, 0) - getTimeZone().getRawOffset() - getTimeZone().getDSTSavings();
    }

    public Date(int year, int month, int day, TimeZone timeZone) {
        this(new YearMonth(year, month), new Day(day), timeZone);
    }

    public Date(int year, int month, int day) {
        this(year, month, day, null);
    }

    public Date(long time, TimeZone timeZone) {
      this(new YearMonth(time, timeZone), new Day(time), null);
    }

    public Date(long time) {
        this(new YearMonth(time), new Day(time), null);
    }

    public Date() {
        this(System.currentTimeMillis());
    }

    public int getYear() {
        return yearMonth.getYear();
    }

    public int getMonth() {
        return yearMonth.getMonth();
    }

    public int getDay() {
        return day.getDay();
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Date))
            return false;

        final Date that = (Date)obj;
        return (yearMonth != null ? yearMonth.equals(that.yearMonth) : that.yearMonth == null) && (day != null ? day.equals(that.day) : that.day == null) && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
    }

    public int hashCode() {
        return (yearMonth != null ? yearMonth.hashCode() : -1) + (day != null ? day.hashCode() : -1) + (timeZone != null ? timeZone.hashCode() : -1);
    }

    protected String toEmbededString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append(yearMonth.toEmbededString());
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
