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

import static org.safris.xdb.entities.DML.BETWEEN;
import static org.safris.xdb.entities.DML.COUNT;
import static org.safris.xdb.entities.DML.SELECT;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.commons.test.LoggableTest;
import org.safris.xdb.entities.DML.NOT;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.classicmodels;
import org.safris.xdb.entities.type;
import org.safris.xdb.schema.VendorIntegration;
import org.safris.xdb.schema.VendorTest;
import org.safris.xdb.schema.vendor.Derby;
import org.safris.xdb.schema.vendor.MySQL;
import org.safris.xdb.schema.vendor.PostgreSQL;

@RunWith(EntityVendorClassRunner.class)
@EntityClass(classicmodels.class)
@VendorTest(Derby.class)
@VendorIntegration({MySQL.class, PostgreSQL.class})
public class BetweenPredicateTest extends LoggableTest {
  @Test
  public void testBetween1() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(NOT.BETWEEN(p.shippedDate, p.purchaseDate, p.requiredDate)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Integer.valueOf(1), rows.nextEntity().get());
    }
  }

  @Test
  public void testBetween2() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(BETWEEN(p.msrp, p.price, 100)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Integer.valueOf(59), rows.nextEntity().get());
    }
  }

  @Test
  public void testBetween3() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(BETWEEN(p.scale, "a", "b")).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Integer.valueOf(0), rows.nextEntity().get());
    }
  }

  @Test
  public void testBetween4() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(BETWEEN(p.quantityInStock, 500, 1000)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Integer.valueOf(7), rows.nextEntity().get());
    }
  }
}