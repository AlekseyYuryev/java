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
import org.libx4j.rdb.jsql.type;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(classicmodels.class)
@VendorSchemaRunner.Test({Derby.class, SQLite.class})
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class, Oracle.class})
@Category(MixedTest.class)
public class JoinedTableTest {
  @Test
  public void testCrossJoin() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      CROSS_JOIN(c).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertTrue(rows.nextEntity().get() > 3900);
    }
  }

  @Test
  public void testNaturalJoin() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      NATURAL_JOIN(c).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertTrue(rows.nextEntity().get() > 300);
    }
  }

  @Test
  public void testInnerJoin() throws IOException, SQLException {
    final classicmodels.Employee e = new classicmodels.Employee();
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      JOIN(c).ON(EQ(p.customerNumber, c.customerNumber)).
      JOIN(e).ON(EQ(c.salesEmployeeNumber, e.employeeNumber)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertTrue(rows.nextEntity().get() > 300);
    }
  }

  @Test
  public void testLeftOuterJoin() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      LEFT_JOIN(c).ON(EQ(p.purchaseNumber, c.customerNumber)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertTrue(rows.nextEntity().get() > 300);
    }
  }

  @Test
  @VendorSchemaRunner.Unsupported(SQLite.class)
  public void testRightOuterJoin() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      RIGHT_JOIN(c).ON(EQ(p.purchaseNumber, c.customerNumber)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertTrue(rows.nextEntity().get() > 100);
    }
  }

  @Test
  @VendorSchemaRunner.Unsupported({Derby.class, SQLite.class, MySQL.class})
  public void testFullOuterJoin() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      FULL_JOIN(c).ON(EQ(p.purchaseNumber, c.customerNumber)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertTrue(rows.nextEntity().get() > 300);
    }
  }
}