package org.safris.xws.xrs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.safris.commons.util.MirroredArrayList;

public class MirroredMultivaluedHashMap<K,V,M> extends HashMap<K,List<V>> implements MultivaluedMap<K,V> {
  private static final long serialVersionUID = 2648516310407246308L;

  private final MirroredMultivaluedHashMap<K,M,V> mirroredMap;
  private final MirroredArrayList.Mirror<V,M> mirror;

  public MirroredMultivaluedHashMap(final MirroredArrayList.Mirror<V,M> mirror1, final MirroredArrayList.Mirror<M,V> mirror2) {
    this.mirroredMap = new MirroredMultivaluedHashMap<K,M,V>(this, mirror2);
    this.mirror = mirror1;
  }

  private MirroredMultivaluedHashMap(final MirroredMultivaluedHashMap<K,M,V> mirroredMap, final MirroredArrayList.Mirror<V,M> mirror) {
    this.mirroredMap = mirroredMap;
    this.mirror = mirror;
  }

  public MultivaluedMap<K,M> getMirroredMap() {
    return mirroredMap;
  }

  public MirroredArrayList.Mirror<V,M> getMirror() {
    return mirror;
  }

  protected final List<V> getValues(final K key) {
    List<V> values = get(key);
    if (values == null)
      put(key, values = new MirroredArrayList<V,M>(mirror, mirroredMap.mirror));

    return values;
  }

  @Override
  public void putSingle(final K key, final V value) {
    final List<V> values = getValues(key);
    values.clear();
    values.add(value);
  }

  @Override
  public void add(final K key, final V value) {
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
    getValues(key).addAll(valueList);
  }

  @Override
  public void addFirst(final K key, final V value) {
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

  private List<V> superPut(final K key, final List<V> value) {
    return super.put(key, value);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<V> put(final K key, final List<V> value) {
    final MirroredArrayList<V,M> list = value instanceof MirroredArrayList ? (MirroredArrayList<V,M>)value : new MirroredArrayList<V,M>(mirror, mirroredMap.mirror);
    mirroredMap.superPut(key, list.getMirror());
    return super.put(key, list);
  }

  @Override
  public void putAll(final Map<? extends K,? extends List<V>> m) {
    for (final Map.Entry<? extends K,? extends List<V>> e : m.entrySet())
      put(e.getKey(), e.getValue());
  }

  private List<V> superRemove(final Object key) {
    return super.remove(key);
  }

  @Override
  public List<V> remove(final Object key) {
    mirroredMap.superRemove(key);
    return super.remove(key);
  }

  private void superClear() {
    super.clear();
  }

  @Override
  public void clear() {
    mirroredMap.superClear();
    super.clear();
  }

  @Override
  public Object clone() {
    final MirroredMultivaluedHashMap<K,V,M> clone = new MirroredMultivaluedHashMap<K,V,M>(mirror, mirroredMap.mirror);
    clone.putAll(this);
    return clone;
  }
}