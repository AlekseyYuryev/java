package org.safris.xws.xrs;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.safris.commons.util.DelegatedList;
import org.safris.commons.util.DelegatedMap;

public class DelegatedMultivaluedHashMap<K,V> extends DelegatedMap<K,List<V>> implements MultivaluedMap<K,V> {
  private static final long serialVersionUID = 2648516310407246308L;

  public interface MultivaluedMapDelegate<K,V> extends DelegatedMap.MapDelegate<K,List<V>> {
    public void putSingle(final K key, final V value);
    public void add(final K key, final V value);
    public void addFirst(final K key, final V value);

    public void add(final K key, final int index, final V element);
    public void remove(final K key, final int index);
  }

  @SuppressWarnings("rawtypes")
  protected final Class<? extends List> listType;
  protected final MultivaluedMapDelegate<K,V> delegate;

  @SuppressWarnings("rawtypes")
  public DelegatedMultivaluedHashMap(final Class<? extends Map> type, final Class<? extends List> listType, final MultivaluedMapDelegate<K,V> delegate) {
    super(type, delegate);
    this.listType = listType;
    this.delegate = delegate;
  }

  public DelegatedMultivaluedHashMap(final DelegatedMultivaluedHashMap<K,V> copy) {
    this(copy.map.getClass(), copy.listType, copy.delegate);
  }

  protected List<V> getValues(final K key) {
    List<V> values = get(key);
    if (values == null) {
      put(key, values = new DelegatedList<V>(listType, new DelegatedList.ListDelegate<V>() {
        @Override
        public void add(final int index, final V element) {
          delegate.add(key, index, element);
        }

        @Override
        public void remove(final int index) {
          delegate.remove(key, index);
        }
      }));
    }

    return values;
  }

  @Override
  public List<V> put(final K key, final List<V> value) {
    delegate.put(key, value);
    return super.put(key, value);
  }

  @Override
  public void putSingle(final K key, final V value) {
    delegate.putSingle(key, value);
    final List<V> values = getValues(key);
    values.clear();
    values.add(value);
  }

  @Override
  public void add(final K key, final V value) {
    delegate.add(key, value);
    getValues(key).add(value);
  }

  @Override
  public V getFirst(final K key) {
    final List<V> value = get(key);
    return value != null ? value.get(0) : null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void addAll(final K key, final V ... newValues) {
    if (newValues.length != 0)
      addAll(key, Arrays.asList(newValues));
  }

  @Override
  public void addAll(final K key, final List<V> valueList) {
    for (final V value : valueList)
      add(key, value);
  }

  @Override
  public void addFirst(final K key, final V value) {
    delegate.addFirst(key, value);
    getValues(key).add(0, value);
  }

  @Override
  public boolean equalsIgnoreValueOrder(final MultivaluedMap<K,V> otherMap) {
    if (otherMap == this)
      return true;

    if (!keySet().equals(otherMap.keySet()))
      return false;

    for (final Map.Entry<K,List<V>> entry : entrySet()) {
      final List<V> otherValue = otherMap.get(entry.getKey());
      if (otherValue.size() != entry.getValue().size() || !otherValue.containsAll(entry.getValue()))
        return false;
    }

    return true;
  }

  @Override
  public DelegatedMultivaluedHashMap<K,V> clone() {
    return new DelegatedMultivaluedHashMap<K,V>(this);
  }
}