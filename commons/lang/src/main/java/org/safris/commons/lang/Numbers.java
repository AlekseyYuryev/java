/*  Copyright Safris Software 2008
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

package org.safris.commons.lang;

public final class Numbers {
  public static int pow(int base, int exp) {
    int result = 1;
    while (exp != 0) {
      if ((exp & 1) != 0)
        result *= base;

      exp >>= 1;
      base *= base;
    }

    return result;
  }

  public static int rotateBits(final int value, final int sizeof, final int distance) {
    return (distance == 0 ? value : (distance < 0 ? value << -distance | value >> (sizeof + distance) : value >> distance | value << (sizeof - distance))) & (pow(2, sizeof) - 1);
  }

  public static boolean isNumber(String s) {
    if (s == null || (s = s.trim()).length() == 0)
      return false;

    int exponent = Integer.MIN_VALUE;
    int dot = Integer.MIN_VALUE;
    boolean negative = false;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c < '0') {
        if (c == '.') {
          if (dot != Integer.MIN_VALUE)
            return false;

          dot = i;
        }
        else if (c == '-') {
          if (i != exponent + 1 && i != 0)
            return false;
          
          negative = true;
        }
        else
          return false;
      }
      else if ('9' < c) {
        if (c != 'E')
          return false;
        
        if (1 < exponent || (negative && i == 1) || i - 1 == dot)
          return false;

        exponent = i;
      }
    }

    return true;
  }

  public static String roundInsignificant(final String value) {
    if (value == null)
      return null;

    if (value.length() == 0)
      return value;

    int i = value.length();
    while (i-- > 0)
      if (value.charAt(i) != '0' && value.charAt(i) != '.')
        break;

    return value.substring(0, i + 1);
  }

  private Numbers() {
  }
}