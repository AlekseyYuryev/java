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
import org.libx4j.rdb.jsql.classicmodels;
import org.libx4j.rdb.jsql.data;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(classicmodels.class)
@VendorSchemaRunner.Test({Derby.class, SQLite.class})
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class, Oracle.class})
@Category(MixedTest.class)
public class HavingClauseTest {
  @Test
  public void test() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    final data.DECIMAL.UNSIGNED d = p.msrp.clone();
    try (final RowIterator<data.DECIMAL.UNSIGNED> rows =
      SELECT(
        SIN(p.msrp).AS(d),
        SELECT(SIN(p.msrp).AS(d)).
        FROM(p).
        WHERE(GT(p.price, 10)).
        GROUP_BY(p).
        HAVING(LT(d, 10)).
        ORDER_BY(DESC(d)).LIMIT(1)).
      FROM(p).
      WHERE(GT(p.price, 10)).
      GROUP_BY(p).
      HAVING(LT(d, 10)).
      ORDER_BY(DESC(d)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(0.9995201585807313, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(0.9995201585807313, rows.nextEntity().get().doubleValue(), 0.0000000001);
    }
  }
}