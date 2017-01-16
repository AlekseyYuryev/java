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

import static org.safris.xdb.entities.DML.ADD;
import static org.safris.xdb.entities.DML.ATAN2;
import static org.safris.xdb.entities.DML.COS;
import static org.safris.xdb.entities.DML.DESC;
import static org.safris.xdb.entities.DML.DIV;
import static org.safris.xdb.entities.DML.LT;
import static org.safris.xdb.entities.DML.MUL;
import static org.safris.xdb.entities.DML.PI;
import static org.safris.xdb.entities.DML.POW;
import static org.safris.xdb.entities.DML.SELECT;
import static org.safris.xdb.entities.DML.SIN;
import static org.safris.xdb.entities.DML.SQRT;
import static org.safris.xdb.entities.DML.SUB;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.Subject;
import org.safris.xdb.entities.datatype.Decimal;
import org.safris.xdb.entities.spec.select.SELECT;

import xdb.ddl.classicmodels;

public class Select2Test extends IntegratedTest {
  private static SELECT<? extends Subject<?>> selectVicinity(final double latitude, final double longitude, final double distance, final int limit) {
    final classicmodels.Customer c = new classicmodels.Customer();
    final Decimal d = new Decimal();

    return SELECT(c, MUL(3959 * 2, ATAN2(
      SQRT(ADD(
        POW(SIN(DIV(MUL(SUB(c.latitude, latitude), PI()), 360)), 2),
        MUL(
          COS(DIV(MUL(c.latitude, PI()), 180)),
          COS(DIV(MUL(latitude, PI()), 180)),
          POW(SIN(DIV(MUL(SUB(c.longitude, longitude), PI()), 360)), 2)))),
      SQRT(ADD(
        SUB(1, POW(SIN(DIV(MUL(SUB(c.latitude, latitude), PI()), 360)), 2)),
        MUL(
          COS(DIV(MUL(latitude, PI()), 180)),
          COS(DIV(MUL(c.latitude, PI()), 180)),
          POW(SIN(DIV(MUL(SUB(c.longitude, longitude), PI()), 360)), 2)))))).
        AS(d)).
      FROM(c).
      GROUP_BY(c).
      HAVING(LT(d, distance)).
      ORDER_BY(DESC(d)).
      LIMIT(limit);
  }

  @Test
  public void testVicinity() throws SQLException {
    final RowIterator<? extends Subject<?>> rows = selectVicinity(37.78536811469731, -122.3931884765625, 10, 1).execute();
    while (rows.nextRow()) {
      final classicmodels.Customer c = (classicmodels.Customer)rows.nextEntity();
      Assert.assertEquals("Mini Wheels Co.", c.companyName.get());
      final Decimal d = (Decimal)rows.nextEntity();
      Assert.assertEquals(Double.valueOf(2.2206911655259236), d.get());
    }
  }
}