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

import static org.safris.xdb.entities.DML.AND;
import static org.safris.xdb.entities.DML.COUNT;
import static org.safris.xdb.entities.DML.EQ;
import static org.safris.xdb.entities.DML.GT;
import static org.safris.xdb.entities.DML.GTE;
import static org.safris.xdb.entities.DML.LT;
import static org.safris.xdb.entities.DML.LTE;
import static org.safris.xdb.entities.DML.NE;
import static org.safris.xdb.entities.DML.OR;
import static org.safris.xdb.entities.DML.SELECT;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.data;

import xdb.ddl.classicmodels;

public class ComparisonPredicateTest extends IntegratedTest {
  @Test
  public void testLt() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(OR(LT(p.comments, "a"), LT("a", p.status), LT(p.comments, p.status))).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(80), rows.nextEntity().get());
  }

  @Test
  public void testLte() throws IOException, SQLException {
    final classicmodels.Customer c = new classicmodels.Customer();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(c).
      WHERE(AND(LTE(c.creditLimit, c.customerNumber), LTE(c.longitude, c.phone), LTE(45, c.phone), LTE(c.creditLimit, 329939933l))).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(24), rows.nextEntity().get());
  }

  @Test
  public void testEq() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(AND(EQ(p.status, p.comments), EQ(p.comments, p.status))).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(0), rows.nextEntity().get());
  }

  @Test
  public void testNe() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(NE(p.purchaseDate, p.shippedDate)).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(312), rows.nextEntity().get());
  }

  @Test
  public void testGt() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(GT(p.status, classicmodels.Purchase.Status.CANCELLED)).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(320), rows.nextEntity().get());
  }

  @Test
  public void testGte() throws IOException, SQLException {
    final classicmodels.PurchaseDetail p = new classicmodels.PurchaseDetail();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(GTE(p.priceEach, p.quantity)).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(2875), rows.nextEntity().get());
  }
}