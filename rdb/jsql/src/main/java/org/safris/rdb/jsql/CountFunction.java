/* Copyright (c) 2017 Seva Safris
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

package org.safris.rdb.jsql;

import java.io.IOException;

final class CountFunction extends Expression<Long> {
  protected static final CountFunction STAR  = new CountFunction();

  protected final boolean distinct;
  protected final String function = "COUNT";
  protected final type.DataType<?> column;

  protected CountFunction(final type.DataType<?> column, final boolean distinct) {
    this.column = column;
    this.distinct = distinct;
  }

  private CountFunction() {
    this.distinct = false;
    this.column = null;
  }

  @Override
  protected final void serialize(final Serialization serialization) throws IOException {
    Serializer.getSerializer(serialization.vendor).serialize(this, serialization);
  }
}