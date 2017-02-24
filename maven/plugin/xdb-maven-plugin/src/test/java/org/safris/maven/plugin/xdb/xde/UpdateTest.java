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
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.dbx.ddlx.runner.Derby;
import org.safris.dbx.ddlx.runner.MySQL;
import org.safris.dbx.ddlx.runner.PostgreSQL;
import org.safris.maven.plugin.xdb.xde.runner.VendorSchemaRunner;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.Transaction;
import org.safris.xdb.entities.classicmodels;
import org.safris.xdb.entities.types;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema({types.class, classicmodels.class})
@VendorSchemaRunner.Test(Derby.class)
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class})
public class UpdateTest {
  @Test
  public void testUpdateEntity() throws IOException, SQLException {
    try (final Transaction transaction = new Transaction(types.class)) {
      classicmodels.Product p = new classicmodels.Product();
      final RowIterator<classicmodels.Product> rows =
        SELECT(p).
        FROM(p).
        LIMIT(1).
        execute(transaction);

      Assert.assertTrue(rows.nextRow());
      p = rows.nextEntity();

      p.price.set(BigDecimal.valueOf(20l));

      final int[] results = UPDATE(p).execute(transaction);
      Assert.assertEquals(1, results[0]);

      transaction.rollback();
    }
  }

  @Test
  public void testUpdateEntities() throws IOException, SQLException {
    try (final Transaction transaction = new Transaction(types.class)) {
      classicmodels.Product p = new classicmodels.Product();
      final RowIterator<classicmodels.Product> rows1 =
        SELECT(p).
        FROM(p).
        LIMIT(1).
        execute(transaction);

      Assert.assertTrue(rows1.nextRow());
      p = rows1.nextEntity();

      classicmodels.ProductLine pl = new classicmodels.ProductLine();
      final RowIterator<classicmodels.ProductLine> rows2 =
        SELECT(pl).
        FROM(pl).
        LIMIT(1).
        execute(transaction);

      Assert.assertTrue(rows2.nextRow());
      pl = rows2.nextEntity();

      p.quantityInStock.set(300);
      pl.description.set(new StringReader("New description"));

      final int[] results = UPDATE(p, pl).execute(transaction);
      Assert.assertEquals(1, results[0]);
      Assert.assertEquals(1, results[1]);

      transaction.rollback();
    }
  }

  @Test
  public void testUpdateSetWhere() throws IOException, SQLException {
    try (final Transaction transaction = new Transaction(types.class)) {
      types.Type t = new types.Type();
      final RowIterator<types.Type> rows =
        SELECT(t).
        FROM(t).
        LIMIT(1).
        execute(transaction);

      Assert.assertTrue(rows.nextRow());
      t = rows.nextEntity();

      final int[] results =
        UPDATE(t).
        SET(t.enumType, types.Type.EnumType.FOUR).
        WHERE(EQ(t.enumType, types.Type.EnumType.ONE)).
        execute(transaction);

      Assert.assertTrue(results[0] > 0);

      transaction.rollback();
    }
  }

  @Test
  public void testUpdateSet() throws IOException, SQLException {
    try (final Transaction transaction = new Transaction(types.class)) {
      types.Type t = new types.Type();
      final RowIterator<types.Type> rows =
        SELECT(t).
        FROM(t).
        LIMIT(1).
        execute(transaction);

      Assert.assertTrue(rows.nextRow());
      t = rows.nextEntity();

      final int[] results =
        UPDATE(t).
        SET(t.datetimeType, LocalDateTime.now()).
        execute(transaction);

      Assert.assertTrue(results[0] > 300);

      transaction.rollback();
    }
  }
}