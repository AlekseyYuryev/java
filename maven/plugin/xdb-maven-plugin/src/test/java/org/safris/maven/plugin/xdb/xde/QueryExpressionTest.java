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
import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.Subject;
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
    try (final RowIterator<? extends type.DataType<?>> rows =
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

  @Test
  public void testMixedSelect() throws IOException, SQLException {
    final classicmodels.Office o = new classicmodels.Office();
    try (final RowIterator<? extends Subject<?>> rows =
      SELECT(
        o.latitude,
        o.longitude,
        o,
        o.latitude,
        o.longitude,
        o,
        o.latitude,
        o.longitude).
      FROM(o).
      WHERE(GT(o.latitude, 0d)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(37.7942635, ((BigDecimal)((type.DataType<?>)rows.nextEntity()).get()).doubleValue(), 0.0000000001);
      Assert.assertEquals(-122.3955861, ((BigDecimal)((type.DataType<?>)rows.nextEntity()).get()).doubleValue(), 0.0000000001);
      Assert.assertEquals("San Francisco", ((classicmodels.Office)rows.nextEntity()).city.get());
      Assert.assertEquals(37.7942635, ((BigDecimal)((type.DataType<?>)rows.nextEntity()).get()).doubleValue(), 0.0000000001);
      Assert.assertEquals(-122.3955861, ((BigDecimal)((type.DataType<?>)rows.nextEntity()).get()).doubleValue(), 0.0000000001);
      Assert.assertEquals("San Francisco", ((classicmodels.Office)rows.nextEntity()).city.get());
      Assert.assertEquals(37.7942635, ((BigDecimal)((type.DataType<?>)rows.nextEntity()).get()).doubleValue(), 0.0000000001);
      Assert.assertEquals(-122.3955861, ((BigDecimal)((type.DataType<?>)rows.nextEntity()).get()).doubleValue(), 0.0000000001);
    }
  }
}