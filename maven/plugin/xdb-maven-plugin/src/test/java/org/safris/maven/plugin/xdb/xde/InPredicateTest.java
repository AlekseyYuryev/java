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
import static org.safris.xdb.entities.DML.IN;
import static org.safris.xdb.entities.DML.SELECT;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.safris.xdb.entities.DML.NOT;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.data;

import xdb.ddl.classicmodels;

public class InPredicateTest extends IntegratedTest {
  @Test
  public void testINList() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(IN(p.productLine, "Ships", "Planes", "Trains")).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(24), rows.nextEntity().get());
  }

  @Test
  public void testNOT_INList() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(NOT.IN(p.productLine, "Ships", "Planes", "Trains")).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(86), rows.nextEntity().get());
  }

  @Test
  public void testINSubQuery() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(IN(p.productLine, SELECT(p.productLine).FROM(p))).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(110), rows.nextEntity().get());
  }

  @Test
  public void testNOT_INSubQuery() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    final RowIterator<data.Long> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(NOT.IN(p.productLine, SELECT(p.productLine).FROM(p))).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(Long.valueOf(0), rows.nextEntity().get());
  }
}