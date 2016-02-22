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

public final class HashCodes {
  private static final Map<Class<?>,Field[]> blackWhiteListMap = new HashMap<Class<?>,Field[]>();

  private static final For.Filter<Field> nonBlacklistFilter = new For.Filter<Field>() {
    @Override
    public boolean accept(final Field item, final Object ... args) {
      final boolean accept = !item.isSynthetic() && !Modifier.isStatic(item.getModifiers()) && item.getAnnotation(NotHashable.class) == null;
      if (accept)
        item.setAccessible(true);

      return accept;
    }
  };

  private static final For.Filter<Field> whitelistFilter = new For.Filter<Field>() {
    @Override
    public boolean accept(final Field item, final Object ... args) {
      final boolean accept = !item.isSynthetic() && !Modifier.isStatic(item.getModifiers()) && item.getAnnotation(Hashable.class) != null;
      if (accept)
        item.setAccessible(true);

      return accept;
    }
  };

  public static int hashCode(final Object obj, final Field field) throws IllegalAccessException {
    int normal;
    Object member;
    if (boolean.class == field.getType())
      normal = field.getBoolean(obj) ? 1231 : 1237;
    else if (byte.class == field.getType())
      normal = field.getByte(obj);
    else if (char.class == field.getType())
      normal = field.getChar(obj);
    else if (short.class == field.getType())
      normal = field.getShort(obj);
    else if (int.class == field.getType())
      normal = field.getInt(obj);
    else if (long.class == field.getType()) {
      final long value = field.getLong(obj);
      normal = (int)(value ^ (value >>> 32));
    }
    else if (float.class == field.getType())
      normal = Float.floatToIntBits(field.getFloat(obj));
    else if (double.class == field.getType()) {
      final long bits = Double.doubleToLongBits(field.getDouble(obj));
      normal = (int)(bits ^ (bits >>> 32));
    }
    else {
      member = field.get(obj);
      normal = member != null ? member.hashCode() : 0;
    }

    return normal;
  }

  public static int hashCode(final Object obj, final String ... fieldName) {
    if (fieldName == null)
      throw new NullPointerException("fieldName == null");

    if (obj == null || fieldName.length == 0)
      return 0;

    int hashCode = 0;
    final Class<?> cls = obj.getClass();
    Field field;
    try {
      for (final String name : fieldName) {
        field = Classes.getDeclaredField(cls, name);
        if (field == null)
          throw new IllegalArgumentException("Field name \"" + name + "\" not found in calss " + cls.getName());

        hashCode = 31 * hashCode + HashCodes.hashCode(obj, field);
      }
    }
    catch (final IllegalAccessException e) {
      throw new Error(e);
    }

    return hashCode;
  }

  public static int hashCode(final Object obj) {
    if (obj == null)
      return 0;

    try {
      int hashCode = 0;
      final Class<?> cls = obj.getClass();
      Field[] fields = blackWhiteListMap.get(cls);
      if (fields == null) {
        final Field[] allFields = Classes.getDeclaredFieldsDeep(cls);
        fields = For.<Field>recursiveOrdered(allFields, Field.class, whitelistFilter);
        if (fields.length == 0)
          fields = For.<Field>recursiveOrdered(allFields, Field.class, nonBlacklistFilter);

        blackWhiteListMap.put(cls, fields);
      }

      for (int i = 0; i < fields.length; i++)
        hashCode = 31 * hashCode + HashCodes.hashCode(obj, fields[i]);

      return hashCode;
    }
    catch (final IllegalAccessException e) {
      throw new Error(e);
    }
  }

  private HashCodes() {
  }
}