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

package org.safris.commons.util;

import java.util.ArrayList;
import java.util.Collection;

public class IdentityArrayList<E> extends ArrayList<E>
{
	private final Object lock = new Object();

	public IdentityArrayList(int initialCapacity)
	{
		super(initialCapacity);
    }

    public IdentityArrayList(Collection<? extends E> c)
	{
		super(c);
    }

    public IdentityArrayList()
	{
		super();
    }

	public boolean contains(Object elem)
    {
        return indexOf(elem) > -1;
    }

    public int indexOf(Object elem)
    {
        for(int i = 0; i < size(); i++)
            if(elem == get(i))
                return i;

        return -1;
    }

    public int lastIndexOf(Object elem)
    {
        for(int i = size() - 1; i >= 0; i--)
            if(elem == get(i))
                return i;

        return -1;
    }

	public boolean remove(Object o)
	{
		synchronized(lock)
		{
			final int index = this.indexOf(o);
			if(index < 0)
				return false;

			return super.remove(index) != null;
		}
	}

	public boolean removeAll(Collection<?> c)
	{
		if(c == null)
			return false;

		boolean modified = false;
		for(Object o : c)
			modified = remove(o) || modified;

		return modified;
	}

	public boolean retainAll(Collection<?> c)
	{
		if(c == null)
			return false;

		if(c.size() == 0 && size() != 0)
		{
			clear();
			return true;
		}

		boolean modified = false;
OUT:
		for(int i = 0; i < c.size(); i++)
		{
			synchronized(lock)
			{
				final Object o = get(i);
				for(final Object obj : c)
					if(obj == o)
						continue OUT;

				modified = remove(i) != null || modified;
			}
		}

		return modified;
	}
}
