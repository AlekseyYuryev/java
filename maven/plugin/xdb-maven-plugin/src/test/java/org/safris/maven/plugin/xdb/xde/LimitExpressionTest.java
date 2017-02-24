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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.dbx.ddlx.runner.Derby;
import org.safris.dbx.ddlx.runner.MySQL;
import org.safris.dbx.ddlx.runner.PostgreSQL;
import org.safris.maven.plugin.xdb.xde.runner.VendorSchemaRunner;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.classicmodels;
import org.safris.xdb.entities.type;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(classicmodels.class)
@VendorSchemaRunner.Test(Derby.class)
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class})
public class LimitExpressionTest {
  @Test
  public void testLimit() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    try (final RowIterator<type.DECIMAL.UNSIGNED> rows =
      SELECT(p.msrp, p.price).
      FROM(p).
      ORDER_BY(p.msrp, p.price).
      LIMIT(3).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Double.valueOf(33.19), rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(Double.valueOf(22.57), rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Double.valueOf(35.36), rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(Double.valueOf(15.91), rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Double.valueOf(37.76), rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(Double.valueOf(16.24), rows.nextEntity().get().doubleValue(), 0.0000000001);
    }
  }

  @Test
  public void testLimitOffset() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    try (final RowIterator<type.DECIMAL.UNSIGNED> rows =
      SELECT(p.msrp, p.price).
      FROM(p).
      ORDER_BY(p.msrp, p.price).
      LIMIT(2).
      OFFSET(1).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Double.valueOf(35.36), rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(Double.valueOf(15.91), rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Double.valueOf(37.76), rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(Double.valueOf(16.24), rows.nextEntity().get().doubleValue(), 0.0000000001);
    }
  }
}