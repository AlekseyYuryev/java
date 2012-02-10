/*  Copyright Safris Software 2009
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

package org.safris.commons.util;

public class Random {
  private static final char[] ALPHA = "abcdefghijklmnopqrstuvwxyz".toCharArray();
  private static final char[] NUMERIC = "0123456789".toCharArray();
  private static final char[] ALPHA_NUMERIC = (new String(NUMERIC) + new String(ALPHA)).toCharArray();

  private static String random(final int length, final char[] chars) {
    if (length <= 0)
      throw new IllegalArgumentException("length <= 0");

    final char[] random = new char[length];
    for (int i = 0; i < length; i++)
      random[i] = chars[(int)(Math.random() * chars.length)];

    return new String(random);
  }

  public static String alpha(final int length) {
    return random(length, ALPHA);
  }

  public static String numeric(final int length) {
    return random(length, NUMERIC);
  }

  public static String alphaNumeric(final int length) {
    return random(length, ALPHA_NUMERIC);
  }
}
