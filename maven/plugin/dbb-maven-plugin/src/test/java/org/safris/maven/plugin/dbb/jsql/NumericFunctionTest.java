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

package org.safris.maven.plugin.dbb.jsql;

import static org.safris.dbb.jsql.DML.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.dbb.ddlx.runner.Derby;
import org.safris.dbb.ddlx.runner.MySQL;
import org.safris.dbb.ddlx.runner.PostgreSQL;
import org.safris.dbb.jsql.RowIterator;
import org.safris.dbb.jsql.Subject;
import org.safris.dbb.jsql.classicmodels;
import org.safris.dbb.jsql.model.select;
import org.safris.dbb.jsql.type.DECIMAL;
import org.safris.dbb.jsql.type.Numeric;
import org.safris.maven.plugin.dbb.jsql.runner.VendorSchemaRunner;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(classicmodels.class)
@VendorSchemaRunner.Test(Derby.class)
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class})
public class NumericFunctionTest {
  private static select.SELECT<? extends Subject<?>> selectVicinity(final double latitude, final double longitude, final double distance, final int limit) {
    final classicmodels.Customer c = new classicmodels.Customer();
    final DECIMAL d = c.longitude.clone();

    return SELECT(c, MUL(3959 * 2, ATAN2(
      SQRT(ADD(
        POW(SIN(DIV(MUL(SUB(c.latitude, latitude), PI()), 360)), 2),
        MUL(MUL(
          COS(DIV(MUL(c.latitude, PI()), 180)),
          COS(DIV(MUL(latitude, PI()), 180))),
          POW(SIN(DIV(MUL(SUB(c.longitude, longitude), PI()), 360)), 2)))),
      SQRT(ADD(
        SUB(1, POW(SIN(DIV(MUL(SUB(c.latitude, latitude), PI()), 360)), 2)),
        MUL(MUL(
          COS(DIV(MUL(latitude, PI()), 180)),
          COS(DIV(MUL(c.latitude, PI()), 180))),
          POW(SIN(DIV(MUL(SUB(c.longitude, longitude), PI()), 360)), 2)))))).
        AS(d)).
      FROM(c).
      GROUP_BY(c).
      HAVING(LT(d, distance)).
      ORDER_BY(DESC(d)).
      LIMIT(limit);
  }

  @Test
  public void testVicinity() throws IOException, SQLException {
    try (final RowIterator<? extends Subject<?>> rows = selectVicinity(37.78536811469731, -122.3931884765625, 10, 1).execute()) {
      while (rows.nextRow()) {
        final classicmodels.Customer c = (classicmodels.Customer)rows.nextEntity();
        Assert.assertEquals("Mini Wheels Co.", c.companyName.get());
        final DECIMAL d = (DECIMAL)rows.nextEntity();
        Assert.assertEquals(Double.valueOf(2.22069), d.get().doubleValue(), 0.00001);
      }
    }
  }

  @Test
  public void testFunctions() throws IOException, SQLException {
    final classicmodels.Office o = new classicmodels.Office();
    try (final RowIterator<? extends Numeric<?>> rows =
      SELECT(
        ROUND(o.longitude, 0),
        SIGN(o.longitude),
        FLOOR(ADD(o.latitude, o.longitude)),
        DIV(o.latitude, o.longitude),
        SQRT(o.latitude),
        CEIL(ABS(o.longitude)),
        ASIN(SIN(o.latitude)),
        ACOS(COS(o.latitude)),
        ATAN(TAN(o.latitude)),
        MOD(o.latitude, 1.2),
        MOD(o.latitude, -1.2),
        MOD(o.latitude, o.latitude),
        EXP(o.latitude),
        LN(o.latitude),
        LOG(2, o.latitude),
        LOG2(o.latitude),
        LOG10(o.latitude)).
      FROM(o).
      WHERE(GT(o.latitude, 0d)).
      ORDER_BY(DESC(o.latitude)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(0, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(-1d, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(51d, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(-614.3756630920124, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(7.177405770889647, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(1, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(1.249671142563306, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(1.249671142563306, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(1.249671142563306, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(1.1151535999999997, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(1.1151535999999997, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(0d, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(2.35910321724922E22, rows.nextEntity().get().doubleValue(), 5.5E7);
      Assert.assertEquals(3.9418760090484146, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(5.686924970053327, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(5.686924970053327, rows.nextEntity().get().doubleValue(), 0.0000000001);
      Assert.assertEquals(1.7119349990765391, rows.nextEntity().get().doubleValue(), 0.0000000001);
    }
  }
}