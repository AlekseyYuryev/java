/* Copyright (c) 2015 lib4j
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

import org.libx4j.rdb.vendor.DBVendor;

abstract class Predicate extends type.BOOLEAN {
  protected final kind.DataType<?> dataType;

  protected Predicate(final kind.DataType<?> dataType) {
    this.dataType = dataType;
  }

  @Override
  protected final String compile(final DBVendor vendor) {
    throw new UnsupportedOperationException();
  }
}