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
