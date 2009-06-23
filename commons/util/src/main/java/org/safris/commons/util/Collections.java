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

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class Collections
{
	public static <K,V> boolean putUnmodifiableMap(Map<? super K,? super V> map, K key, V value)
	{
		try
		{
			final Field mField = map.getClass().getDeclaredField("m");
			mField.setAccessible(true);
			final Map<? super K,? super V> m = (Map)mField.get(map);
			m.put(key, value);
			return true;
		}
		catch(RuntimeException e)
		{
			return false;
		}
		catch(Exception e)
		{
			return false;
		}
	}

    /**
     * Sorts the specified list into ascending order, according to the
     * <i>natural ordering</i> of its elements.  This implementation differs
	 * from the one in java.util.Collections in that it allows null entries to
	 * be sorted, which are placed in the beginning of the list.
     *
     * @param  list the list to be sorted.
     * @throws ClassCastException if the list contains elements that are not
     *	       <i>mutually comparable</i> (for example, strings and integers).
     * @throws UnsupportedOperationException if the specified list's
     *	       list-iterator does not support the <tt>set</tt> operation.
     * @see java.util.Collections#sort(List)
     */
	public static <T extends Comparable<? super T>> void sort(List<T> list)
	{
		if(list.remove(null))
		{
			java.util.Collections.<T>sort(list);
            list.add(0, null);
		}
		else
			java.util.Collections.<T>sort(list);
	}

    /**
     * Sorts the specified list according to the order induced by the
     * specified comparator.  This implementation differs from the one in
	 * java.util.Collections in that it allows null entries to be sorted, which
	 * are placed in the beginning of the list.
     *
     * @param  list the list to be sorted.
     * @param  c the comparator to determine the order of the list.  A
     *        <tt>null</tt> value indicates that the elements' <i>natural
     *        ordering</i> should be used.
     * @throws ClassCastException if the list contains elements that are not
     *	       <i>mutually comparable</i> using the specified comparator.
     * @throws UnsupportedOperationException if the specified list's
     *	       list-iterator does not support the <tt>set</tt> operation.
     * @see java.util.Collections#sort(List, Comparator)
     */
	public static <T> void sort(List<T> list, Comparator<? super T> c)
	{
		if(list.remove(null))
		{
			java.util.Collections.<T>sort(list, c);
            list.add(0, null);
		}
		else
			java.util.Collections.<T>sort(list, c);

	}

	private Collections()
	{
	}
}
