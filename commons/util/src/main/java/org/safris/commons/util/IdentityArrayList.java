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
    for (Object o : c)
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
}
