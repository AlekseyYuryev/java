/*  Copyright 2010 Safris Technologies Inc.
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

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class ArrayMap<K extends Comparable<K>,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable {
    protected static class Entry<K extends Comparable<K>,V> implements Comparable<Entry<K,V>>, Map.Entry<K,V> {
        private final K key;
        private V value;

        protected Entry(K key, V value) {
            super();
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            final V oldValue = value;
            this.value = value;
            return oldValue;
        }

        public int compareTo(Entry<K,V> o) {
            return key.compareTo(o.key);
        }

        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (!(o instanceof Entry))
                return false;

            final Entry that = (Entry)o;
            return (key != null ? key.equals(that.key) : that.value == null) && (value != null ? value.equals(that.value) : that.value == null);
        }

        public int hashCode() {
            return (key != null ? key.hashCode() : 0) ^ (value != null ? value.hashCode() : 0);
        }
    }

    protected ArrayList<Entry<K,V>> entries;

    public ArrayMap() {
        entries = new ArrayList<Entry<K,V>>();
    }

    public ArrayMap(Map<K,V> m) {
        this();
        putAll(m);
    }

    public int indexOf(Object key) {
        if (key == null) {
            for (int i = 0; i < size(); i++)
                if (key == entries.get(i).getKey())
                    return i;
        }
        else {
            for (int i = 0; i < size(); i++)
                if (key.equals(entries.get(i).getKey()))
                    return i;
        }

        return -1;
    }

    public ArrayMap.Entry<K,V> getEntry(int i) {
        return entries.get(i);
    }

    public void clear() {
        entries.clear();
    }

    public boolean containsKey(Object key) {
        return indexOf(key) != -1;
    }

    public boolean containsValue(Object value) {
        for (int i = 0; i < size(); i++)
            if (value.equals(((Entry)entries.get(i)).value))
                return true;

        return false;
    }

    public Set<Map.Entry<K,V>> entrySet() {
        final TreeSet<Map.Entry<K,V>> set = new TreeSet<Map.Entry<K,V>>();
        for (int i = 0; i < size(); i++)
            set.add(entries.get(i));

        return set;
    }

    public boolean equals(Object o) {
        return o == this;
    }

    public V get(Object key) {
        final int index = indexOf(key);
        if (index == -1)
            return null;

        return ((Map.Entry<K,V>)entries.get(index)).getValue();
    }

    public int hashCode() {
        int hashCode = 0;
        for (int i = 0; i < size(); i++)
            hashCode ^= ((Entry)entries.get(i)).hashCode();

        return hashCode;
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public Set<K> keySet() {
        final TreeSet<K> keys = new TreeSet<K>();
        for (int i = 0; i < size(); i++)
            keys.add(entries.get(i).getKey());

        return keys;
    }

    public V put(K key, V value) {
        final int index = indexOf(key);
        if (index != -1)
            return entries.get(index).setValue(value);

        entries.add(new Entry<K,V>(key, value));
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> t) {
        if (t == null)
            throw new NullPointerException("t == null");

        for (Map.Entry<? extends K, ? extends V> entry : t.entrySet())
            put(entry.getKey(), entry.getValue());
    }

    public V remove(Object key) {
        final int i = indexOf(key);
        if (i == -1)
            return null;

        return entries.remove(i).getValue();
    }

    public int size() {
        return entries.size();
    }

    public Collection<V> values() {
        final ArrayList<V> values = new ArrayList<V>();
        for (int i = 0; i < size(); i++)
            values.add(entries.get(i).value);

        return values;
    }
}
