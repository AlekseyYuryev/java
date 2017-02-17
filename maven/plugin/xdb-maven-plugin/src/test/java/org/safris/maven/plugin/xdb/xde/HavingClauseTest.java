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

import static org.safris.xdb.entities.DML.DESC;
import static org.safris.xdb.entities.DML.GT;
import static org.safris.xdb.entities.DML.LT;
import static org.safris.xdb.entities.DML.SELECT;
import static org.safris.xdb.entities.DML.SIN;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.commons.test.LoggableTest;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.classicmodels;
import org.safris.xdb.entities.type;

@RunWith(ClassicModelsTestRunner.class)
public class HavingClauseTest extends LoggableTest {
  @Test
  public void test() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    final type.DECIMAL d = p.msrp.clone();
    try (final RowIterator<type.DECIMAL> rows =
      SELECT(SIN(p.msrp).AS(d)).
      FROM(p).
      WHERE(GT(p.price, 10)).
      GROUP_BY(p).
      HAVING(LT(d, 10)).
      ORDER_BY(DESC(d)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(0.9995201585807313, rows.nextEntity().get().doubleValue(), 0.0000000001);
    }
  }
}