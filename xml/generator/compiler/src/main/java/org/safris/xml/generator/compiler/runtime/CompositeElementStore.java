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

import java.util.ArrayList;
import java.util.List;

final class CompositeElementStore {
  private final List<Binding> elements;
  private final List<ElementAudit<Binding>> elementAudits;

  protected CompositeElementStore(final int initialCapacity) {
    this.elements = new GeneralElementList<Binding>(this, initialCapacity);
    this.elementAudits = new ArrayList<ElementAudit<Binding>>(initialCapacity);
  }

  protected CompositeElementStore(final CompositeElementStore copy) {
    this.elements = new GeneralElementList<Binding>(this);
    this.elementAudits = new ArrayList<ElementAudit<Binding>>(copy.elementAudits);
  }

  protected CompositeElementStore() {
    this.elements = new GeneralElementList<Binding>(this);
    this.elementAudits = new ArrayList<ElementAudit<Binding>>();
  }

  protected int size() {
    return elements.size();
  }

  protected List<Binding> getElements() {
    return elements;
  }

  protected Binding getElement(final int index) {
    return elements.get(index);
  }

  protected ElementAudit<Binding> getElementAudits(final int index) {
    return elementAudits.get(index);
  }

  protected boolean add(final Binding element, final ElementAudit<Binding> elementAudit, final boolean addToAudit) {
    synchronized (elements) {
      if (!elements.add(element))
        throw new BindingRuntimeException("Addition of element should have modified the elements list!");

      if (!elementAudits.add(elementAudit))
        throw new BindingRuntimeException("Addition of element should have modified the elementAudits list!");

      if (addToAudit && !elementAudit.addElement(element))
        throw new BindingRuntimeException("Addition of element should have modified the elementAudit list!");
    }

    return true;
  }

  protected void addBefore(final Binding before, final Binding element, final ElementAudit<Binding> elementAudit) {
    synchronized (elements) {
      final int index = elements.indexOf(before);
      elements.add(index, element);
      elementAudits.add(index, elementAudit);
    }
  }

  protected void addAfter(final Binding after, final Binding element, final ElementAudit<Binding> elementAudit) {
    synchronized (elements) {
      final int index = elements.indexOf(after);
      elements.add(index + 1, element);
      elementAudits.add(index, elementAudit);
    }
  }

  protected void replace(final Binding original, final Binding element, final ElementAudit<Binding> elementAudit) {
    synchronized (elements) {
      final int index = elements.indexOf(original);
      elements.set(index, element);
      elementAudits.set(index, elementAudit);
    }
  }

  protected boolean remove(final Binding element) {
    synchronized (elements) {
      final int index = elements.indexOf(element);
      if (index < 0)
        return false;

      if (elements.remove(index) != element)
        throw new BindingRuntimeException("Element identities do not match. Report this please.");

      // NOTE: The remove() method is initiated from the value list, which
      // NOTE: means that it is responsible for removing its own element
      // NOTE: and it is not the responsibility of this method to remove
      // NOTE: that element from the value list of the ElementAudit.
      elementAudits.remove(index);
    }

    return true;
  }

  protected boolean remove(final int index, final Binding element) {
    synchronized (elements) {
      final ElementAudit<?> elementAudit = elementAudits.remove(index);
      if (elementAudit != null)
        return ((SpecificElementList<?>)elementAudit.getElements()).remove(element, false);
    }

    return false;
  }

  protected void clear() {
    synchronized (elements) {
      elements.clear();
      elementAudits.clear();
    }
  }

  public CompositeElementStore clone() {
    return new CompositeElementStore(this);
  }

  public boolean equals(final Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof CompositeElementStore))
      return false;

    final CompositeElementStore that = (CompositeElementStore)obj;
    return elements.equals(that.elements) && elementAudits.equals(that.elementAudits);
  }

  public int hashCode() {
    return elements.hashCode();
  }
}