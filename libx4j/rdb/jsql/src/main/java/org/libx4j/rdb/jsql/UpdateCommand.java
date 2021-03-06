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

package org.libx4j.rdb.jsql;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.libx4j.rdb.jsql.UpdateImpl.SET;
import org.libx4j.rdb.jsql.UpdateImpl.UPDATE;
import org.libx4j.rdb.jsql.UpdateImpl.WHERE;

final class UpdateCommand extends Command {
  private final UPDATE update;
  private List<SET> set;
  private WHERE where;

  protected UpdateCommand(final UPDATE update) {
    this.update = update;
  }

  protected UPDATE update() {
    return update;
  }

  protected List<SET> set() {
    return set;
  }

  protected void add(final SET set) {
    if (this.set == null)
      this.set = new ArrayList<SET>();

    this.set.add(set);
  }

  protected WHERE where() {
    return where;
  }

  protected void add(final WHERE where) {
    this.where = where;
  }

  @Override
  protected void compile(final Compilation compilation) throws IOException {
    final Compiler compiler = Compiler.getCompiler(compilation.vendor);
    if (set() != null)
      compiler.compile(update(), set(), where(), compilation);
    else
      compiler.compile(update(), compilation);
  }
}