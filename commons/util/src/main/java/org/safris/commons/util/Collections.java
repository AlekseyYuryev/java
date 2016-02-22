/* Copyright (c) 2008 Seva Safris
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

package org.safris.commons.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class Collections {
  public static String toString(final Collection<?> collection, String delimiter) {
    if (collection == null)
      return null;

    if (delimiter == null)
      delimiter = "";

    if (collection.size() == 0)
      return "";

    final StringBuilder string = new StringBuilder();
    for (final Object obj : collection)
      string.append(delimiter).append(String.valueOf(obj));

    return string.substring(delimiter.length());
  }

  public static <K,V>boolean putUnmodifiableMap(final Map<? super K,? super V> map, final K key, final V value) {
    try {
      final Field mField = map.getClass().getDeclaredField("m");
      mField.setAccessible(true);
      final Map<? super K,? super V> m = (Map<? super K,? super V>)mField.get(map);
      m.put(key, value);
      return true;
    }
    catch (final Exception e) {
      return false;
    }
  }

  /**
   * Sorts the specified list into ascending order, according to the
   * <i>natural ordering</i> of its elements.  This implementation differs
   * from the one in java.util.Collections in that it allows null entries to
   * be sorted, which are placed in the beginning of the list.
   *
   * @param  list the list to be sorted.
   * @throws ClassCastException if the list contains elements that are not
   *         <i>mutually comparable</i> (for example, strings and integers).
   * @throws UnsupportedOperationException if the specified list's
   *         list-iterator does not support the <tt>set</tt> operation.
   * @see java.util.Collections#sort(List)
   */
  public static <T extends Comparable<? super T>>void sort(final List<T> list) {
    if (list.remove(null)) {
      java.util.Collections.<T>sort(list);
      list.add(0, null);
    }
    else {
      java.util.Collections.<T>sort(list);
    }
  }

  /**
   * Sorts the specified list according to the order induced by the
   * specified comparator.  This implementation differs from the one in
   * java.util.Collections in that it allows null entries to be sorted, which
   * are placed in the beginning of the list.
   *
   * @param  list the list to be sorted.
   * @param  c the comparator to determine the order of the list.  A
   *        <tt>null</tt> value indicates that the elements' <i>natural
   *        ordering</i> should be used.
   * @throws ClassCastException if the list contains elements that are not
   *         <i>mutually comparable</i> using the specified comparator.
   * @throws UnsupportedOperationException if the specified list's
   *         list-iterator does not support the <tt>set</tt> operation.
   * @see java.util.Collections#sort(List, Comparator)
   */
  public static <T>void sort(final List<T> list, final Comparator<? super T> c) {
    if (list.remove(null)) {
      java.util.Collections.<T>sort(list, c);
      list.add(0, null);
    }
    else {
      java.util.Collections.<T>sort(list, c);
    }
  }

  /**
   * Returns a mutable list containing the specified object.
   *
   * @param clazz the final class type of the List.
   * @param o the object to be stored in the returned list.
   * @return a mutable list containing the specified object.
   */
  public static <T>List<T> singletonList(final Class<? extends List<?>> clazz, final T o) {
    try {
      final List<T> list = (List<T>)clazz.newInstance();
      list.add(o);
      return list;
    }
    catch (final Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  @SafeVarargs
  public static <T>Collection<T> asCollection(final Class<? extends Collection> cls, final T ... a) {
    try {
      final Collection<T> list = cls.newInstance();
      for (int i = 0; i < a.length; i++)
        list.add(a[i]);

      return list;
    }
    catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Collections() {
  }
}