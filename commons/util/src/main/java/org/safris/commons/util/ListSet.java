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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public final class ListSet<E> implements List<E>, Set<E> {
  private final Class<? extends List<E>> listClass;
  private final Class<? extends Set<E>> setClass;
  private final List<E> list;
  private final Set<E> set;
  private final Map<E,Integer> map = new HashMap<E,Integer>();

  public ListSet(final Class<? extends List<E>> listClass, final Class<? extends Set<E>> setClass, final Collection<E> c) {
    this(listClass, setClass);
    addAll(c);
  }

  public ListSet(final Class<? extends List<E>> listClass, final Class<? extends Set<E>> setClass) {
    this.listClass = listClass;
    this.setClass = setClass;
    try {
      list = listClass.newInstance();
      set = setClass.newInstance();
    }
    catch (final Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  public int size() {
    return list.size();
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }

  public boolean contains(final Object o) {
    return set.contains(o);
  }

  public Iterator<E> iterator() {
    return new ListSetIterator(list.iterator());
  }

  public Object[] toArray() {
    return list.toArray();
  }

  public <T extends Object> T[] toArray(final T[] a) {
    return list.toArray(a);
  }

  public boolean add(final E e) {
    final boolean added = set.add(e);
    if (added) {
      map.put(e, list.size());
      list.add(e);
    }

    return added;
  }

  public void add(final int index, final E element) {
    addAt(index, element);
  }

  private boolean addAt(final int index, final E element) {
    final Integer exists = map.get(element);
    if (exists != null)
      list.remove(exists.intValue());
    else
      set.add(element);

    map.put(element, index);
    list.add(index, element);
    return exists != index;
  }

  public E set(final int index, final E element) {
    final E remove = list.set(index, element);
    map.remove(remove);
    set.remove(remove);
    map.put(element, index);
    set.add(element);
    return remove;
  }

  public boolean remove(final Object o) {
    final boolean removed = set.remove(o);
    if (removed)
      list.remove(map.remove(o).intValue());

    return removed;
  }

  public boolean containsAll(final Collection<?> c) {
    return set.containsAll(c);
  }

  public boolean addAll(final Collection<? extends E> c) {
    boolean changed = false;
    for (final E e : c)
      changed = add(e) || changed;

    return changed;
  }

  public boolean addAll(int index, final Collection<? extends E> c) {
    boolean changed = false;
    for (final E e : c)
      changed = addAt(index++, e) || changed;

    return changed;
  }

  public boolean removeAll(final Collection<?> c) {
    final int origSize = size();
    Integer index;
    for (final Object obj : c) {
      index = map.remove(obj);
      if (index != null) {
        list.remove(index.intValue());
        set.remove(index);
      }
    }

    return origSize != size();
  }

  public boolean retainAll(final Collection<?> c) {
    final int origSize = size();
    final Set<?> s = new HashSet<>(c);
    E e;
    int i = 0;
    while (i < size()) {
      e = list.get(i);
      if (!s.contains(e))
        remove(i);
      else
        i++;
    }

    return origSize != size();
  }

  public void clear() {
    map.clear();
    set.clear();
    list.clear();
  }

  public E get(final int index) {
    return list.get(index);
  }

  public E remove(final int index) {
    final E e = list.remove(index);
    set.remove(e);
    map.remove(e);
    return e;
  }

  public int indexOf(final Object o) {
    return map.get(o);
  }

  public int lastIndexOf(final Object o) {
    return map.get(o);
  }

  public ListIterator<E> listIterator() {
    return new ListSetListIterator(list.listIterator());
  }

  public ListIterator<E> listIterator(final int index) {
    return new ListSetListIterator(list.listIterator(index));
  }

  public List<E> subList(final int fromIndex, final int toIndex) {
    throw new UnsupportedOperationException();
  }

  private class ListSetIterator implements Iterator<E> {
    private final Iterator<E> iterator;
    protected E last;

    public ListSetIterator(final Iterator<E> iterator) {
      this.iterator = iterator;
    }

    public boolean hasNext() {
      return iterator.hasNext();
    }

    public E next() {
      return last = iterator.next();
    }

    public void remove() {
      iterator.remove();
      set.remove(last);
      last = null;
    }
  }

  private final class ListSetListIterator extends ListSetIterator implements ListIterator<E> {
    private final ListIterator<E> iterator;

    public ListSetListIterator(final ListIterator<E> iterator) {
      super(iterator);
      this.iterator = iterator;
    }

    public boolean hasPrevious() {
      return iterator.hasPrevious();
    }

    public E previous() {
      return last = iterator.previous();
    }

    public int nextIndex() {
      return iterator.nextIndex();
    }

    public int previousIndex() {
      return iterator.previousIndex();
    }

    public void set(final E e) {
      if (last == null)
        return;

      iterator.set(e);
      ListSet.this.set.remove(last);
      final int index = ListSet.this.map.remove(last);
      ListSet.this.map.put(e, index);
      last = null;
    }

    public void add(final E e) {
      throw new UnsupportedOperationException();
    }
  }
}