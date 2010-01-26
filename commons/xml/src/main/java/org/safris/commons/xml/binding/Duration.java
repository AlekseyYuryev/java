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
 * http://www.w3.org/TR/xmlschema11-2/#duration
 */
public class Duration {
    public static Duration parseDuration(String string) {
        if (string == null)
            throw new NullPointerException("string == null");

        string = string.trim();
        if (string.length() == 0)
            throw new IllegalArgumentException("Invalid duration: Empty string");

        int offset = 0;
        final boolean isNegative;
        final char signChar = string.charAt(0);
        if (signChar == '-') {
            isNegative = true;
            ++offset;
        }
        else if (signChar == '+') {
            isNegative = false;
            ++offset;
        }
        else {
            isNegative = false;
        }

        if (string.charAt(offset) != P)
            throw new IllegalArgumentException("Invalid duration: " + string + " (must start with P, +P, or -P)");

        long years = -1;
        long months = -1;
        long days = -1;
        long hours = -1;
        long minutes = -1;
        float seconds = -1;
        long secondsDecimalDigits = -1;
        boolean separatorSeen = false;

        final StringBuffer buffer = new StringBuffer();
        while (++offset < string.length()) {
            final char ch = string.charAt(offset);
            if (Character.isDigit(ch)) {
                buffer.append(ch);
            }
            else if (ch == T) {
                if (separatorSeen)
                    throw new IllegalArgumentException("Invalid duration: " + string + " (date/time separator 'T' used twice)");

                separatorSeen = true;
            }
            else {
                long digits;
                if (buffer.length() == 0) {
                    digits = 0;
                }
                else {
                    try {
                        digits = Long.parseLong(buffer.toString());
                    }
                    catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid duration: "  + string + " (max long value exceeded by " + buffer + ")");
                    }

                    buffer.setLength(0);
                }

                if (secondsDecimalDigits > -1) {
                    if (ch != S)
                        throw new IllegalArgumentException("Invalid duration: " + string + " (Duration point not allowed here: " + secondsDecimalDigits + "." + buffer + ch + ")");

                    if (!separatorSeen)
                        throw new IllegalArgumentException("Invalid duration: " + string + "(seconds specified before date/time separator 'T' seen)");

                    if (seconds != -1)
                        throw new IllegalArgumentException("Invalid duration: " + string + " (seconds specified twice)");

                    seconds = Float.parseFloat(secondsDecimalDigits + "." + digits);
                    secondsDecimalDigits = -1;
                }
                else {
                    if (ch == '.') {
                        secondsDecimalDigits = digits;
                    }
                    else if (separatorSeen) {
                        if (ch == S) {
                            if (seconds != -1)
                                throw new IllegalArgumentException("Invalid duration: " + string + " (seconds specified twice)");

                            seconds = digits;
                        }
                        else if (ch == M) {
                            if (minutes != -1)
                                throw new IllegalArgumentException("Invalid duration: " + string + " (minutes specified twice)");

                            if (seconds != -1)
                                throw new IllegalArgumentException("Invalid duration: " + string + " (minutes specified after seconds)");

                            minutes = digits;
                        }
                        else if (ch == H) {
                            if (hours != -1)
                                throw new IllegalArgumentException("Invalid duration: " + string + " (hours specified twice)");

                            if (minutes != -1)
                                throw new IllegalArgumentException("Invalid duration: " + string + " (hours specified after minutes)");

                            if (seconds != -1)
                                throw new IllegalArgumentException("Invalid duration: " + string + " (seconds specified after minutes)");

                            hours = digits;
                        }
                        else if (ch == Y || ch == D)
                            throw new IllegalArgumentException("Invalid duration: " + string + " (years or days of month specified after date/time separator 'T' seen)");
                    }
                    else {
                        if (ch == Y) {
                            if (years != -1)
                                throw new IllegalArgumentException("Invalid duration: " + string + " (years specified twice)");

                            if (months != -1)
                                throw new IllegalArgumentException("Invalid duration: " + string + " (years specified after months)");

                            if (days != -1)
                                throw new IllegalArgumentException("Invalid duration: " + string + " (years specified after days of month)");

                            years = digits;
                        }
                        else if (ch == M) {
                            if (months != -1)
                                throw new IllegalArgumentException("Invalid duration: " + string + " (months specified twice)");

                            if (days != -1)
                                throw new IllegalArgumentException("Invalid duration: " + string + " (days of month specified after months)");

                            months = digits;
                        }
                        else if (ch == D) {
                            if (days != -1)
                                throw new IllegalArgumentException("Invalid duration: " + string + " (days of month specified twice)");

                            days = digits;
                        }
                        else if (ch == H || ch == S)
                            throw new IllegalArgumentException("Invalid duration: " + string + " (hours or seconds specified before date/time separator 'T' seen)");
                    }
                }
            }
        }

        return new Duration(isNegative, years == -1 ? 0 : years, months == -1 ? 0 : months, days == -1 ? 0 : days, hours == -1 ? 0 : hours, minutes == -1 ? 0 : minutes, seconds == -1 ? 0 : seconds);
    }

    private static final char P = 'P';
    private static final char Y = 'Y';
    private static final char M = 'M';
    private static final char D = 'D';
    private static final char T = 'T';
    private static final char H = 'H';
    private static final char S = 'S';

    private boolean isNegative = false;
    private final long years;
    private final long months;
    private final long days;
    private final long hours;
    private final long minutes;
    private final float seconds;

    protected Duration() {
        this(false, 0, 0, 0, 0, 0, 0);
    }

    public Duration(boolean isNegative, int years) {
        this(isNegative, years, 0, 0, 0, 0, 0);
    }

    public Duration(boolean isNegative, long years, long months) {
        this(isNegative, years, months, 0, 0, 0, 0);
    }

    public Duration(boolean isNegative, long years, long months, long days) {
        this(isNegative, years, months, days, 0, 0, 0);
    }

    public Duration(boolean isNegative, long years, long months, long days, long hours) {
        this(isNegative, years, months, days, hours, 0, 0);
    }

    public Duration(boolean isNegative, long years, long months, long days, long hours, long minutes) {
        this(isNegative, years, months, days, hours, minutes, 0);
    }

    public Duration(boolean isNegative, long years, long months, long days, long hours, long minutes, float seconds) {
        this.isNegative = isNegative;
        this.years = years;
        this.months = months;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Duration))
            return false;

        final Duration that = (Duration)obj;
        return (isNegative == that.isNegative) && (years == that.years) && (months == that.months) && (days == that.days) && (hours == that.hours) && (minutes == that.minutes) && (seconds == that.seconds);
    }

    // FIXME: Come up with a better hash algorithm
    public int hashCode() {
        return (int)((isNegative ? -1 : 1) * (years + 1) * (months + 1) + (days + 1) * (hours + 1) * (minutes + 1) * (seconds + 1));
    }

    public String toString() {
        final StringBuffer buffer;
        if (isNegative)
            buffer = new StringBuffer("-");
        else
            buffer = new StringBuffer();

        buffer.append(String.valueOf(P));
        if (years != -1) {
            if (years != 0) {
                buffer.append(years);
                buffer.append(Y);
            }

            if (months != 0) {
                buffer.append(months);
                buffer.append(M);
            }

            if (days != 0) {
                buffer.append(days);
                buffer.append(D);
            }
        }

        if (hours != 0 || minutes != 0 || seconds != 0) {
            buffer.append(T);
            if (hours != 0) {
                buffer.append(hours);
                buffer.append(H);
            }

            if (minutes != 0) {
                buffer.append(minutes);
                buffer.append(M);
            }

            if (seconds != 0) {
                buffer.append(seconds);
                while (buffer.charAt(buffer.length() - 1) == '0')
                    buffer.deleteCharAt(buffer.length() - 1);

                if (buffer.charAt(buffer.length() - 1) == '.')
                    buffer.deleteCharAt(buffer.length() - 1);

                buffer.append(S);
            }
        }

        if (buffer.length() == 1)
            buffer.append(0).append(D);

        return buffer.toString();
    }
}
