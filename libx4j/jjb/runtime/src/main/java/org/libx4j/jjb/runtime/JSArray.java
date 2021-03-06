/* Copyright (c) 2016 lib4j
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

package org.libx4j.jjb.runtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

import org.lib4j.util.Collections;

public class JSArray<E> extends JSObject implements List<E>, RandomAccess, Cloneable, Serializable {
  private static final long serialVersionUID = -3764980134016799398L;

  protected static <T> String toString(final Collection<T> value, final int depth) {
    if (value == null)
      return "null";

    if (value.size() == 0)
      return "[]";

    final StringBuilder string = new StringBuilder("[");
    final Iterator<T> iterator = value.iterator();
    string.append(toString(iterator.next(), depth));
    while (iterator.hasNext())
      string.append(", ").append(toString(iterator.next(), depth));

    return string.append("]").toString();
  }

  private final ArrayList<E> list;

  public JSArray() {
    this.list = new ArrayList<E>();
  }

  public JSArray(final Collection<? extends E> c) {
    this.list = new ArrayList<E>(c);
  }

  public JSArray(final int size) {
    this.list = new ArrayList<E>(size);
  }

  @SuppressWarnings("unchecked")
  private JSArray(final JSArray<E> copy) {
    this.list = (ArrayList<E>)copy.list.clone();
  }

  @Override
  protected String _name() {
    return null;
  }

  @Override
  protected Binding<?> _getBinding(final String name) {
    return null;
  }

  @Override
  protected Collection<Binding<?>> _bindings() {
    return null;
  }

  @Override
  protected JSBundle _bundle() {
    return null;
  }

  @Override
  protected String _encode(final int depth) {
    return toString(this, depth);
  }

  @Override
  protected boolean _skipUnknown() {
    return false;
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean contains(final Object o) {
    return list.contains(o);
  }

  @Override
  public Iterator<E> iterator() {
    return list.iterator();
  }

  @Override
  public Object[] toArray() {
    return list.toArray();
  }

  @Override
  public <T> T[] toArray(final T[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final E e) {
    return list.add(e);
  }

  @Override
  public boolean remove(final Object o) {
    return list.remove(o);
  }

  @Override
  public boolean containsAll(final Collection<?> c) {
    return list.containsAll(c);
  }

  @Override
  public boolean addAll(final Collection<? extends E> c) {
    return list.addAll(c);
  }

  @Override
  public boolean addAll(final int index, final Collection<? extends E> c) {
    return list.addAll(index, c);
  }

  @Override
  public boolean removeAll(final Collection<?> c) {
    return list.removeAll(c);
  }

  @Override
  public boolean retainAll(final Collection<?> c) {
    return list.retainAll(c);
  }

  @Override
  public void clear() {
    list.clear();
  }

  @Override
  public E get(final int index) {
    return list.get(index);
  }

  @Override
  public E set(final int index, final E element) {
    return list.set(index, element);
  }

  @Override
  public void add(final int index, final E element) {
    list.add(index, element);
  }

  @Override
  public E remove(final int index) {
    return list.remove(index);
  }

  @Override
  public int indexOf(final Object o) {
    return list.indexOf(o);
  }

  @Override
  public int lastIndexOf(final Object o) {
    return list.lastIndexOf(o);
  }

  @Override
  public ListIterator<E> listIterator() {
    return list.listIterator();
  }

  @Override
  public ListIterator<E> listIterator(final int index) {
    return list.listIterator(index);
  }

  @Override
  public List<E> subList(final int fromIndex, final int toIndex) {
    return list.subList(fromIndex, toIndex);
  }

  @Override
  public JSArray<E> clone() {
    return new JSArray<E>(this);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof JSArray))
      return false;

    final JSArray<?> that = (JSArray<?>)obj;
    return Collections.equals(this, that);
  }

  @Override
  public String toString() {
    return _encode(1);
  }
}