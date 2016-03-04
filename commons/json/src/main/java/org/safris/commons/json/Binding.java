/* Copyright (c) 2015 Seva Safris
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.safris.commons.json.validator.Validator;

public class Binding<T> {
  public final String name;
  public final Method set;
  public final Field property;
  public final Class<?> type;
  public final boolean array;
  public final boolean notNull;
  public final Validator<T>[] validators;

  public Binding(final String name, final Method set, final Field property, final Class<?> type, final boolean array, final boolean notNull, final Validator<T> ... validators) {
    property.setAccessible(true);
    this.name = name;
    this.set = set;
    this.property = property;
    this.type = type;
    this.array = array;
    this.notNull = notNull;
    this.validators = validators;
  }

  protected String validate(final T value) {
    final String[] errors = Validator.validate(validators, value);
    if (errors.length == 0)
      return null;

    String message = "";
    for (final String error : errors)
      message += "\n\"" + name + "\" " + error;

    return message.substring(1);
  }
}