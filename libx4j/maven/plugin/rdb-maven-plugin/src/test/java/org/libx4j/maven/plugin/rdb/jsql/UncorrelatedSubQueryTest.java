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

package org.libx4j.maven.plugin.rdb.jsql;

import static org.libx4j.rdb.jsql.DML.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lib4j.test.MixedTest;
import org.libx4j.maven.plugin.rdb.jsql.runner.VendorSchemaRunner;
import org.libx4j.rdb.ddlx.runner.Derby;
import org.libx4j.rdb.ddlx.runner.MySQL;
import org.libx4j.rdb.ddlx.runner.Oracle;
import org.libx4j.rdb.ddlx.runner.PostgreSQL;
import org.libx4j.rdb.ddlx.runner.SQLite;
import org.libx4j.rdb.jsql.RowIterator;
import org.libx4j.rdb.jsql.Transaction;
import org.libx4j.rdb.jsql.classicmodels;
import org.libx4j.rdb.jsql.data;
import org.libx4j.rdb.jsql.types;
import org.libx4j.rdb.jsql.world;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema({classicmodels.class, types.class, world.class})
@VendorSchemaRunner.Test({Derby.class, SQLite.class})
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class, Oracle.class})
@Category(MixedTest.class)
public class UncorrelatedSubQueryTest {
  @Test
  public void testAdd() throws IOException, SQLException {
    try (final Transaction transaction = new Transaction(types.class)) {
      types.Type t = new types.Type();
      final RowIterator<? extends data.Numeric<?>> rows =
        SELECT(
          ADD(t.tinyintType, SELECT(MIN(t.bigintType)).FROM(t)),
          SUB(t.smallintType, SELECT(AVG(t.intType)).FROM(t)),
          ADD(t.intType, SELECT(COUNT(t.smallintType)).FROM(t)),
          SUB(t.floatType, SELECT(MAX(t.tinyintType)).FROM(t)),
          ADD(t.doubleType, SELECT(MIN(t.decimalType)).FROM(t)),
          SUB(t.decimalType, SELECT(AVG(t.intType)).FROM(t)),
          ADD(t.bigintType, SELECT(MAX(t.floatType)).FROM(t))
        ).
        FROM(t).
        execute(transaction);

      Assert.assertTrue(rows.nextRow());
    }
  }

  // FIXME: Add more tests...
}