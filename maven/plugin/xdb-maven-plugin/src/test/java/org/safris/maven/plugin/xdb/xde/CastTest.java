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

import static org.safris.xdb.entities.DML.AND;
import static org.safris.xdb.entities.DML.CAST;
import static org.safris.xdb.entities.DML.GT;
import static org.safris.xdb.entities.DML.LIKE;
import static org.safris.xdb.entities.DML.LT;
import static org.safris.xdb.entities.DML.SELECT;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.commons.test.LoggableTest;
import org.safris.xdb.entities.DML.NOT;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.type;
import org.safris.xdb.entities.types;

@RunWith(TypesTestRunner.class)
public class CastTest extends LoggableTest {
  @Test
  public void testBooleanToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.booleanType).AS.CHAR(5)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBooleanToClob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CLOB> rows =
      SELECT(
        CAST(t.booleanType).AS.CLOB(5)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testFloatToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.floatType).AS.DOUBLE(t.floatType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testFloatToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.floatType).AS.DECIMAL(18, 5, t.floatType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testFloatToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.floatType).AS.SMALLINT(3, t.floatType.unsigned())).
      FROM(t).
      WHERE(AND(LT(t.floatType, 255), GT(t.floatType, -256))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testFloatToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.floatType).AS.MEDIUMINT(5, t.floatType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testFloatToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.floatType).AS.INTEGER(10, t.floatType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testFloatToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.floatType).AS.BIGINT(16, t.floatType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDoubleToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.doubleType).AS.FLOAT(t.doubleType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDoubleToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.doubleType).AS.DECIMAL(18, 5, t.doubleType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDoubleToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.doubleType).AS.SMALLINT(3, t.doubleType.unsigned())).
      FROM(t).
      WHERE(AND(LT(t.doubleType, 255), GT(t.doubleType, -256))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDoubleToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.doubleType).AS.MEDIUMINT(5, t.doubleType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDoubleToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.doubleType).AS.INTEGER(10, t.doubleType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDoubleToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.doubleType).AS.BIGINT(16, t.doubleType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.decimalType).AS.FLOAT(t.decimalType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.decimalType).AS.DOUBLE(t.decimalType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.decimalType).AS.DECIMAL(18, 5, t.decimalType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.decimalType).AS.SMALLINT(3, t.decimalType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.decimalType).AS.MEDIUMINT(5, t.decimalType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.decimalType).AS.INTEGER(10, t.decimalType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.decimalType).AS.BIGINT(16, t.decimalType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.decimalType).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testSmallIntToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.smallintType).AS.FLOAT(t.smallintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testSmallIntToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.smallintType).AS.DOUBLE(t.smallintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testSmallIntToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.smallintType).AS.DECIMAL(18, 5, t.smallintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testSmallIntToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.smallintType).AS.SMALLINT(3, t.smallintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testSmallIntToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.smallintType).AS.MEDIUMINT(5, t.smallintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testSmallIntToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.smallintType).AS.INTEGER(10, t.smallintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testSmallIntToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.smallintType).AS.BIGINT(16, t.smallintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testSmallIntToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.smallintType).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.mediumintType).AS.FLOAT(t.mediumintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.mediumintType).AS.DOUBLE(t.mediumintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.mediumintType).AS.DECIMAL(18, 5, t.mediumintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.mediumintType).AS.SMALLINT(3, t.mediumintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.mediumintType).AS.MEDIUMINT(5, t.mediumintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.mediumintType).AS.INTEGER(10, t.mediumintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.mediumintType).AS.BIGINT(16, t.mediumintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.mediumintType).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.intType).AS.FLOAT(t.intType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.intType).AS.DOUBLE(t.intType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.intType).AS.DECIMAL(18, 5, t.intType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.intType).AS.SMALLINT(3, t.intType.unsigned())).
      FROM(t).
      WHERE(AND(LT(t.intType, 255), GT(t.intType, -256))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.intType).AS.MEDIUMINT(5, t.intType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.intType).AS.INTEGER(10, t.intType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.intType).AS.BIGINT(16, t.intType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.intType).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.bigintType).AS.FLOAT(t.bigintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.bigintType).AS.DOUBLE(t.bigintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.bigintType).AS.DECIMAL(20, 5, t.bigintType.unsigned())).
      FROM(t).
      WHERE(AND(LT(t.bigintType, 2147483647), GT(t.bigintType, -2147483648))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.bigintType).AS.SMALLINT(3, t.bigintType.unsigned())).
      FROM(t).
      WHERE(AND(LT(t.bigintType, 255), GT(t.bigintType, -256))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.bigintType).AS.MEDIUMINT(5, t.bigintType.unsigned())).
      FROM(t).
      WHERE(AND(LT(t.bigintType, 2147483647), GT(t.bigintType, -2147483648))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.bigintType).AS.INTEGER(10, t.bigintType.unsigned())).
      FROM(t).
      WHERE(AND(LT(t.bigintType, 2147483647), GT(t.bigintType, -2147483648))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.bigintType).AS.BIGINT(16, t.bigintType.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.bigintType).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.charType).AS.DECIMAL(18, 5, false)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.SMALLINT> rows =
      SELECT(
        CAST(t.charType).AS.SMALLINT(3, false)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "%.%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.charType).AS.MEDIUMINT(5, false)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "%.%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.charType).AS.INTEGER(10, false)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "%.%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.charType).AS.BIGINT(16, false)).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%1%"), NOT.LIKE(t.charType, "%.%"), NOT.LIKE(t.charType, "%-%"), NOT.LIKE(t.charType, "%:%"))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.charType).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToDate() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DATE> rows =
      SELECT(
        CAST(t.charType).AS.DATE()).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%-%-%"), NOT.LIKE(t.charType, "%-%-% "))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TIME> rows =
      SELECT(
        CAST(t.charType).AS.TIME()).
      FROM(t).
      WHERE(AND(LIKE(t.charType, "%:%:%"), NOT.LIKE(t.charType, " %:%:%"))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToDateTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DATETIME> rows =
      SELECT(
        CAST(t.charType).AS.DATETIME()).
      FROM(t).
      WHERE(LIKE(t.charType, "%-%-% %:%:%")).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToClob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CLOB> rows =
      SELECT(
        CAST(t.charType).AS.CLOB(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDateToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.dateType).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testTimeToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.timeType).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testTimeToTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TIME> rows =
      SELECT(
        CAST(t.timeType).AS.TIME()).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDateTimeToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.datetimeType).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDateTimeToDate() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DATE> rows =
      SELECT(
        CAST(t.datetimeType).AS.DATE()).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDateTimeToTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TIME> rows =
      SELECT(
        CAST(t.datetimeType).AS.TIME()).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDateTimeToDateTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DATETIME> rows =
      SELECT(
        CAST(t.datetimeType).AS.DATETIME()).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testClobToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.clobType).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testClobToClob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CLOB> rows =
      SELECT(
        CAST(t.clobType).AS.CLOB(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBlobToBlob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BLOB> rows =
      SELECT(
        CAST(t.blobType).AS.BLOB(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBinaryToBlob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BLOB> rows =
      SELECT(
        CAST(t.binaryType).AS.BLOB(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBinaryToBinary() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BINARY> rows =
      SELECT(
        CAST(t.binaryType).AS.BINARY(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }
}