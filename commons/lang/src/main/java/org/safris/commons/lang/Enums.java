package org.safris.commons.lang;

import java.lang.reflect.Array;
import java.util.List;

public final class Enums {
  @SuppressWarnings("unchecked")
  private static <T extends Enum<T>>T[] recurseValueOf(final Class<T> enumClass, final int index, final int depth, final String ... names) {
    if (index == names.length)
      return (T[])Array.newInstance(enumClass, depth);

    T value;
    try {
      value = Enum.valueOf(enumClass, names[index]);
    }
    catch (final IllegalArgumentException e) {
      value = null;
    }

    final T[] enums = recurseValueOf(enumClass, index + 1, value != null ? depth + 1 : depth, names);
    if (value != null)
      enums[depth] = value;

    return enums;
  }

  public static <T extends Enum<T>>T[] valueOf(final Class<T> enumClass, final String ... names) {
    return recurseValueOf(enumClass, 0, 0, names);
  }

  public static <T extends Enum<T>>T[] valueOf(final Class<T> enumClass, final List<String> names) {
    return valueOf(enumClass, names.toArray(new String[names.size()]));
  }

  private Enums() {
  }
}