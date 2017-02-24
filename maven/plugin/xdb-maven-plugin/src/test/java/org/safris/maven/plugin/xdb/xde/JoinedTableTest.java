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

package org.safris.maven.plugin.xdb.xde;

import static org.safris.xdb.entities.DML.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.dbx.ddlx.runner.Derby;
import org.safris.dbx.ddlx.runner.MySQL;
import org.safris.dbx.ddlx.runner.PostgreSQL;
import org.safris.maven.common.Log;
import org.safris.maven.plugin.xdb.xde.runner.VendorSchemaRunner;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.classicmodels;
import org.safris.xdb.entities.type;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(classicmodels.class)
@VendorSchemaRunner.Test(Derby.class)
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class})
public class JoinedTableTest {
  @Test
  public void testCrossJoin() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      CROSS.JOIN(c).
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
      NATURAL.JOIN(c).
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
      LEFT.JOIN(c).ON(EQ(p.purchaseNumber, c.customerNumber)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertTrue(rows.nextEntity().get() > 300);
    }
  }

  @Test
  public void testRightOuterJoin() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      RIGHT.JOIN(c).ON(EQ(p.purchaseNumber, c.customerNumber)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertTrue(rows.nextEntity().get() > 100);
    }
  }

  @Test
  public void testFullOuterJoin() throws IOException, SQLException {
    try {
      final classicmodels.Purchase p = new classicmodels.Purchase();
      final classicmodels.Customer c = new classicmodels.Customer();
      try (final RowIterator<type.INT> rows =
        SELECT(COUNT()).
        FROM(p).
        FULL.JOIN(c).ON(EQ(p.purchaseNumber, c.customerNumber)).
        execute()) {
        Assert.assertTrue(rows.nextRow());
        Assert.assertTrue(rows.nextEntity().get() > 300);
      }
    }
    catch (final SQLSyntaxErrorException e) {
      // FIXME: Should we modify the SQL to use UNION here?
      if ("42X01".equals(e.getSQLState()) && e.getErrorCode() == 30000)
        Log.warn("Derby does not support FULL OUTER JOIN");
      else if ("42000".equals(e.getSQLState()) && e.getErrorCode() == 1064)
        Log.warn("MySQL does not support FULL OUTER JOIN");
      else
        throw e;
    }
  }
}