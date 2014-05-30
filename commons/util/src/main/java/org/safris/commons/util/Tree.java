/*  Copyright Safris Software 2006
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

import java.util.Collection;

public interface Tree<E> {
  // int size();
  // boolean isEmpty();
  // boolean contains(final Object o);
  TreeIterator<E> iterator();
  // Object[] toArray();
  // <T> T[] toArray(final T[] a);
  boolean addChild(final E p, final E e);
  boolean remove(final Object o);
  boolean containsAll(final Collection<?> c);
  boolean addAll(Collection<? extends E> c);
  boolean retainAll(final Collection<?> c);
  boolean removeAll(final Collection<?> c);
  void clear();
  boolean equals(final Object o);
  int hashCode();
}