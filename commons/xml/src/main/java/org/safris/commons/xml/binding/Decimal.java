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

/**
 * http://www.w3.org/TR/xmlschema11-2/#decimal
 */
public final class Decimal extends Number {
  private static final long serialVersionUID = -1301674537295759648L;

  public static Decimal parseDecimal(final String string) {
    return new Decimal(Double.parseDouble(string));
  }

  private final Number value;

  public Decimal(final Number value) {
    this.value = value;
  }

  /**
   * Returns the value of the specified number as an <code>int</code>.
   * This may involve rounding or truncation.
   * @return  the numeric value represented by this object after conversion
   *          to type <code>int</code>.
   */
  public int intValue() {
    return value.intValue();
  }

  /**
   * Returns the value of the specified number as a <code>long</code>.
   * This may involve rounding or truncation.
   * @return  the numeric value represented by this object after conversion
   *          to type <code>long</code>.
   */
  public long longValue() {
    return value.longValue();
  }

  /**
   * Returns the value of the specified number as a <code>float</code>.
   * This may involve rounding.
   * @return  the numeric value represented by this object after conversion
   *          to type <code>float</code>.
   */
  public float floatValue() {
    return value.floatValue();
  }

  /**
   * Returns the value of the specified number as a <code>double</code>.
   * This may involve rounding.
   * @return  the numeric value represented by this object after conversion
   *          to type <code>double</code>.
   */
  public double doubleValue() {
    return value.doubleValue();
  }

  public String toString() {
    if (longValue() == doubleValue())
      return String.valueOf(longValue());

    return String.valueOf(doubleValue());
  }
}