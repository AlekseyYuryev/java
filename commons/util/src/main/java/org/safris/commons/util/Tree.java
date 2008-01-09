package org.safris.commons.util;

import java.util.Collection;

public interface Tree<E>
{
//	int size();

//	boolean isEmpty();

//	boolean contains(Object o);

	TreeIterator<E> iterator();

//	Object[] toArray();

//	<T> T[] toArray(T[] a);

	boolean addChild(E p, E e);

	boolean remove(Object o);

	boolean containsAll(Collection<?> c);

	boolean addAll(Collection<? extends E> c);

	boolean retainAll(Collection<?> c);

	boolean removeAll(Collection<?> c);

	void clear();

	boolean equals(Object o);

	int hashCode();
}
