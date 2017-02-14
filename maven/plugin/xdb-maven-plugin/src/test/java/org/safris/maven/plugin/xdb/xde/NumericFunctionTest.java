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

import static org.safris.xdb.entities.DML.ABS;
import static org.safris.xdb.entities.DML.ACOS;
import static org.safris.xdb.entities.DML.ADD;
import static org.safris.xdb.entities.DML.ASIN;
import static org.safris.xdb.entities.DML.ATAN;
import static org.safris.xdb.entities.DML.ATAN2;
import static org.safris.xdb.entities.DML.CEIL;
import static org.safris.xdb.entities.DML.COS;
import static org.safris.xdb.entities.DML.DESC;
import static org.safris.xdb.entities.DML.DIV;
import static org.safris.xdb.entities.DML.EXP;
import static org.safris.xdb.entities.DML.FLOOR;
import static org.safris.xdb.entities.DML.GT;
import static org.safris.xdb.entities.DML.LN;
import static org.safris.xdb.entities.DML.LOG;
import static org.safris.xdb.entities.DML.LOG10;
import static org.safris.xdb.entities.DML.LOG2;
import static org.safris.xdb.entities.DML.LT;
import static org.safris.xdb.entities.DML.MOD;
import static org.safris.xdb.entities.DML.MUL;
import static org.safris.xdb.entities.DML.PI;
import static org.safris.xdb.entities.DML.POW;
import static org.safris.xdb.entities.DML.ROUND;
import static org.safris.xdb.entities.DML.SELECT;
import static org.safris.xdb.entities.DML.SIGN;
import static org.safris.xdb.entities.DML.SIN;
import static org.safris.xdb.entities.DML.SQRT;
import static org.safris.xdb.entities.DML.SUB;
import static org.safris.xdb.entities.DML.TAN;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.commons.test.LoggableTest;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.Subject;
import org.safris.xdb.entities.classicmodels;
import org.safris.xdb.entities.type.DECIMAL;
import org.safris.xdb.entities.type.Numeric;
import org.safris.xdb.entities.model.select;

@RunWith(ClassicModelsTestRunner.class)
public class NumericFunctionTest extends LoggableTest {
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
    final RowIterator<? extends Subject<?>> rows = selectVicinity(37.78536811469731, -122.3931884765625, 10, 1).execute();
    while (rows.nextRow()) {
      final classicmodels.Customer c = (classicmodels.Customer)rows.nextEntity();
      Assert.assertEquals("Mini Wheels Co.", c.companyName.get());
      final DECIMAL d = (DECIMAL)rows.nextEntity();
      Assert.assertEquals(Double.valueOf(2.2206911655259236), d.get().doubleValue(), 0.0000000001);
    }
  }

  @Test
  public void testFunctions() throws IOException, SQLException {
    final classicmodels.Office o = new classicmodels.Office();
    final RowIterator<? extends Numeric<?>> rows =
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
        MOD(o.latitude, o.latitude),
        EXP(o.latitude),
        LN(o.latitude),
        LOG(o.latitude, 2),
        LOG2(o.latitude),
        LOG10(o.latitude)).
      FROM(o).
      WHERE(GT(o.latitude, 0d)).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(-122d, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(-1d, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(-85d, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(-0.3087877978632434, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(6.147703920977327, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(123d, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(0.0951516569224807, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(0.09515165692248098, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(0.0951516569224807, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(0.5942635000000009, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(0d, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(2.5932243185642152E16, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(3.6321573318496814, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(5.240095370388024, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(5.240095370388024, rows.nextEntity().get().doubleValue(), 0.0000000001);
    Assert.assertEquals(1.5774258866267548, rows.nextEntity().get().doubleValue(), 0.0000000001);
  }
}