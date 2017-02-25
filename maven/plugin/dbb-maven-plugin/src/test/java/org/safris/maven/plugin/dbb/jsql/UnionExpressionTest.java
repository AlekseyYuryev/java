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

package org.safris.maven.plugin.dbb.jsql;

import static org.safris.dbb.jsql.DML.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.dbb.ddlx.runner.Derby;
import org.safris.dbb.ddlx.runner.MySQL;
import org.safris.dbb.ddlx.runner.PostgreSQL;
import org.safris.dbb.jsql.Entity;
import org.safris.dbb.jsql.RowIterator;
import org.safris.dbb.jsql.classicmodels;
import org.safris.maven.plugin.dbb.jsql.runner.VendorSchemaRunner;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(classicmodels.class)
@VendorSchemaRunner.Test(Derby.class)
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class})
public class UnionExpressionTest {
  @Test
  public void testUnion() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    final classicmodels.Customer c = new classicmodels.Customer();
    try (final RowIterator<? extends Entity> rows =
      SELECT(p, c).
      FROM(p).
      LEFT.JOIN(c).ON(EQ(p.customerNumber, c.customerNumber)).
      UNION(SELECT(p, c).
        FROM(p).
        RIGHT.JOIN(c).ON(EQ(p.customerNumber, c.customerNumber))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }
}