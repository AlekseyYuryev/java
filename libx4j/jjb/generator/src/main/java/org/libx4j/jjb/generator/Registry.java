/* Copyright (c) 2017 lib4j
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.libx4j.jjb.generator;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.libx4j.jjb.jsonx.xe.jsonx_jsonx;

class Registry {
  class Value {
    private final String ref;

    public Value(final String ref) {
      if (refToModel.containsKey(ref))
        throw new IllegalArgumentException("ObjectModel class=\"" + ref + "\" already registered");

      refToModel.put(ref, null);
      this.ref = ref;
    }

    public <T extends Model>T value(final T model, final ComplexModel referrer) {
      refToModel.put(ref, model);
      return reference(model, referrer);
    }
  }

  private static String getKey(final Model model) {
    final String key = model.ref();
    if (key == null)
      throw new NullPointerException("key == null");

    return key;
  }

  private final Map<String,Model> refToModel;
  private final Map<String,List<ComplexModel>> references;

  public Registry() {
    refToModel = new LinkedHashMap<String,Model>();
    references = new LinkedHashMap<String,List<ComplexModel>>();
  }

  private Registry(final Map<String,Model> refToModel, final Map<String,List<ComplexModel>> references) {
    this.refToModel = refToModel;
    this.references = references;
  }

  public Value declare(final Class<?> clazz) {
    return new Value(clazz.getName());
  }

  public Value declare(final jsonx_jsonx._boolean binding) {
    return new Value(binding._name$().text());
  }

  public Value declare(final jsonx_jsonx._number binding) {
    return new Value(binding._name$().text());
  }

  public Value declare(final jsonx_jsonx._string binding) {
    return new Value(binding._name$().text());
  }

  public Value declare(final jsonx_jsonx._array binding) {
    return new Value(binding._name$().text());
  }

  public Value declare(final jsonx_jsonx._object binding) {
    return new Value(binding._class$().text());
  }

  public <T extends Model>T reference(final T model, final ComplexModel referrer) {
    if (referrer == null)
      return model;

    final String key = getKey(model);
    List<ComplexModel> referrers = references.get(key);
    if (referrers == null)
      references.put(key, referrers = new LinkedList<ComplexModel>());

    referrers.add(referrer);
    return model;
  }

  public Element getElement(final String ref) {
    return refToModel.get(ref);
  }

  public Collection<Model> elements() {
    return refToModel.values();
  }

  public int size() {
    return refToModel.size();
  }

  public int getNumReferrers(final Model model) {
    final List<ComplexModel> referrers = references.get(model.ref());
    return referrers == null ? 0 : referrers.size();
  }

  protected Registry normalize() {
    final LinkedHashMap<String,Model> clone = new LinkedHashMap<String,Model>(refToModel);
    final Iterator<? extends Map.Entry<String,Model>> iterator = clone.entrySet().iterator();
    while (iterator.hasNext()) {
      final Map.Entry<String,Model> entry = iterator.next();
      final Factor normalized = entry.getValue().normalize(this);
      entry.setValue(normalized instanceof PropertyElement ? ((PropertyElement)normalized).ref() : (Model)normalized);
    }

    return new Registry(Collections.unmodifiableMap(clone), Collections.unmodifiableMap(references));
  }
}