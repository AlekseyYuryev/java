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
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.xdb.entities.Interval;
import org.safris.xdb.entities.Interval.Unit;
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
public class DateTimeValueExpressionTest {
  @Test
  public void testInSelect() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    try (final RowIterator<type.DATE> rows =
      SELECT(
        SUB(p.purchaseDate, new Interval(2, Unit.DAYS)),
        ADD(p.purchaseDate, new Interval(3, Unit.DECADES))).
      FROM(p).
      LIMIT(1).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(LocalDate.parse("2003-01-04"), rows.nextEntity().get());
      Assert.assertEquals(LocalDate.parse("2033-01-06"), rows.nextEntity().get());
    }
  }

  @Test
  public void testInWhere() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(GT(p.shippedDate, ADD(p.requiredDate, new Interval(2, Unit.DAYS)))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Integer.valueOf(1), rows.nextEntity().get());
    }
  }
}