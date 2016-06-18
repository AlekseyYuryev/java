/* Copyright (c) 2015 Seva Safris
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

package org.safris.commons.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public abstract class JSObject extends JSObjectUtil {
  @SuppressWarnings("unchecked")
  public static <T extends JSObject>T parse(final Class<T> clazz, final InputStream in) throws DecodeException, IOException {
    try {
      return (T)decode(in, next(in), clazz.newInstance());
    }
    catch (final InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T extends JSObject>T parse(final Class<T> clazz, final String json) throws DecodeException, IOException {
    return parse(clazz, new ByteArrayInputStream(json.getBytes()));
  }

  protected static <T>void clone(final Property<T> property, final Property<T> clone) {
    property.clone(clone);
  }

  protected static <T>T get(final Property<T> property) {
    return property.get();
  }

  protected static <T>void set(final Property<T> property, final T value) {
    property.set(value);
  }

  protected static <T>boolean wasSet(final Property<T> property) {
    return property.wasSet();
  }

  protected static <T>T encode(final Property<T> property) throws EncodeException {
    return property.encode();
  }

  protected static <T>void decode(final Property<T> property) throws DecodeException {
    property.decode();
  }

  public JSObject(final JSObject clone) {
  }

  public JSObject() {
  }

  protected abstract String _name();

  protected String _encode(final int depth) {
    return "";
  }

  protected abstract Binding<?> _getBinding(final String name);

  protected abstract Collection<Binding<?>> _bindings();

  protected abstract JSBundle _bundle();
}