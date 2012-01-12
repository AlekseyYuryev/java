/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.commons.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

public class SortedVector<E extends Comparable> extends Vector<E> {
  public boolean add(final E e) {
    addElement(e);
    return true;
  }

  public synchronized boolean addAll(final Collection<? extends E> c) {
    final int newSize = elementCount + c.size();
    if (elementData.length < newSize)
      setSize(newSize);

    for (E e : c)
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
    Comparable object;
    int compare;
    while (end >= start) {
      mid = (start + end) / 2;
      if (mid >= length)
        break;

      object = (Comparable)elementData[mid];
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
    for (Object o : c)
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
