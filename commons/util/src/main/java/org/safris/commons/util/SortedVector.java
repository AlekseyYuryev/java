/* Copyright (c) 2012 Seva Safris
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

public final class SortedVector<E extends Comparable<E>> extends Vector<E> {
  private static final long serialVersionUID = -4696161917681651124L;

  public boolean add(final E e) {
    addElement(e);
    return true;
  }

  public synchronized boolean addAll(final Collection<? extends E> c) {
    final int newSize = elementCount + c.size();
    if (elementData.length < newSize)
      setSize(newSize);

    for (final E e : c)
      addElement(e);

    return true;
  }

  public boolean addAll(final int index, final Collection<? extends E> c) {
    return addAll(c);
  }

  public synchronized void addElement(final E obj) {
    final int length = size();
    int start = 0;
    int end = length;
    int mid = 0;
    E object;
    int compare;
    while (end >= start) {
      mid = (start + end) / 2;
      if (mid >= length)
        break;

      object = (E)elementData[mid];
      compare = obj.compareTo(object);
      if (compare <= 0)
        end = mid - 1;
      else
        start = ++mid;
    }

    super.insertElementAt(obj, mid);
  }

  public boolean contains(final Object o) {
    return 0 <= indexOf(o, 0);
  }

  public synchronized boolean containsAll(final Collection<?> c) {
    for (final Object o : c)
      if (!contains(o))
        return false;

    return true;
  }

  public int indexOf(final Object o, final int index) {
    return Arrays.binarySearch(elementData, index, elementData.length - 1, o);
  }

  public void insertElementAt(final E obj, final int index) {
    addElement(obj);
  }

  public synchronized boolean retainAll(final Collection<?> c) {
    final Object[] sorted = c.toArray();
    Arrays.sort(sorted);

    int count = 0;
    final Object[] retained = new Object[elementCount];
    if (sorted.length < elementCount) {
      for (int i = 0, j, k = 0; i < sorted.length; i++) {
        if (0 <= (j = Arrays.binarySearch(elementData, k, elementCount, sorted[i]))) {
          k = j;
          retained[count++] = sorted[i];
          while (sorted[i].equals(elementData[--j]))
            retained[count++] = elementData[j];
          while (sorted[i].equals(elementData[++k]))
            retained[count++] = elementData[k];
        }
      }
    }
    else {
      for (int i = 0, j, k = 0; i < elementCount; i++) {
        if (0 <= (j = Arrays.binarySearch(sorted, k, sorted.length, elementData[i]))) {
          k = j;
          retained[count++] = elementData[i];
        }
      }
    }

    final boolean changed = elementCount != count;
    elementCount = count;
    elementData = retained;
    return changed;
  }

  public synchronized E set(final int index, final E element) {
    final E removed = remove(index);
    addElement(element);
    return removed;
  }

  public void setElementAt(final E obj, final int index) {
    set(index, obj);
  }
}