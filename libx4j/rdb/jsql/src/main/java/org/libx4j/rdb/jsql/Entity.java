/* Copyright (c) 2014 lib4j
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

package org.libx4j.rdb.jsql;

import java.io.IOException;
import java.util.Set;

public abstract class Entity extends Subject<Entity> implements Cloneable {
  protected final type.DataType<?>[] column;
  protected final type.DataType<?>[] primary;
  private final boolean wasSelected;

  protected Entity(final boolean wasSelected, final type.DataType<?>[] column, final type.DataType<?>[] primary) {
    this.wasSelected = wasSelected;
    this.column = column;
    this.primary = primary;
  }

  protected Entity(final Entity entity) {
    this.wasSelected = false;
    this.column = entity.column.clone();
    this.primary = entity.primary.clone();
  }

  protected Entity() {
    this.wasSelected = false;
    this.column = null;
    this.primary = null;
  }

  protected final boolean wasSelected() {
    return wasSelected;
  }

  @SuppressWarnings("unchecked")
  protected final Class<? extends Schema> schema() {
    return (Class<? extends Schema>)getClass().getEnclosingClass();
  }

  @Override
  protected final Entity evaluate(final Set<Evaluable> visited) {
    return this;
  }

  @Override
  protected final void compile(final Compilation compilation) throws IOException {
    Compiler.getCompiler(compilation.vendor).compile(this, compilation);
  }

  protected abstract String name();
  protected abstract Entity newInstance();

  @Override
  protected abstract Entity clone();
}