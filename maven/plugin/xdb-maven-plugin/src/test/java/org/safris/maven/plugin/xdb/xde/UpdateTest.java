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

import static org.safris.xdb.entities.DML.EQ;
import static org.safris.xdb.entities.DML.UPDATE;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.commons.test.LoggableTest;
import org.safris.xdb.entities.classicmodels;
import org.safris.xdb.schema.VendorIntegration;
import org.safris.xdb.schema.VendorTest;
import org.safris.xdb.schema.vendor.Derby;
import org.safris.xdb.schema.vendor.MySQL;
import org.safris.xdb.schema.vendor.PostgreSQL;

@RunWith(EntityVendorClassRunner.class)
@EntityClass(classicmodels.class)
@VendorTest(Derby.class)
@VendorIntegration({MySQL.class, PostgreSQL.class})
public class UpdateTest extends LoggableTest {
  @Test
  public void testUpdateEntity() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    p.purchaseNumber.set(10334l);
    p.status.set(classicmodels.Purchase.Status.SHIPPED);
    final int[] results = UPDATE(p).execute();

    Assert.assertEquals(1, results[0]);
  }

  @Test
  public void testUpdateEntities() throws IOException, SQLException {
    final classicmodels.Customer c = new classicmodels.Customer();
    c.customerNumber.set(103);
    c.firstName.set("Jill");

    final classicmodels.Office o = new classicmodels.Office();
    o.officeCode.set(1l);
    o.territory.set("APAC");

    final int[] results = UPDATE(c, o).execute();

    Assert.assertEquals(1, results[0]);
    Assert.assertEquals(1, results[1]);
  }

  @Test
  public void testUpdateSetWhere() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final int[] results = UPDATE(p).SET(p.status, classicmodels.Purchase.Status.DISPUTED).WHERE(EQ(p.purchaseNumber, 10334l)).execute();

    Assert.assertEquals(1, results[0]);
  }

  @Test
  public void testUpdateSet() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final int[] results = UPDATE(p).SET(p.status, classicmodels.Purchase.Status.ON_HOLD).execute();

    Assert.assertTrue(results[0] > 300);
  }
}