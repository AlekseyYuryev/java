/* Copyright (c) 2016 Seva Safris
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

package org.safris.commons.json;

public class Property<T> {
  private final JSObject jsObject;
  private final Binding<T> binding;
  private T value;

  public Property(final JSObject jsObject, final Binding<T> binding) {
    this.jsObject = jsObject;
    this.binding = binding;
  }

  public T value() {
    return value;
  }

  public void value(final T value) {
    this.value = value;
  }

  @SuppressWarnings("unchecked")
  public T encode() throws EncodeException {
    final String error = binding.validate(value);
    if (error != null)
      throw new EncodeException(error, jsObject);

    if (value instanceof Number) {
      final Number number = (Number)value;
      return (T)(number.intValue() == number.doubleValue() ? String.valueOf(number.intValue()) : String.valueOf(number.doubleValue()));
    }

    return value;
  }

  public void decode() throws DecodeException {
    final String error = binding.validate(value);
    if (error != null)
      throw new DecodeException(error, jsObject);
  }
}