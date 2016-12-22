/* Copyright (c) 2006 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.commons.xml.binding;

/**
 * http://www.w3.org/TR/xmlschema11-2/#decimal
 */
public final class Decimal extends Number implements Comparable<Number> {
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
  @Override
  public int intValue() {
    return value.intValue();
  }

  /**
   * Returns the value of the specified number as a <code>long</code>.
   * This may involve rounding or truncation.
   * @return  the numeric value represented by this object after conversion
   *          to type <code>long</code>.
   */
  @Override
  public long longValue() {
    return value.longValue();
  }

  /**
   * Returns the value of the specified number as a <code>float</code>.
   * This may involve rounding.
   * @return  the numeric value represented by this object after conversion
   *          to type <code>float</code>.
   */
  @Override
  public float floatValue() {
    return value.floatValue();
  }

  /**
   * Returns the value of the specified number as a <code>double</code>.
   * This may involve rounding.
   * @return  the numeric value represented by this object after conversion
   *          to type <code>double</code>.
   */
  @Override
  public double doubleValue() {
    return value.doubleValue();
  }

  @Override
  public int compareTo(final Number o) {
    return value.doubleValue() < o.doubleValue() ? -1 : value.doubleValue() == o.doubleValue() ? 0 : 1;
  }

  @Override
  public String toString() {
    return longValue() == doubleValue() ? String.valueOf(longValue()) : String.valueOf(doubleValue());
  }
}