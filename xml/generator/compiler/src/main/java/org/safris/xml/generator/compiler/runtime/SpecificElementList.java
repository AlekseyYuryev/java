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

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import org.safris.commons.util.IdentityArrayList;

final class SpecificElementList<E extends Binding> extends IdentityArrayList<E> {
  private final ElementAudit elementAudit;

  protected SpecificElementList(ElementAudit elementAudit, int initialCapacity) {
    super(initialCapacity);
    this.elementAudit = elementAudit;
  }

  protected SpecificElementList(ElementAudit elementAudit, Collection<? extends E> c) {
    super(c);
    this.elementAudit = elementAudit;
  }

  protected SpecificElementList(ElementAudit elementAudit) {
    super();
    this.elementAudit = elementAudit;
  }

  protected boolean add(E o, boolean addWithAudit) {
    return addWithAudit ? elementAudit.getParent()._$$addElementNoAudit(elementAudit, o) && super.add(o) : super.add(o);
  }

  public boolean add(E o) {
    final E after = get(size() - 1);
    elementAudit.getParent()._$$addElementAfter(after, elementAudit, o);
    return super.add(o);
  }

  public E set(int index, E element) {
    final E original = get(index);
    elementAudit.getParent()._$$replaceElement(original, elementAudit, element);
    return super.set(index, element);
  }

  public void add(int index, E element) {
    final E before = get(index);
    elementAudit.getParent()._$$addElementBefore(before, elementAudit, element);
    super.add(index, element);
  }

  public E remove(int index) {
    final E element = get(index);
    remove(element);
    return element;
  }

  protected boolean remove(Object o, boolean removeFromAudit) {
    if (!(o instanceof Binding) || !contains(o))
      return false;

    final boolean listModified = super.remove(o);
    if (!removeFromAudit)
      return listModified;

    final boolean auditModified = elementAudit.getParent()._$$removeElement((Binding)o);
    if (auditModified == listModified)
      return auditModified;

    throw new RuntimeBindingException("Both lists should have been modified, or none at all.");
  }

  public boolean remove(Object o) {
    final boolean retVal = remove(o, true);
    if (size() == 0)
      elementAudit.reset();

    return retVal;
  }

  public boolean removeAll(Collection<?> c) {
    final boolean retVal = super.removeAll(c);
    if (size() == 0)
      elementAudit.reset();

    return retVal;
  }

  public boolean retainAll(Collection<?> c) {
    final boolean retVal = super.retainAll(c);
    if (size() == 0)
      elementAudit.reset();

    return retVal;
  }

  public void clear() {
    super.clear();
    elementAudit.reset();
  }

  public Iterator iterator() {
    return new ElementIterator();
  }

  public ListIterator<E> listIterator() {
    return listIterator(0);
  }

  public ListIterator<E> listIterator(final int index) {
    if (index < 0 || index > size())
      throw new IndexOutOfBoundsException("Index: " + index);

    return new ElementListIterator(index);
  }

  private class ElementIterator implements Iterator<E> {
    private final Iterator<E> iterator = SpecificElementList.super.iterator();
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

      final E removeMe = SpecificElementList.this.get(lastRet);
      if (!SpecificElementList.this.remove(removeMe))
        throw new IllegalStateException("remove() method should have removed an element here");

      if (lastRet < cursor)
        cursor--;
      lastRet = -1;
    }
  }

  private class ElementListIterator implements ListIterator<E> {
    private final ListIterator<E> listIterator;

    protected ElementListIterator(int index) {
      listIterator = SpecificElementList.super.listIterator(index);
    }

    public boolean hasNext() {
      return listIterator.hasNext();
    }

    public E next() {
      return listIterator.next();
    }

    public boolean hasPrevious() {
      return listIterator.hasPrevious();
    }

    public E previous() {
      return listIterator.previous();
    }

    public int nextIndex() {
      return listIterator.nextIndex();
    }

    public int previousIndex() {
      return listIterator.previousIndex();
    }

    public void remove() {
      final int index = nextIndex() - 1;
      SpecificElementList.this.remove(index);
    }

    public void set(E o) {
      final int index = nextIndex() - 1;
      SpecificElementList.this.set(index, o);
    }

    public void add(E o) {
      final int index = nextIndex() - 1;
      SpecificElementList.this.add(index, o);
    }
  }
}
