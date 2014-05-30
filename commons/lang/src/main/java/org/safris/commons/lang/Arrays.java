package org.safris.commons.lang;

import java.lang.reflect.Array;

public final class Arrays {
  @SafeVarargs
  public static <T>T[] addAll(final T[] ... array) {
    int length = 0;
    Class<?> type = null;
    for (final Object[] a : array) {
      if (a != null) {
        length += a.length;
        if (type == null)
          type = a.getClass().getComponentType();
      }
    }
    
    final Object[] all = (Object[])Array.newInstance(type, length);
    int index = 0;
    for (final Object[] a : array)
      System.arraycopy(a, 0, all, index += a.length, a.length);

    return (T[])all;
  }

  private Arrays() {
  }
}