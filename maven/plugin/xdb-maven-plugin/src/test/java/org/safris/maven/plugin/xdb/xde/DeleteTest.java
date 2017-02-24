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

import static org.safris.dbx.jsql.DML.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.dbx.ddlx.runner.Derby;
import org.safris.dbx.ddlx.runner.MySQL;
import org.safris.dbx.ddlx.runner.PostgreSQL;
import org.safris.dbx.jsql.Transaction;
import org.safris.maven.plugin.xdb.xde.runner.VendorSchemaRunner;
import org.safris.xdb.entities.classicmodels;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(classicmodels.class)
@VendorSchemaRunner.Test(Derby.class)
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class})
public class DeleteTest {
  @Test
  public void testDeleteEntity() throws IOException, SQLException {
    try (final Transaction transaction = new Transaction(classicmodels.class)) {
      final classicmodels.Purchase p = new classicmodels.Purchase();
      p.purchaseNumber.set(10102l);
      p.customerNumber.set(181);
      final int[] results = DELETE(p).execute(transaction);

      Assert.assertEquals(1, results[0]);

      transaction.rollback();
    }
  }

  @Test
  public void testDeleteEntities() throws IOException, SQLException {
    try (final Transaction transaction = new Transaction(classicmodels.class)) {
      final classicmodels.Purchase p = new classicmodels.Purchase();
      p.purchaseNumber.set(10100l);
      p.customerNumber.set(363);

      final classicmodels.Payment pa = new classicmodels.Payment();
      pa.customerNumber.set(103);

      final int[] results = DELETE(p, pa).execute(transaction);

      Assert.assertEquals(1, results[0]);
      Assert.assertEquals(3, results[1]);

      transaction.rollback();
    }
  }

  @Test
  public void testDeleteWhere() throws IOException, SQLException {
    try (final Transaction transaction = new Transaction(classicmodels.class)) {
      final classicmodels.Purchase p = new classicmodels.Purchase();
      final int[] results = DELETE(p).WHERE(EQ(p.purchaseDate, LocalDate.parse("2003-01-09"))).execute(transaction);

      Assert.assertEquals(1, results[0]);

      transaction.rollback();
    }
  }

  @Test
  public void testDeleteAll() throws IOException, SQLException {
    try (final Transaction transaction = new Transaction(classicmodels.class)) {
      final classicmodels.PurchaseDetail p = new classicmodels.PurchaseDetail();
      final int[] results = DELETE(p).execute(transaction);

      Assert.assertTrue(results[0] > 2985);

      transaction.rollback();
    }
  }
}