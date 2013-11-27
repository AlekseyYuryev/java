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

import java.util.ArrayList;
import java.util.Collection;

public class IdentityArrayList<E> extends ArrayList<E> {
  private final Object mutex = new Object();

  public IdentityArrayList(final int initialCapacity) {
    super(initialCapacity);
  }

  public IdentityArrayList(final Collection<? extends E> c) {
    super(c);
  }

  public IdentityArrayList() {
    super();
  }

  public boolean contains(final Object o) {
    return indexOf(o) > -1;
  }

  public int indexOf(final Object o) {
    for (int i = 0; i < size(); i++)
      if (o == get(i))
        return i;

    return -1;
  }

  public int lastIndexOf(final Object o) {
    for (int i = size() - 1; i >= 0; i--)
      if (o == get(i))
        return i;

    return -1;
  }

  public boolean remove(final Object o) {
    synchronized (mutex) {
      final int index = indexOf(o);
      if (index < 0)
        return false;

      return super.remove(index) != null;
    }
  }

  public boolean removeAll(final Collection<?> c) {
    if (c == null)
      return false;

    final int size = size();
    for (final Object o : c)
      remove(o);

    return size != size();
  }

  public boolean retainAll(final Collection<?> c) {
    if (c == null)
      return false;

    if (c.size() == 0 && size() != 0) {
      clear();
      return true;
    }

    boolean modified = false;
    OUT:
    for (int i = 0; i < c.size(); i++) {
      synchronized (mutex) {
        final Object o = get(i);
        for (final Object obj : c)
          if (obj == o)
            continue OUT;

        modified = remove(i) != null || modified;
      }
    }

    return modified;
  }

  public IdentityArrayList<E> clone() {
    return new IdentityArrayList(this);
  }
}
