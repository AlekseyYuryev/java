package org.safris.commons.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class MirroredArrayList<T,M> extends ArrayList<T> {
  public static interface Mirror<T,M> {
    public M reflect(final T value);
  }

  private static final long serialVersionUID = 9187704221643956539L;

  private final MirroredArrayList<M,T> mirroredList;
  private final Mirror<T,M> mirror;

  public MirroredArrayList(final Mirror<T,M> mirror1, final Mirror<M,T> mirror2) {
    this.mirroredList = new MirroredArrayList<M,T>(this, mirror2);
    this.mirror = mirror1;
  }

  private MirroredArrayList(final MirroredArrayList<M,T> mirroredList, final Mirror<T,M> mirror) {
    this.mirroredList = mirroredList;
    this.mirror = mirror;
  }

  public MirroredArrayList<M,T> getMirror() {
    return mirroredList;
  }

  @Override
  public Object clone() {
    final MirroredArrayList<T,M> clone = new MirroredArrayList<T,M>(mirror, mirroredList.mirror);
    clone.addAll(this);
    return clone;
  }

  private T superSet(final int index, final T element) {
    return super.set(index, element);
  }

  @Override
  public T set(final int index, final T element) {
    mirroredList.superSet(index, mirror.reflect(element));
    return super.set(index, element);
  }

  private boolean superAdd(final T e) {
    return super.add(e);
  }

  @Override
  public boolean add(final T e) {
    mirroredList.superAdd(mirror.reflect(e));
    return super.add(e);
  }

  public void superAdd(final int index, final T element) {
    super.add(index, element);
  }

  @Override
  public void add(final int index, final T element) {
    mirroredList.superAdd(index, mirror.reflect(element));
    super.add(index, element);
  }

  private T superRemove(final int index) {
    return super.remove(index);
  }

  @Override
  public T remove(final int index) {
    mirroredList.superRemove(index);
    return super.remove(index);
  }

  @Override
  public boolean remove(final Object o) {
    final int index = indexOf(o);
    if (index == -1)
      return false;

    mirroredList.superRemove(index);
    super.remove(index);
    return true;
  }

  private void superClear() {
    super.clear();
  }

  @Override
  public void clear() {
    mirroredList.superClear();
    super.clear();
  }

  @Override
  public boolean addAll(final Collection<? extends T> c) {
    return addAll(c.size(), c);
  }

  @Override
  public boolean addAll(int index, final Collection<? extends T> c) {
    if (c.size() == 0)
      return false;

    for (final T e : c) {
      mirroredList.add(index, mirror.reflect(e));
      super.add(index++, e);
    }

    return true;
  }

  @Override
  public boolean removeAll(final Collection<?> c) {
    final int sizeBefore = size();
    for (final Object e : c) {
      final int index = indexOf(e);
      if (index != -1)
        remove(e);
    }

    return size() < sizeBefore;
  }

  @Override
  public boolean retainAll(final Collection<?> c) {
    final int sizeBefore = size();
    for (int i = 0; i < size(); i++)
      if (!c.contains(get(i)))
        remove(i);

    return size() < sizeBefore;
  }

  private void superTrimToSize() {
    super.trimToSize();
  }

  @Override
  public void trimToSize() {
    mirroredList.superTrimToSize();
    super.trimToSize();
  }

  @Override
  public boolean removeIf(final Predicate<? super T> filter) {
    Objects.requireNonNull(filter);
    final int sizeBefore = size();
    final Vector<Integer> remove = new Vector<Integer>(size());
    for (int i = 0; i < size(); i++)
      if (filter.test(get(i)))
        remove.add(i);

    for (final Integer index : remove)
      remove(index);

    return size() < sizeBefore;
  }

  @Override
  public void ensureCapacity(int minCapacity) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void forEach(Consumer<? super T> action) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Spliterator<T> spliterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void replaceAll(final UnaryOperator<T> operator) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void sort(final Comparator<? super T> c) {
    throw new UnsupportedOperationException();
  }
}