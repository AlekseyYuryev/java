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
import org.safris.dbb.ddlx.runner.Oracle;
import org.safris.dbb.ddlx.runner.PostgreSQL;
import org.safris.dbb.ddlx.runner.SQLite;
import org.safris.dbb.jsql.RowIterator;
import org.safris.dbb.jsql.classicmodels;
import org.safris.dbb.jsql.type.CHAR;
import org.safris.maven.plugin.dbb.jsql.runner.VendorSchemaRunner;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(classicmodels.class)
@VendorSchemaRunner.Test({Derby.class, SQLite.class})
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class, Oracle.class})
public class StringValueExpressionTest {
  @Test
  public void testConcat() throws IOException, SQLException {
    final classicmodels.Office o = new classicmodels.Office();
    try (final RowIterator<CHAR> rows =
      SELECT(
        // Char/Enum
        CONCAT(o.city, o.country),
        CONCAT("-", o.city, o.country),
        CONCAT(o.city, "-", o.country),
        CONCAT(o.city, o.country, "-"),
        CONCAT("-", o.city, o.country, "-"),
        CONCAT("-", o.city, "-", o.country, "-"),
        CONCAT(o.city, "-", o.country, "-"),
        CONCAT("-", o.city, "-", o.country),

        // Enum/Char
        CONCAT(o.country, o.city),
        CONCAT("-", o.country, o.city),
        CONCAT(o.country, "-", o.city),
        CONCAT(o.country, o.city, "-"),
        CONCAT("-", o.country, o.city, "-"),
        CONCAT("-", o.country, "-", o.city, "-"),
        CONCAT(o.country, "-", o.city, "-"),
        CONCAT("-", o.country, "-", o.city),

        // Char/Char
        CONCAT(o.city, o.city),
        CONCAT("-", o.city, o.city),
        CONCAT(o.city, "-", o.city),
        CONCAT(o.city, o.city, "-"),
        CONCAT("-", o.city, o.city, "-"),
        CONCAT("-", o.city, "-", o.city, "-"),
        CONCAT(o.city, "-", o.city, "-"),
        CONCAT("-", o.city, "-", o.city),

        // Enum/Enum
        CONCAT(o.country, o.country),
        CONCAT("-", o.country, o.country),
        CONCAT(o.country, "-", o.country),
        CONCAT(o.country, o.country, "-"),
        CONCAT("-", o.country, o.country, "-"),
        CONCAT("-", o.country, "-", o.country, "-"),
        CONCAT(o.country, "-", o.country, "-"),
        CONCAT("-", o.country, "-", o.country),

        // Char
        CONCAT(o.city, "-"),
        CONCAT("-", o.city),
        CONCAT("-", o.city, "-"),

        // Enum
        CONCAT(o.country, "-"),
        CONCAT("-", o.country),
        CONCAT("-", o.country, "-")).
      FROM(o).
      execute()) {
      Assert.assertTrue(rows.nextRow());

      // Char/Enum
      Assert.assertEquals("San FranciscoUS", rows.nextEntity().get());
      Assert.assertEquals("-San FranciscoUS", rows.nextEntity().get());
      Assert.assertEquals("San Francisco-US", rows.nextEntity().get());
      Assert.assertEquals("San FranciscoUS-", rows.nextEntity().get());
      Assert.assertEquals("-San FranciscoUS-", rows.nextEntity().get());
      Assert.assertEquals("-San Francisco-US-", rows.nextEntity().get());
      Assert.assertEquals("San Francisco-US-", rows.nextEntity().get());
      Assert.assertEquals("-San Francisco-US", rows.nextEntity().get());

      // Enum/Char
      Assert.assertEquals("USSan Francisco", rows.nextEntity().get());
      Assert.assertEquals("-USSan Francisco", rows.nextEntity().get());
      Assert.assertEquals("US-San Francisco", rows.nextEntity().get());
      Assert.assertEquals("USSan Francisco-", rows.nextEntity().get());
      Assert.assertEquals("-USSan Francisco-", rows.nextEntity().get());
      Assert.assertEquals("-US-San Francisco-", rows.nextEntity().get());
      Assert.assertEquals("US-San Francisco-", rows.nextEntity().get());
      Assert.assertEquals("-US-San Francisco", rows.nextEntity().get());

      // Char/Char
      Assert.assertEquals("San FranciscoSan Francisco", rows.nextEntity().get());
      Assert.assertEquals("-San FranciscoSan Francisco", rows.nextEntity().get());
      Assert.assertEquals("San Francisco-San Francisco", rows.nextEntity().get());
      Assert.assertEquals("San FranciscoSan Francisco-", rows.nextEntity().get());
      Assert.assertEquals("-San FranciscoSan Francisco-", rows.nextEntity().get());
      Assert.assertEquals("-San Francisco-San Francisco-", rows.nextEntity().get());
      Assert.assertEquals("San Francisco-San Francisco-", rows.nextEntity().get());
      Assert.assertEquals("-San Francisco-San Francisco", rows.nextEntity().get());

      // Enum/Enum
      Assert.assertEquals("USUS", rows.nextEntity().get());
      Assert.assertEquals("-USUS", rows.nextEntity().get());
      Assert.assertEquals("US-US", rows.nextEntity().get());
      Assert.assertEquals("USUS-", rows.nextEntity().get());
      Assert.assertEquals("-USUS-", rows.nextEntity().get());
      Assert.assertEquals("-US-US-", rows.nextEntity().get());
      Assert.assertEquals("US-US-", rows.nextEntity().get());
      Assert.assertEquals("-US-US", rows.nextEntity().get());

      // Char
      Assert.assertEquals("San Francisco-", rows.nextEntity().get());
      Assert.assertEquals("-San Francisco", rows.nextEntity().get());
      Assert.assertEquals("-San Francisco-", rows.nextEntity().get());

      // Enum
      Assert.assertEquals("US-", rows.nextEntity().get());
      Assert.assertEquals("-US", rows.nextEntity().get());
      Assert.assertEquals("-US-", rows.nextEntity().get());
    }
  }
}