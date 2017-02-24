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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.dbx.ddlx.runner.Derby;
import org.safris.dbx.ddlx.runner.MySQL;
import org.safris.dbx.ddlx.runner.PostgreSQL;
import org.safris.dbx.jsql.RowIterator;
import org.safris.dbx.jsql.type;
import org.safris.maven.plugin.xdb.xde.runner.VendorSchemaRunner;
import org.safris.xdb.entities.classicmodels;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(classicmodels.class)
@VendorSchemaRunner.Test(Derby.class)
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class})
public class BooleanValueExpressionTest {
  @Test
  public void test() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    try (final RowIterator<type.BOOLEAN> rows =
      SELECT(
        EQ(p.price, p.msrp),
        LT(p.price, p.msrp),
        AND(EQ(p.price, p.msrp),
          GT(p.price, p.msrp)),
        OR(EQ(p.price, p.msrp),
          GT(p.price, p.msrp))).
      FROM(p).
      WHERE(AND(LIKE(p.name, "%Ford%"), GT(p.quantityInStock, 100))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(false, rows.nextEntity().get());
      Assert.assertEquals(true, rows.nextEntity().get());
      Assert.assertEquals(false, rows.nextEntity().get());
      Assert.assertEquals(false, rows.nextEntity().get());
    }
  }
}