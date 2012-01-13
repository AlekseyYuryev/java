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

package org.safris.xml.generator.compiler.runtime;

import java.util.Iterator;
import org.safris.commons.util.IdentityArrayList;

final class GeneralElementList<E extends Binding> extends IdentityArrayList<E> {
  private final CompositeElementStore directory;

  protected GeneralElementList(CompositeElementStore directory, int initialCapacity) {
    super(initialCapacity);
    this.directory = directory;
  }

  protected GeneralElementList(CompositeElementStore directory) {
    this.directory = directory;
  }

  public Iterator iterator() {
    return new ElementIterator();
  }

  private class ElementIterator implements Iterator<E> {
    private final Iterator<E> iterator = GeneralElementList.super.iterator();
    private int cursor = 0;
    private int lastRet = -1;

    public boolean hasNext() {
      return iterator.hasNext();
    }

    public E next() {
      final E next = iterator.next();
      lastRet = cursor++;
      return next;
    }

    public void remove() {
      if (lastRet == -1)
        throw new IllegalStateException();

      final E removeMe = GeneralElementList.this.get(lastRet);
      directory.remove(lastRet, removeMe);
      GeneralElementList.this.remove(lastRet);

      if (lastRet < cursor)
        cursor--;
      lastRet = -1;
    }
  }
}
