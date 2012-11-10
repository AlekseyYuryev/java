/*  Copyright Safris Software 2008
 *
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

    final StringBuffer buffer = new StringBuffer();
    for (final Object obj : collection)
      buffer.append(delimiter).append(String.valueOf(obj));

    return buffer.substring(delimiter.length());
  }

  public static <K,V>boolean putUnmodifiableMap(final Map<? super K,? super V> map, final K key, final V value) {
    try {
      final Field mField = map.getClass().getDeclaredField("m");
      mField.setAccessible(true);
      final Map<? super K,? super V> m = (Map)mField.get(map);
      m.put(key, value);
      return true;
    }
    catch (Exception e) {
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
   * @param clazz the class type of the List.
   * @param o the object to be stored in the returned list.
   * @return a mutable list containing the specified object.
   */
  public static <T>List<T> singletonList(final Class<? extends List> clazz, final T o) {
    try {
      final List<T> list = clazz.newInstance();
      list.add(o);
      return list;
    }
    catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  private Collections() {
  }
}
