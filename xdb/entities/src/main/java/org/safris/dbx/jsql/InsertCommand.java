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

package org.safris.dbx.jsql;

import java.io.IOException;

import org.safris.dbx.jsql.Insert.INSERT;
import org.safris.dbx.jsql.Insert.VALUES;

final class InsertCommand extends Command {
  private final INSERT<?> insert;
  private VALUES<?> values;

  public InsertCommand(final INSERT<?> insert) {
    this.insert = insert;
  }

  public INSERT<?> insert() {
    return insert;
  }

  public void add(final VALUES<?> values) {
    this.values = values;
  }

  public VALUES<?> values() {
    return values;
  }

  @Override
  protected void serialize(final Serialization serialization) throws IOException {
    final Serializer serializer = Serializer.getSerializer(serialization.vendor);
    if (values() != null)
      serializer.serialize(insert(), values(), serialization);
    else
      serializer.serialize(insert(), serialization);
  }
}