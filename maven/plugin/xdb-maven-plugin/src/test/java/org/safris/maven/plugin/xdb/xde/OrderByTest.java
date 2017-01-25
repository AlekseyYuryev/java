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
import static org.safris.xdb.entities.DML.SELECT;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.data;

import xdb.ddl.classicmodels;

public class OrderByTest extends IntegratedTest {
  @Test
  public void testORDER_BY() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    final RowIterator<data.Decimal> rows =
      SELECT(p.msrp, p.price).
      FROM(p).
      ORDER_BY(DESC(p.price), p.msrp).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Double.valueOf(147.74), rows.nextEntity().get());
  }
}