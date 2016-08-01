package org.safris.commons.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public final class Enumerations {
  @SuppressWarnings("unchecked")
  private static <T>T[] recurse(final Class<T> componentType, final Enumeration<T> enumeration, final int depth) {
    if (enumeration.hasMoreElements()) {
      final T element = enumeration.nextElement();
      final T[] array = recurse(componentType, enumeration, depth + 1);
      array[depth] = element;
      return array;
    }

    return (T[])Array.newInstance(componentType, depth);
  }

  public static <T>T[] toArray(final Class<T> componentType, final Enumeration<T> enumeration) {
    return recurse(componentType, enumeration, 0);
  }

  public static <T>List<T> toList(final Class<T> componentType, final Enumeration<T> enumeration) {
    final T[] array = recurse(componentType, enumeration, 0);
    return Arrays.asList(array);
  }

  private Enumerations() {
  }
}