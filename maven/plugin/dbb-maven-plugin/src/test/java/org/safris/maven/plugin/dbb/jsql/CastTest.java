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
import org.safris.dbb.jsql.DML.NOT;
import org.safris.dbb.jsql.RowIterator;
import org.safris.dbb.jsql.type;
import org.safris.dbb.jsql.types;
import org.safris.maven.plugin.dbb.jsql.runner.VendorSchemaRunner;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema(types.class)
@VendorSchemaRunner.Test({Derby.class, SQLite.class})
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class, Oracle.class})
public class CastTest {
  @Test
  public void testBooleanToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.booleanType).AS.CHAR(5)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBooleanToClob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.CLOB> rows =
      SELECT(
        CAST(t.booleanType).AS.CLOB(5)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testFloatToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.floatType).AS.DOUBLE()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testFloatToFloatUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.FLOAT.UNSIGNED> rows =
      SELECT(
        CAST(t.floatType).AS.UNSIGNED()).
      FROM(t).
      WHERE(GTE(t.floatType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testFloatToDoubleUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DOUBLE.UNSIGNED> rows =
      SELECT(
        CAST(t.floatType).AS.DOUBLE.UNSIGNED()).
      FROM(t).
      WHERE(GTE(t.floatType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testFloatToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.floatType).AS.DECIMAL(31, 10)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testFloatToDecimalUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL.UNSIGNED> rows =
      SELECT(
        CAST(t.floatType).AS.DECIMAL.UNSIGNED(31, 10)).
      FROM(t).
      WHERE(GTE(t.floatType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testFloatToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.floatType).AS.TINYINT(3)).
      FROM(t).
      WHERE(AND(GTE(t.floatType, -128), LTE(t.floatType, 127))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testFloatToSmallIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT.UNSIGNED> rows =
      SELECT(
        CAST(t.floatType).AS.TINYINT.UNSIGNED(3)).
      FROM(t).
      WHERE(AND(GTE(t.floatType, 0), LTE(t.floatType, 255))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testFloatToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.floatType).AS.SMALLINT(5)).
      FROM(t).
      WHERE(AND(GTE(t.floatType, -128), LTE(t.floatType, 127))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testFloatToMediumIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT.UNSIGNED> rows =
      SELECT(
        CAST(t.floatType).AS.SMALLINT.UNSIGNED(5)).
      FROM(t).
      WHERE(GTE(t.floatType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testFloatToInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.floatType).AS.INT(10)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testFloatToIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT.UNSIGNED> rows =
      SELECT(
        CAST(t.floatType).AS.INT.UNSIGNED(10)).
      FROM(t).
      WHERE(GTE(t.floatType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testFloatToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.floatType).AS.BIGINT(19)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testFloatToBigIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT.UNSIGNED> rows =
      SELECT(
        CAST(t.floatType).AS.BIGINT.UNSIGNED(19)).
      FROM(t).
      WHERE(GTE(t.floatType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDoubleToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.doubleType).AS.FLOAT()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDoubleToFloatUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.FLOAT.UNSIGNED> rows =
      SELECT(
        CAST(t.doubleType).AS.FLOAT.UNSIGNED()).
      FROM(t).
      WHERE(GTE(t.doubleType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDoubleToDoubleUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DOUBLE.UNSIGNED> rows =
      SELECT(
        CAST(t.doubleType).AS.UNSIGNED()).
      FROM(t).
      WHERE(GTE(t.doubleType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDoubleToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.doubleType).AS.DECIMAL(31, 10)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDoubleToDecimalUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL.UNSIGNED> rows =
      SELECT(
        CAST(t.doubleType).AS.DECIMAL.UNSIGNED(31, 10)).
      FROM(t).
      WHERE(GTE(t.doubleType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDoubleToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.doubleType).AS.TINYINT(3)).
      FROM(t).
      WHERE(AND(GTE(t.doubleType, -128), LTE(t.doubleType, 127))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDoubleToSmallIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT.UNSIGNED> rows =
      SELECT(
        CAST(t.doubleType).AS.TINYINT.UNSIGNED(3)).
      FROM(t).
      WHERE(AND(GTE(t.doubleType, 0), LTE(t.doubleType, 255))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDoubleToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.doubleType).AS.SMALLINT(5)).
      FROM(t).
      WHERE(AND(GTE(t.doubleType, -32768), LTE(t.doubleType, 32767))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDoubleToMediumIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT.UNSIGNED> rows =
      SELECT(
        CAST(t.doubleType).AS.SMALLINT.UNSIGNED(5)).
      FROM(t).
      WHERE(AND(GTE(t.doubleType, 0), LTE(t.doubleType, 99999))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDoubleToInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.doubleType).AS.INT(10)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDoubleToIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT.UNSIGNED> rows =
      SELECT(
        CAST(t.doubleType).AS.INT.UNSIGNED(10)).
      FROM(t).
      WHERE(GTE(t.doubleType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDoubleToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.doubleType).AS.BIGINT(19)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDoubleToBigIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT.UNSIGNED> rows =
      SELECT(
        CAST(t.doubleType).AS.BIGINT.UNSIGNED(19)).
      FROM(t).
      WHERE(GTE(t.doubleType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.decimalType).AS.FLOAT()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToFloatUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.FLOAT.UNSIGNED> rows =
      SELECT(
        CAST(t.decimalType).AS.FLOAT.UNSIGNED()).
      FROM(t).
      WHERE(GTE(t.decimalType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.decimalType).AS.DOUBLE()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToDoubleUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DOUBLE.UNSIGNED> rows =
      SELECT(
        CAST(t.decimalType).AS.DOUBLE.UNSIGNED()).
      FROM(t).
      WHERE(GTE(t.decimalType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.decimalType).AS.DECIMAL(31, 10)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToDecimalUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL.UNSIGNED> rows =
      SELECT(
        CAST(t.decimalType).AS.UNSIGNED(31, 10)).
      FROM(t).
      WHERE(GTE(t.decimalType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.decimalType).AS.TINYINT(3)).
      FROM(t).
      WHERE(AND(GTE(t.decimalType, -128), LTE(t.decimalType, 127))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToSmallIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT.UNSIGNED> rows =
      SELECT(
        CAST(t.decimalType).AS.TINYINT.UNSIGNED(3)).
      FROM(t).
      WHERE(AND(GTE(t.decimalType, 0), LTE(t.decimalType, 255))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.decimalType).AS.SMALLINT(5)).
      FROM(t).
      WHERE(AND(GTE(t.decimalType, -128), LTE(t.decimalType, 127))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToMediumIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT.UNSIGNED> rows =
      SELECT(
        CAST(t.decimalType).AS.SMALLINT.UNSIGNED(5)).
      FROM(t).
      WHERE(AND(GTE(t.decimalType, 0), LTE(t.decimalType, 99999))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.decimalType).AS.INT(10)).
      FROM(t).
      WHERE(AND(GTE(t.decimalType, -2147483648), LTE(t.decimalType, 2147483647))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT.UNSIGNED> rows =
      SELECT(
        CAST(t.decimalType).AS.INT.UNSIGNED(10)).
      FROM(t).
      WHERE(AND(GTE(t.decimalType, 0), LTE(t.decimalType, 16777215))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.decimalType).AS.BIGINT(19)).
      FROM(t).
      WHERE(AND(GTE(t.decimalType, 0), LTE(t.decimalType, 2147483647))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToBigIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT.UNSIGNED> rows =
      SELECT(
        CAST(t.decimalType).AS.BIGINT.UNSIGNED(19)).
      FROM(t).
      WHERE(AND(GTE(t.decimalType, 0), LTE(t.decimalType, 2147483647))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDecimalToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.decimalType).AS.CHAR(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.tinyintType).AS.FLOAT()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToFloatUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.FLOAT.UNSIGNED> rows =
      SELECT(
        CAST(t.tinyintType).AS.FLOAT.UNSIGNED()).
      FROM(t).
      WHERE(GTE(t.tinyintType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.tinyintType).AS.DOUBLE()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToDoubleUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DOUBLE.UNSIGNED> rows =
      SELECT(
        CAST(t.tinyintType).AS.DOUBLE.UNSIGNED()).
      FROM(t).
      WHERE(GTE(t.tinyintType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.tinyintType).AS.DECIMAL(31, 10)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToDecimalUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL.UNSIGNED> rows =
      SELECT(
        CAST(t.tinyintType).AS.DECIMAL.UNSIGNED(31, 10)).
      FROM(t).
      WHERE(GTE(t.tinyintType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.tinyintType).AS.TINYINT(3)).
      FROM(t).
      WHERE(AND(GTE(t.tinyintType, -128), LTE(t.tinyintType, 127))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToSmallIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT.UNSIGNED> rows =
      SELECT(
        CAST(t.tinyintType).AS.UNSIGNED(3)).
      FROM(t).
      WHERE(AND(GTE(t.tinyintType, 0), LTE(t.tinyintType, 255))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.tinyintType).AS.SMALLINT(5)).
      FROM(t).
      WHERE(AND(GTE(t.tinyintType, -128), LTE(t.tinyintType, 127))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToMediumIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT.UNSIGNED> rows =
      SELECT(
        CAST(t.tinyintType).AS.SMALLINT.UNSIGNED(5)).
      FROM(t).
      WHERE(AND(GTE(t.tinyintType, 0), LTE(t.tinyintType, 255))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.tinyintType).AS.INT(10)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT.UNSIGNED> rows =
      SELECT(
        CAST(t.tinyintType).AS.INT.UNSIGNED(10)).
      FROM(t).
      WHERE(GTE(t.tinyintType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.tinyintType).AS.BIGINT(19)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToBigIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT.UNSIGNED> rows =
      SELECT(
        CAST(t.tinyintType).AS.BIGINT.UNSIGNED(19)).
      FROM(t).
      WHERE(GTE(t.tinyintType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testSmallIntToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.tinyintType).AS.CHAR(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.smallintType).AS.FLOAT()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToFloatUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.FLOAT.UNSIGNED> rows =
      SELECT(
        CAST(t.smallintType).AS.FLOAT.UNSIGNED()).
      FROM(t).
      WHERE(GTE(t.tinyintType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.smallintType).AS.DOUBLE()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToDoubleUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DOUBLE.UNSIGNED> rows =
      SELECT(
        CAST(t.smallintType).AS.DOUBLE.UNSIGNED()).
      FROM(t).
      WHERE(GTE(t.tinyintType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.smallintType).AS.DECIMAL(31, 10)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToDecimalUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL.UNSIGNED> rows =
      SELECT(
        CAST(t.smallintType).AS.DECIMAL.UNSIGNED(31, 10)).
      FROM(t).
      WHERE(GTE(t.tinyintType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.smallintType).AS.TINYINT(3)).
      FROM(t).
      WHERE(AND(GTE(t.smallintType, -128), LTE(t.smallintType, 127))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToSmallIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT.UNSIGNED> rows =
      SELECT(
        CAST(t.smallintType).AS.TINYINT.UNSIGNED(3)).
      FROM(t).
      WHERE(AND(GTE(t.smallintType, 0), LTE(t.smallintType, 255))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.smallintType).AS.SMALLINT(5)).
      FROM(t).
      WHERE(AND(GTE(t.smallintType, -32768), LTE(t.smallintType, 32767))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToMediumIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT.UNSIGNED> rows =
      SELECT(
        CAST(t.smallintType).AS.UNSIGNED(5)).
      FROM(t).
      WHERE(AND(GTE(t.smallintType, 0), LTE(t.smallintType, 32767))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.smallintType).AS.INT(10)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT.UNSIGNED> rows =
      SELECT(
        CAST(t.smallintType).AS.INT.UNSIGNED(10)).
      FROM(t).
      WHERE(GTE(t.smallintType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.smallintType).AS.BIGINT(19)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToBigIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT.UNSIGNED> rows =
      SELECT(
        CAST(t.smallintType).AS.BIGINT.UNSIGNED(19)).
      FROM(t).
      WHERE(GTE(t.smallintType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testMediumIntToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.smallintType).AS.CHAR(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.intType).AS.FLOAT()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToFloatUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.FLOAT.UNSIGNED> rows =
      SELECT(
        CAST(t.intType).AS.FLOAT.UNSIGNED()).
      FROM(t).
      WHERE(GTE(t.intType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.intType).AS.DOUBLE()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToDoubleUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DOUBLE.UNSIGNED> rows =
      SELECT(
        CAST(t.intType).AS.DOUBLE.UNSIGNED()).
      FROM(t).
      WHERE(GTE(t.intType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.intType).AS.DECIMAL(31, 10)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToDecimalUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL.UNSIGNED> rows =
      SELECT(
        CAST(t.intType).AS.DECIMAL.UNSIGNED(31, 10)).
      FROM(t).
      WHERE(GTE(t.intType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.intType).AS.TINYINT(3)).
      FROM(t).
      WHERE(AND(GTE(t.intType, -128), LTE(t.intType, 127))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToSmallIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT.UNSIGNED> rows =
      SELECT(
        CAST(t.intType).AS.TINYINT.UNSIGNED(3)).
      FROM(t).
      WHERE(AND(GTE(t.intType, 0), LTE(t.intType, 255))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.intType).AS.SMALLINT(5)).
      FROM(t).
      WHERE(AND(GTE(t.intType, -32768), LTE(t.intType, 32767))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToMediumIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT.UNSIGNED> rows =
      SELECT(
        CAST(t.intType).AS.SMALLINT.UNSIGNED(5)).
      FROM(t).
      WHERE(AND(GTE(t.intType, 0), LTE(t.intType, 99999))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.intType).AS.INT(10)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT.UNSIGNED> rows =
      SELECT(
        CAST(t.intType).AS.UNSIGNED(10)).
      FROM(t).
      WHERE(GTE(t.intType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.intType).AS.BIGINT(19)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToBigIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT.UNSIGNED> rows =
      SELECT(
        CAST(t.intType).AS.BIGINT.UNSIGNED(19)).
      FROM(t).
      WHERE(GTE(t.intType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testIntToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.intType).AS.CHAR(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.bigintType).AS.FLOAT()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToFloatUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.FLOAT.UNSIGNED> rows =
      SELECT(
        CAST(t.bigintType).AS.FLOAT.UNSIGNED()).
      FROM(t).
      WHERE(GTE(t.bigintType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.bigintType).AS.DOUBLE()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToDoubleUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DOUBLE.UNSIGNED> rows =
      SELECT(
        CAST(t.bigintType).AS.DOUBLE.UNSIGNED()).
      FROM(t).
      WHERE(GTE(t.bigintType, 0)).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.bigintType).AS.DECIMAL(20, 5)).
      FROM(t).
      WHERE(AND(LT(t.bigintType, 2147483647), GT(t.bigintType, -2147483648))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToDecimalUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL.UNSIGNED> rows =
      SELECT(
        CAST(t.bigintType).AS.DECIMAL.UNSIGNED(20, 5)).
      FROM(t).
      WHERE(AND(LT(t.bigintType, 2147483647), GTE(t.bigintType, 0))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.bigintType).AS.TINYINT(3)).
      FROM(t).
      WHERE(AND(GTE(t.bigintType, -128), LTE(t.bigintType, 127))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToSmallIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT.UNSIGNED> rows =
      SELECT(
        CAST(t.bigintType).AS.TINYINT.UNSIGNED(3)).
      FROM(t).
      WHERE(AND(GTE(t.bigintType, 0), LTE(t.bigintType, 255))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.bigintType).AS.SMALLINT(5)).
      FROM(t).
      WHERE(AND(GTE(t.bigintType, -32768), LTE(t.bigintType, 32767))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToMediumIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT.UNSIGNED> rows =
      SELECT(
        CAST(t.bigintType).AS.SMALLINT.UNSIGNED(5)).
      FROM(t).
      WHERE(AND(GTE(t.bigintType, 0), LTE(t.bigintType, 99999))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.bigintType).AS.INT(10)).
      FROM(t).
      WHERE(AND(LT(t.bigintType, 2147483647), GT(t.bigintType, -2147483648))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT.UNSIGNED> rows =
      SELECT(
        CAST(t.bigintType).AS.INT.UNSIGNED(10)).
      FROM(t).
      WHERE(AND(LT(t.bigintType, 2147483647), GTE(t.bigintType, 0))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.bigintType).AS.BIGINT(19)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToBigIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT.UNSIGNED> rows =
      SELECT(
        CAST(t.bigintType).AS.UNSIGNED(19)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBigIntToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.bigintType).AS.CHAR(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.charType).AS.DECIMAL(31, 10)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToDecimalUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DECIMAL.UNSIGNED> rows =
      SELECT(
        CAST(t.charType).AS.DECIMAL.UNSIGNED(31, 10)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "-%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.charType).AS.TINYINT(3)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "%.%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToSmallIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TINYINT.UNSIGNED> rows =
      SELECT(
        CAST(t.charType).AS.TINYINT.UNSIGNED(3)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "%.%"), NOT.LIKE(t.charType, "-%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.charType).AS.SMALLINT(5)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "%.%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToMediumIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.SMALLINT.UNSIGNED> rows =
      SELECT(
        CAST(t.charType).AS.SMALLINT.UNSIGNED(5)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "%.%"), NOT.LIKE(t.charType, "-%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.charType).AS.INT(10)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "%.%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.INT.UNSIGNED> rows =
      SELECT(
        CAST(t.charType).AS.INT.UNSIGNED(10)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "%.%"), NOT.LIKE(t.charType, "-%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.charType).AS.BIGINT(19)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "%.%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToBigIntUnsigned() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BIGINT.UNSIGNED> rows =
      SELECT(
        CAST(t.charType).AS.BIGINT.UNSIGNED(19)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "%.%"), NOT.LIKE(t.charType, "-%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.charType).AS.CHAR(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToDate() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DATE> rows =
      SELECT(
        CAST(t.charType).AS.DATE()).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%-%-%"), NOT.LIKE(t.charType, "%-%-% %"))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TIME> rows =
      SELECT(
        CAST(t.charType).AS.TIME()).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%:%:%"), NOT.LIKE(t.charType, "% %:%:%"))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToDateTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        CAST(t.charType).AS.DATETIME()).
      FROM(t).
      WHERE(LIKE(t.charType, "%-%-% %:%:%")).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testCharToClob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.CLOB> rows =
      SELECT(
        CAST(t.charType).AS.CLOB(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDateToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.dateType).AS.CHAR(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testTimeToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.timeType).AS.CHAR(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testTimeToTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TIME> rows =
      SELECT(
        CAST(t.timeType).AS.TIME()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDateTimeToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.datetimeType).AS.CHAR(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDateTimeToDate() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DATE> rows =
      SELECT(
        CAST(t.datetimeType).AS.DATE()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDateTimeToTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.TIME> rows =
      SELECT(
        CAST(t.datetimeType).AS.TIME()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testDateTimeToDateTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        CAST(t.datetimeType).AS.DATETIME()).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testClobToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.clobType).AS.CHAR(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testClobToClob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.CLOB> rows =
      SELECT(
        CAST(t.clobType).AS.CLOB(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBlobToBlob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BLOB> rows =
      SELECT(
        CAST(t.blobType).AS.BLOB(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBinaryToBlob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BLOB> rows =
      SELECT(
        CAST(t.binaryType).AS.BLOB(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }

  @Test
  public void testBinaryToBinary() throws IOException, SQLException {
    final types.Type t = new types.Type();
    try (final RowIterator<type.BINARY> rows =
      SELECT(
        CAST(t.binaryType).AS.BINARY(254)).
      FROM(t).
      execute()) {
      Assert.assertTrue(rows.nextRow());
    }
  }
}