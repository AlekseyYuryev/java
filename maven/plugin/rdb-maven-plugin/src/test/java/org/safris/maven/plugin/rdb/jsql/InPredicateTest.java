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

package org.safris.maven.plugin.rdb.jsql;

import static org.safris.rdb.jsql.DML.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.safris.commons.test.MixedTest;
import org.safris.maven.plugin.rdb.jsql.runner.VendorSchemaRunner;
import org.safris.rdb.ddlx.runner.Derby;
import org.safris.rdb.ddlx.runner.MySQL;
import org.safris.rdb.ddlx.runner.Oracle;
import org.safris.rdb.ddlx.runner.PostgreSQL;
import org.safris.rdb.ddlx.runner.SQLite;
import org.safris.rdb.jsql.RowIterator;
import org.safris.rdb.jsql.classicmodels;
import org.safris.rdb.jsql.type;
import org.safris.rdb.jsql.DML.NOT;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(classicmodels.class)
@VendorSchemaRunner.Test({Derby.class, SQLite.class})
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class, Oracle.class})
@Category(MixedTest.class)
public class InPredicateTest {
  @Test
  public void testInList() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(IN(p.productLine, "Ships", "Planes", "Trains")).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Integer.valueOf(24), rows.nextEntity().get());
    }
  }

  @Test
  public void testNotInList() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(NOT.IN(p.productLine, "Ships", "Planes", "Trains")).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Integer.valueOf(86), rows.nextEntity().get());
    }
  }

  @Test
  public void testInSubQuery() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(IN(p.productLine, SELECT(p.productLine).FROM(p))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Integer.valueOf(110), rows.nextEntity().get());
    }
  }

  @Test
  public void testNotInSubQuery() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(NOT.IN(p.productLine, SELECT(p.productLine).FROM(p))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Integer.valueOf(0), rows.nextEntity().get());
    }
  }
}