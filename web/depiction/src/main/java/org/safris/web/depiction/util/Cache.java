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

package org.safris.web.depiction.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Cache<K,V>
{
	private final Map<K,Entry> map = new HashMap<K,Entry>();
	
	public Entry get(K key)
	{
		synchronized(key)
		{
			return map.get(key);
		}
	}
	
	public void put(K key, V value)
	{
		if(value == null)
			return;
		
		synchronized(key)
		{
			map.put(key, new Entry(key, value));
		}
	}
	
	public class Entry
	{
		private final Date created;
		private final K key;
		private final V value;
		
		public Entry(K key, V value)
		{
			this.created = new Date(System.currentTimeMillis());
			this.key = key;
			this.value = value;
		}
		
		public K getKey()
		{
			return key;
		}
		
		public V getValue()
		{
			return value;
		}
		
		public Date getCreated()
		{
			return created;
		}
	}
}
