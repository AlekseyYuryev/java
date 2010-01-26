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

        this.timeZone = timeZone;
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

    public String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append(month);
        buffer.append("-");
        if (getDay() < 10)
            buffer.append("0").append(getDay());
        else
            buffer.append(getDay());

        if (timeZone == null)
            return buffer.toString();

        if (DateTime.GMT.equals(timeZone))
            return buffer.append("Z").toString();

        return buffer.append(timeZone.getID().substring(3)).toString();
    }
}
