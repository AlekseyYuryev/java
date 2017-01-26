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

import static org.safris.xdb.entities.DML.COUNT;
import static org.safris.xdb.entities.DML.CROSS;
import static org.safris.xdb.entities.DML.EQ;
import static org.safris.xdb.entities.DML.NATURAL;
import static org.safris.xdb.entities.DML.SELECT;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import org.junit.Assert;
import org.junit.Test;
import org.safris.maven.common.Log;
import org.safris.xdb.entities.DML.OUTER;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.data;

import xdb.ddl.classicmodels;

public class JoinedTableTest extends IntegratedTest {
  @Test
  public void testCrossJoin() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(p).
      JOIN(CROSS, c).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(39772), rows.nextEntity().get());
  }

  @Test
  public void testNaturalJoin() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(p).
      JOIN(NATURAL, c).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(326), rows.nextEntity().get());
  }

  @Test
  public void testInnerJoin() throws IOException, SQLException {
    final classicmodels.Employee e = new classicmodels.Employee();
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(p).
      JOIN(c).ON(EQ(p.customerNumber, c.customerNumber)).
      JOIN(e).ON(EQ(c.salesEmployeeNumber, e.employeeNumber)).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(326), rows.nextEntity().get());
  }

  @Test
  public void testLeftOuterJoin() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(p).
      JOIN(OUTER.LEFT, c).ON(EQ(p.purchaseNumber, c.customerNumber)).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(326), rows.nextEntity().get());
  }

  @Test
  public void testRightOuterJoin() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(p).
      JOIN(OUTER.RIGHT, c).ON(EQ(p.purchaseNumber, c.customerNumber)).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(122), rows.nextEntity().get());
  }

  @Test
  public void testFullOuterJoin() throws IOException, SQLException {
    try {
      final classicmodels.Purchase p = new classicmodels.Purchase();
      final classicmodels.Customer c = new classicmodels.Customer();
      final RowIterator<data.Long> rows =
        SELECT(COUNT()).
        FROM(p).
        JOIN(OUTER.FULL, c).ON(EQ(p.purchaseNumber, c.customerNumber)).
        execute();

      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Long.valueOf(326), rows.nextEntity().get());
    }
    catch (final SQLSyntaxErrorException e) {
      // FIXME: Should we modify the SQL to use UNION here?
      if ("42X01".equals(e.getSQLState()) && e.getErrorCode() == 30000)
        Log.warn("Derby does not support FULL OUTER JOIN");
      else
        throw e;
    }
  }
}