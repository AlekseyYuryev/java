package org.safris.xml.generator.compiler.runtime;

import java.util.Iterator;
import org.safris.commons.util.IdentityArrayList;

final class GeneralElementList<E extends Binding> extends IdentityArrayList<E>
{
	private final CompositeElementStore directory;

	protected GeneralElementList(CompositeElementStore directory, int initialCapacity)
	{
		super(initialCapacity);
		this.directory = directory;
    }

	protected GeneralElementList(CompositeElementStore directory)
	{
		this.directory = directory;
	}

	public Iterator iterator()
	{
		return new ElementIterator();
	}

	private class ElementIterator implements Iterator<E>
	{
		private final Iterator<E> iterator = GeneralElementList.super.iterator();
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

			final E removeMe = GeneralElementList.this.get(lastRet);
			directory.remove(lastRet, removeMe);
			GeneralElementList.this.remove(lastRet);

			if(lastRet < cursor)
				cursor--;
			lastRet = -1;
		}
	}
}
