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
import java.math.BigDecimal;
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
public class QueryExpressionTest {
  @Test
  public void testFrom() throws IOException, SQLException {
    final classicmodels.Office o = new classicmodels.Office();
    try (final RowIterator<classicmodels.Office> rows =
      SELECT(o).
      FROM(o).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals("100 Market Street", rows.nextEntity().address1.get());
      Assert.assertTrue(rows.nextRow() && rows.nextRow() && rows.nextRow() && rows.nextRow() && rows.nextRow() && rows.nextRow());
      Assert.assertEquals("25 Old Broad Street", rows.nextEntity().address1.get());
      Assert.assertTrue(!rows.nextRow());
    }
  }

  @Test
  public void testFromMultiple() throws IOException, SQLException {
    final classicmodels.Office o = new classicmodels.Office();
    final classicmodels.Customer c = new classicmodels.Customer();
    try (final RowIterator<classicmodels.Address> rows =
      SELECT(o, c).
      FROM(o, c).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals("100 Market Street", rows.nextEntity().address1.get());
      Assert.assertEquals("54, rue Royale", rows.nextEntity().address1.get());
    }
  }

  @Test
  public void testWhere() throws IOException, SQLException {
    final classicmodels.Office o = new classicmodels.Office();
    try (final RowIterator<? extends data.DataType<?>> rows =
      SELECT(o.address1, o.latitude).
      FROM(o).
      WHERE(AND(
        EQ(o.phone, 81332245000l),
        OR(GT(o.latitude, 20d),
          LT(o.longitude, 100d)))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals("4-1 Kioicho", rows.nextEntity().get());
      Assert.assertEquals(35.6811759, ((BigDecimal)rows.nextEntity().get()).doubleValue(), 0.0000000001);
    }
  }
}