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

package org.safris.maven.plugin.dbx.jsql;

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
import org.safris.dbx.jsql.classicmodels;
import org.safris.dbx.jsql.type;
import org.safris.maven.plugin.dbx.jsql.runner.VendorSchemaRunner;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(classicmodels.class)
@VendorSchemaRunner.Test(Derby.class)
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class})
public class NumericValueExpressionTest {
  @Test
  public void test() throws IOException, SQLException {
    final classicmodels.Product p = new classicmodels.Product();
    try (final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        ADD(COUNT(), 5),
        SUB(COUNT(), 5),
        MUL(COUNT(), 2),
        DIV(COUNT(), 2)).
      FROM(p).
      WHERE(OR(
        LT(ADD(p.msrp, p.price), 20),
        GT(SUB(p.msrp, p.quantityInStock), 10),
        EQ(MUL(p.msrp, ADD(p.msrp, p.price)), 40),
        EQ(DIV(p.msrp, SUB(p.msrp, p.quantityInStock)), 7))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Long.valueOf(rows.nextEntity().get().longValue() - 5), Long.valueOf(rows.nextEntity().get().longValue() + 5));
      Assert.assertEquals(Long.valueOf(rows.nextEntity().get().longValue() / 2), Long.valueOf(rows.nextEntity().get().longValue() * 2));
    }
  }
}