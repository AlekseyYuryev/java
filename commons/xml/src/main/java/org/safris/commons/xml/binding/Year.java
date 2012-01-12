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

import java.util.Calendar;
import java.util.TimeZone;
import org.safris.commons.util.CalendarUtil;

/**
 * http://www.w3.org/TR/xmlschema11-2/#gYear
 */
public class Year {
    public static Year parseYear(String string) {
        if (string == null)
            throw new NullPointerException("string == null");

        string = string.trim();
        if (string.length() < YEAR_FRAG_MIN_LENGTH)
            throw new IllegalArgumentException("year == " + string);

        final int year = parseYearFrag(string);
        int index = string.indexOf("Z", YEAR_FRAG_MIN_LENGTH);
        if (index == -1)
            index = string.indexOf("-", YEAR_FRAG_MIN_LENGTH);

        if (index == -1)
            index = string.indexOf("+", YEAR_FRAG_MIN_LENGTH);

        final TimeZone timeZone;
        if (index != -1)
            timeZone = Time.parseTimeZoneFrag(string.substring(index));
        else
            timeZone = null;

        return new Year(year, timeZone);
    }

    protected static int parseYearFrag(String string) {
        if (string == null)
            throw new NullPointerException("string == null");

        if (string.length() == 0)
            throw new IllegalArgumentException(string);

        int index = string.indexOf("Z", YEAR_FRAG_MIN_LENGTH);
        if (index != -1)
            string = string.substring(0, index);

        index = string.indexOf("-", YEAR_FRAG_MIN_LENGTH);
        if (index != -1)
            string = string.substring(0, index);

        index = string.indexOf("+", YEAR_FRAG_MIN_LENGTH);
        if (index != -1)
            string = string.substring(0, index);

        if (string.length() < YEAR_FRAG_MIN_LENGTH)
            throw new IllegalArgumentException(string);

        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException(string, e);
        }
    }

    protected static final int YEAR_FRAG_MIN_LENGTH = 4;

    private final int year;
    private final TimeZone timeZone;
    private final long epochTime;

    public Year(int year, TimeZone timeZone) {
        this.year = year;
        this.timeZone = timeZone != null ? timeZone : TimeZone.getDefault();
        epochTime = java.util.Date.UTC(year - 1900, 0, 1, 0, 0, 0) - getTimeZone().getRawOffset() - getTimeZone().getDSTSavings();
    }

    public Year(int year) {
        this(year, null);
    }

    public Year(long time, TimeZone timeZone) {
      this(CalendarUtil.newCalendar(time, timeZone).get(Calendar.YEAR), null);
    }

    public Year(long time) {
      this(CalendarUtil.newCalendar(time).get(Calendar.YEAR), null);
    }

    public Year() {
        this(System.currentTimeMillis());
    }

    public int getYear() {
        return year;
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

        if (!(obj instanceof Year))
            return false;

        final Year that = (Year)obj;
        return this.year == that.year && (timeZone != null ? timeZone.equals(that.timeZone) : that.timeZone == null);
    }

    public int hashCode() {
        return year ^ 5 + (timeZone != null ? timeZone.hashCode() : -1);
    }

    protected String toEmbededString() {
        final StringBuffer buffer = new StringBuffer();
        if (year < 10)
            buffer.append("000").append(year);
        else if (year < 100)
            buffer.append("00").append(year);
        else if (year < 1000)
            buffer.append("0").append(year);
        else
            buffer.append(year);

        return buffer.toString();
    }

    public String toString() {
        return new StringBuffer(toEmbededString()).append(Time.formatTimeZone(timeZone)).toString();
    }
}
