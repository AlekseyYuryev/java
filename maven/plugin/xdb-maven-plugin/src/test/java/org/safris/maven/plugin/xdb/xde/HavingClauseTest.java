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
import static org.safris.xdb.entities.DML.DESC;
import static org.safris.xdb.entities.DML.GT;
import static org.safris.xdb.entities.DML.LT;
import static org.safris.xdb.entities.DML.MAX;
import static org.safris.xdb.entities.DML.OR;
import static org.safris.xdb.entities.DML.SELECT;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.data;

import xdb.ddl.classicmodels;

public class HavingClauseTest extends IntegratedTest {
  @Test
  public void test() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    final data.Long c = new data.Long();
    final data.Decimal d = new data.Decimal();
    final RowIterator<? extends data.Numeric<?>> rows =
      SELECT(COUNT().AS(c), MAX(p.msrp).AS(d)).
      FROM(p).
      WHERE(GT(p.price, 10)).
      GROUP_BY(p.vendor).
      HAVING(OR(LT(c, 10), GT(d, 10))).
      ORDER_BY(DESC(c)).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(10), rows.nextEntity().get());
  }
}