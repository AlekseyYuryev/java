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
    public boolean filter(final Field value, final Object ... args) {
      final boolean filter = !value.isSynthetic() && !Modifier.isStatic(value.getModifiers()) && value.getAnnotation(NotHashable.class) == null;
      if (filter)
        value.setAccessible(true);

      return filter;
    }
  };

  private static final For.Filter<Field> whitelistFilter = new For.Filter<Field>() {
    public boolean filter(final Field value, final Object ... args) {
      final boolean filter = !value.isSynthetic() && !Modifier.isStatic(value.getModifiers()) && value.getAnnotation(Hashable.class) != null;
      if (filter)
        value.setAccessible(true);

      return filter;
    }
  };

  public static int hashCode(final Object obj, final Field field) throws IllegalAccessException {
    int normal;
    Object member;
    if (boolean.class == field.getType())
      normal = field.getBoolean(obj) ? 1231 : 1237;
    else if (byte.class == field.getType())
      normal = (int)field.getByte(obj);
    else if (char.class == field.getType())
      normal = (int)field.getChar(obj);
    else if (short.class == field.getType())
      normal = (int)field.getShort(obj);
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
        fields = For.<Field>rfor(allFields, whitelistFilter);
        if (fields == null)
          fields = For.<Field>rfor(allFields, nonBlacklistFilter);

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