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

package org.safris.maven.plugin.dbb.jsql;

import static org.safris.dbb.jsql.DML.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.dbb.ddlx.runner.Derby;
import org.safris.dbb.ddlx.runner.MySQL;
import org.safris.dbb.ddlx.runner.PostgreSQL;
import org.safris.dbb.ddlx.runner.SQLite;
import org.safris.dbb.jsql.RowIterator;
import org.safris.dbb.jsql.classicmodels;
import org.safris.dbb.jsql.type;
import org.safris.maven.plugin.dbb.jsql.runner.VendorSchemaRunner;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(classicmodels.class)
@VendorSchemaRunner.Test({Derby.class, SQLite.class})
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class})
public class QuantifiedComparisonPredicateTest {
  @Test
  @VendorSchemaRunner.Unsupported(SQLite.class)
  public void testAll() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(c).
      WHERE(
        LT(c.creditLimit,
          ALL(SELECT(COUNT()).
            FROM(p).
            WHERE(NE(p.purchaseDate, p.shippedDate))))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Integer.valueOf(24), rows.nextEntity().get());
    }
  }

  @Test
  @VendorSchemaRunner.Unsupported(SQLite.class)
  public void testAny() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(c).
      WHERE(
        GT(c.customerNumber,
          ANY(SELECT(COUNT()).
            FROM(p).
            WHERE(GT(p.purchaseDate, p.shippedDate))))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertTrue(rows.nextEntity().get() > 100);
    }
  }

  @Test
  @VendorSchemaRunner.Unsupported(SQLite.class)
  public void testSome() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(c).
      WHERE(
        GT(c.customerNumber,
          SOME(SELECT(COUNT()).
            FROM(p).
            WHERE(LT(p.purchaseDate, p.shippedDate))))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertTrue(rows.nextEntity().get() > 50);
    }
  }
}