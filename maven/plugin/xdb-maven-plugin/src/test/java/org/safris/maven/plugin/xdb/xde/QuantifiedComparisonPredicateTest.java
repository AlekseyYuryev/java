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

import static org.safris.xdb.entities.DML.ALL;
import static org.safris.xdb.entities.DML.ANY;
import static org.safris.xdb.entities.DML.*;
import static org.safris.xdb.entities.DML.EQ;
import static org.safris.xdb.entities.DML.GT;
import static org.safris.xdb.entities.DML.LT;
import static org.safris.xdb.entities.DML.SELECT;
import static org.safris.xdb.entities.DML.SOME;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.data;

import xdb.ddl.classicmodels;

public class QuantifiedComparisonPredicateTest extends IntegratedTest {
  @Test
  public void testALL() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(c).
      WHERE(LT(c.customerNumber, ALL(SELECT(COUNT()).FROM(p).WHERE(NE(p.purchaseDate, p.shippedDate))))).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(57), rows.nextEntity().get());
  }

  @Test
  public void testANY() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(c).
      WHERE(GT(c.customerNumber, ANY(SELECT(COUNT()).FROM(p).WHERE(GT(p.purchaseDate, p.shippedDate))))).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(122), rows.nextEntity().get());
  }

  @Test
  public void testSOME() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(c).
      WHERE(GT(c.customerNumber, SOME(SELECT(COUNT()).FROM(p).WHERE(LT(p.purchaseDate, p.shippedDate))))).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(57), rows.nextEntity().get());
  }
}