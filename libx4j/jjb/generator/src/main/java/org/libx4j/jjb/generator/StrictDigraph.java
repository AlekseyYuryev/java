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

import java.util.function.Function;

import org.lib4j.util.IndirectDigraph;

class StrictDigraph<T,R> extends IndirectDigraph<T,R> {
  private final String selfLinkErrorPrefix;

  public StrictDigraph(final String selfLinkErrorPrefix, final Function<T,R> reference) {
    super(reference);
    this.selfLinkErrorPrefix = selfLinkErrorPrefix;
  }

  @Override
  public boolean addEdgeRef(final T from, final R to) {
    if (reference.apply(from).equals(to))
      throw new ValidationException(selfLinkErrorPrefix + ": " + reference.apply(from) + " -> " + to);

    return super.addEdgeRef(from, to);
  }
}