/* Copyright (c) 2017 lib4j
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

package org.libx4j.maven.plugin.rdb.jsql;

import static org.libx4j.rdb.jsql.DML.*;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.lib4j.test.MixedTest;
import org.libx4j.maven.plugin.rdb.jsql.runner.VendorSchemaRunner;
import org.libx4j.rdb.ddlx.runner.Derby;
import org.libx4j.rdb.ddlx.runner.MySQL;
import org.libx4j.rdb.ddlx.runner.Oracle;
import org.libx4j.rdb.ddlx.runner.PostgreSQL;
import org.libx4j.rdb.ddlx.runner.SQLite;
import org.libx4j.rdb.jsql.DML.SUM;
import org.libx4j.rdb.jsql.RowIterator;
import org.libx4j.rdb.jsql.classicmodels;
import org.libx4j.rdb.jsql.type;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(classicmodels.class)
@VendorSchemaRunner.Test({Derby.class, SQLite.class})
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class, Oracle.class})
@Category(MixedTest.class)
public class SetFunctionTest {
  @Test
  public void testSetFunctions() throws IOException, SQLException {
    final classicmodels.Customer c = new classicmodels.Customer();
    try (final RowIterator<? extends type.DataType<?>> rows =
      SELECT(
        AVG(c.phone),
        MAX(c.city),
        MIN(c.country),
        SUM.DISTINCT(c.salesEmployeeNumber)).
      FROM(c).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(BigInteger.valueOf(24367857008l), rows.nextEntity().get());
      Assert.assertEquals("White Plains", rows.nextEntity().get());
      Assert.assertEquals(classicmodels.Address.Country.AU, rows.nextEntity().get());
      Assert.assertEquals(Long.valueOf(21003), rows.nextEntity().get());
    }
  }
}