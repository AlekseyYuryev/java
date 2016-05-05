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

import java.lang.reflect.Array;

public final class Arrays {
  public static abstract class Filter<T> {
    public abstract T filter(final T value);
  }

  @SafeVarargs
  public static <T>T[] filter(final Filter<T> filter, final T ... array) {
    for (int i = 0; i < array.length; i++)
      array[i] = filter.filter(array[i]);

    return array;
  }

  // FIXME: This has bugs!
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

    @SuppressWarnings("unchecked")
    final T[] all = (T[])Array.newInstance(type, length);
    int index = 0;
    for (final Object[] a : array)
      System.arraycopy(a, 0, all, index += a.length, a.length);

    return all;
  }

  public static void fillIncremental(final byte[] array, byte start) {
    for (int i = 0; i < array.length; i++)
      array[i] = start++;
  }

  public static void fillIncremental(final char[] array, char start) {
    for (int i = 0; i < array.length; i++)
      array[i] = start++;
  }

  public static void fillIncremental(final short[] array, short start) {
    for (int i = 0; i < array.length; i++)
      array[i] = start++;
  }

  public static void fillIncremental(final int[] array, int start) {
    for (int i = 0; i < array.length; i++)
      array[i] = start++;
  }

  public static void fillIncremental(final long[] array, long start) {
    for (int i = 0; i < array.length; i++)
      array[i] = start++;
  }

  public static void fillIncremental(final float[] array, float start) {
    for (int i = 0; i < array.length; i++)
      array[i] = start++;
  }

  public static void fillIncremental(final double[] array, double start) {
    for (int i = 0; i < array.length; i++)
      array[i] = start++;
  }

  private Arrays() {
  }
}