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

public abstract class JSBundle {
  protected abstract String getSpec();

  protected static <T>T get(final Property<T> property) {
    return property.get();
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
}