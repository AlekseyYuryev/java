/*  Copyright 2008 Safris Technologies Inc.
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

package org.safris.xml.generator.compiler.runtime;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import org.safris.commons.util.IdentityArrayList;

final class SpecificElementList<E extends Binding> extends IdentityArrayList<E>
{
	private final ElementAudit elementAudit;

	protected SpecificElementList(ElementAudit elementAudit, int initialCapacity)
	{
		super(initialCapacity);
		this.elementAudit = elementAudit;
    }

    protected SpecificElementList(ElementAudit elementAudit, Collection<? extends E> c)
	{
		super(c);
		this.elementAudit = elementAudit;
    }

    protected SpecificElementList(ElementAudit elementAudit)
	{
		super();
		this.elementAudit = elementAudit;
    }

	protected boolean add(E o, boolean addWithAudit)
	{
		return addWithAudit ? elementAudit.getParent()._$$addElementNoAudit(elementAudit, o) && super.add(o) : super.add(o);
	}

	public boolean add(E o)
	{
		final E after = get(size() - 1);
		elementAudit.getParent()._$$addElementAfter(after, elementAudit, o);
		return super.add(o);
	}

	public E set(int index, E element)
	{
		final E original = get(index);
		elementAudit.getParent()._$$replaceElement(original, elementAudit, element);
		return super.set(index, element);
	}

	public void add(int index, E element)
	{
		final E before = get(index);
		elementAudit.getParent()._$$addElementBefore(before, elementAudit, element);
		super.add(index, element);
	}

	public E remove(int index)
	{
		final E element = get(index);
		remove(element);
		return element;
	}

	protected boolean remove(Object o, boolean removeFromAudit)
	{
		if(!(o instanceof Binding))
			return false;

		if(!contains(o))
			return false;

		final boolean listModified = super.remove(o);
		if(!removeFromAudit)
			return listModified;

		final boolean auditModified = elementAudit.getParent()._$$removeElement((Binding)o);
		if(auditModified == listModified)
			return auditModified;

		throw new RuntimeBindingException("Both lists should have been modified, or none at all.");
	}

	public boolean remove(Object o)
	{
		return remove(o, true);
	}

	public Iterator iterator()
	{
		return new ElementIterator();
	}

	public ListIterator<E> listIterator()
	{
		return listIterator(0);
    }

    public ListIterator<E> listIterator(final int index)
	{
		if(index < 0 || index > size())
			throw new IndexOutOfBoundsException("Index: " + index);

		return new ElementListIterator(index);
    }

	private class ElementIterator implements Iterator<E>
	{
		private final Iterator<E> iterator = SpecificElementList.super.iterator();
		private int cursor = 0;
		private int lastRet = -1;

		public boolean hasNext()
		{
			return iterator.hasNext();
		}

		public E next()
		{
			final E next = iterator.next();
			lastRet = cursor++;
			return next;
		}

		public void remove()
		{
			if(lastRet == -1)
				throw new IllegalStateException();

			final E removeMe = SpecificElementList.this.get(lastRet);
			if(!SpecificElementList.this.remove(removeMe))
				throw new IllegalStateException("remove() method should have removed an element here");

			if(lastRet < cursor)
				cursor--;
			lastRet = -1;
		}
	}

	private class ElementListIterator implements ListIterator<E>
	{
		private final ListIterator<E> listIterator;

		protected ElementListIterator(int index)
		{
			listIterator = SpecificElementList.super.listIterator(index);
		}

		public boolean hasNext()
		{
			return listIterator.hasNext();
		}

		public E next()
		{
			return listIterator.next();
		}

		public boolean hasPrevious()
		{
			return listIterator.hasPrevious();
		}

		public E previous()
		{
			return listIterator.previous();
		}

		public int nextIndex()
		{
			return listIterator.nextIndex();
		}

		public int previousIndex()
		{
			return listIterator.previousIndex();
		}

		public void remove()
		{
			final int index = nextIndex() - 1;
			SpecificElementList.this.remove(index);
		}

		public void set(E o)
		{
			final int index = nextIndex() - 1;
			SpecificElementList.this.set(index, o);
		}

		public void add(E o)
		{
			final int index = nextIndex() - 1;
			SpecificElementList.this.add(index, o);
		}
	}
}
