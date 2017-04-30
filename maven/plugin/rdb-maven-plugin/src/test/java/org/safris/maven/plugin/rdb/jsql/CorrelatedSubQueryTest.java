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

package org.safris.maven.plugin.rdb.jsql;

import static org.safris.rdb.jsql.DML.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.safris.commons.test.MixedTest;
import org.safris.maven.plugin.rdb.jsql.runner.VendorSchemaRunner;
import org.safris.rdb.ddlx.runner.Derby;
import org.safris.rdb.ddlx.runner.MySQL;
import org.safris.rdb.ddlx.runner.Oracle;
import org.safris.rdb.ddlx.runner.PostgreSQL;
import org.safris.rdb.ddlx.runner.SQLite;
import org.safris.rdb.jsql.RowIterator;
import org.safris.rdb.jsql.Subject;
import org.safris.rdb.jsql.classicmodels;
import org.safris.rdb.jsql.type;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(classicmodels.class)
@VendorSchemaRunner.Test({Derby.class, SQLite.class})
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class, Oracle.class})
@Category(MixedTest.class)
public class CorrelatedSubQueryTest {
  @Test
  public void testWhere() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c1 = new classicmodels.Customer();
    final classicmodels.Customer c2 = new classicmodels.Customer();
    try (final RowIterator<? extends Subject<?>> rows =
      SELECT(p, c2).
      FROM(p,
        SELECT(c1).
        FROM(c1).
        WHERE(GT(c1.creditLimit, 10)).
        AS(c2)).
      WHERE(AND(
        LT(p.purchaseDate, p.requiredDate),
        EQ(p.customerNumber, c2.customerNumber))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertTrue(rows.nextEntity() instanceof classicmodels.Purchase);
      Assert.assertTrue(rows.nextEntity() instanceof classicmodels.Customer);
    }
  }

  @Test
  public void testSelect() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    final type.INT.UNSIGNED s = c.salesEmployeeNumber.clone();
    try (final RowIterator<? extends Subject<?>> rows =
      SELECT(p,
        SELECT(MAX(c.salesEmployeeNumber)).
        FROM(c).
        WHERE(GT(c.creditLimit, 10)).
        AS(s)).
      FROM(p).
      WHERE(
        LT(p.purchaseDate, p.requiredDate)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertTrue(rows.nextEntity() instanceof classicmodels.Purchase);
    }
  }
}