/*  Copyright Safris Software 2006
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
