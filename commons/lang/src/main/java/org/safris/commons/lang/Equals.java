/* Copyright (c) 2014 Seva Safris
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

package org.safris.commons.lang;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.lang.reflect.Classes;
import org.safris.commons.util.For;

public final class Equals {
  private static final Map<Class<?>,Field[]> blackWhiteListMap = new HashMap<Class<?>,Field[]>();

  private static final For.Filter<Field> nonBlacklistFilter = new For.Filter<Field>() {
    public boolean accept(final Field item, final Object ... args) {
      final boolean filter = !item.isSynthetic() && !Modifier.isStatic(item.getModifiers()) && item.getAnnotation(NotEqualable.class) == null;
      if (filter)
        item.setAccessible(true);

      return filter;
    }
  };

  private static final For.Filter<Field> whitelistFilter = new For.Filter<Field>() {
    public boolean accept(final Field item, final Object ... args) {
      final boolean filter = !item.isSynthetic() && !Modifier.isStatic(item.getModifiers()) && item.getAnnotation(Equalable.class) != null;
      if (filter)
        item.setAccessible(true);

      return filter;
    }
  };

  public static boolean equals(final Object a, final Object b, final Field field) throws IllegalAccessException {
    if (boolean.class == field.getType())
      return field.getBoolean(a) == field.getBoolean(b);

    if (byte.class == field.getType())
      return field.getByte(a) == field.getByte(b);

    if (char.class == field.getType())
      return field.getChar(a) == field.getChar(b);

    if (short.class == field.getType())
      return field.getShort(a) == field.getShort(b);

    if (int.class == field.getType())
      return field.getInt(a) == field.getInt(b);

    if (long.class == field.getType())
      return field.getLong(a) == field.getLong(b);

    if (float.class == field.getType())
      return field.getFloat(a) == field.getFloat(a);

    if (double.class == field.getType())
      return field.getDouble(a) == field.getDouble(a);

    final Object ao = field.get(a);
    final Object bo = field.get(b);
    return ao != null ? ao.equals(bo) : bo == null;
  }

  public static boolean equals(final Object a, final Object b, final String ... fieldName) {
    if (fieldName == null)
      throw new NullPointerException("fieldName == null");

    if (a == b || fieldName.length == 0)
      return true;

    if (a == null || b == null || !a.getClass().isInstance(b))
      return false;

    boolean equals = false;
    final Class<?> cls = a.getClass();
    Field field;
    try {
      for (final String name : fieldName) {
        field = Classes.getDeclaredField(cls, name);
        if (field == null)
          throw new IllegalArgumentException("Field name \"" + name + "\" not found in calss " + cls.getName());

        equals = Equals.equals(a, b, field);
        if (!equals)
          return false;
      }

      return true;
    }
    catch (final IllegalAccessException e) {
      throw new Error(e);
    }
  }

  public static boolean equals(final Object a, final Object b) {
    if (a == b)
      return true;

    if (a == null || b == null || a.getClass() != b.getClass())
      return false;

    try {
      boolean equals;
      final Class<?> cls = a.getClass();
      Field[] fields = blackWhiteListMap.get(cls);
      if (fields == null) {
        final Field[] allFields = Classes.getDeclaredFieldsDeep(cls);
        fields = For.<Field>recursiveOrdered(allFields, Field.class, whitelistFilter);
        if (fields.length == 0)
          fields = For.<Field>recursiveOrdered(allFields, Field.class, nonBlacklistFilter);

        blackWhiteListMap.put(cls, fields);
      }

      if (fields != null) {
        for (int i = 0; i < fields.length; i++) {
          equals = Equals.equals(a, b, fields[i]);
          if (!equals)
            return false;
        }
      }

      return true;
    }
    catch (final IllegalAccessException e) {
      throw new Error(e);
    }
  }

  private Equals() {
  }
}