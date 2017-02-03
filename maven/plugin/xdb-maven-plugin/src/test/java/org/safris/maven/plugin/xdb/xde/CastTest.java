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
        CAST(t.typeBoolean).AS.CHAR(5)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBooleanToClob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CLOB> rows =
      SELECT(
        CAST(t.typeBoolean).AS.CLOB(5)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testFloatToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.typeFloat).AS.DOUBLE(t.typeFloat.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testFloatToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.typeFloat).AS.DECIMAL(18, 5, t.typeFloat.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testFloatToTinyInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.typeFloat).AS.TINYINT(3, t.typeFloat.unsigned())).
      FROM(t).
      WHERE(AND(LT(t.typeFloat, 255), GT(t.typeFloat, -256))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testFloatToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.typeFloat).AS.MEDIUMINT(5, t.typeFloat.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testFloatToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.typeFloat).AS.INTEGER(10, t.typeFloat.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testFloatToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.typeFloat).AS.BIGINT(16, t.typeFloat.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDoubleToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.typeDouble).AS.FLOAT(t.typeDouble.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDoubleToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.typeDouble).AS.DECIMAL(18, 5, t.typeDouble.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDoubleToTinyInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.typeDouble).AS.TINYINT(3, t.typeDouble.unsigned())).
      FROM(t).
      WHERE(AND(LT(t.typeDouble, 255), GT(t.typeDouble, -256))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDoubleToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.typeDouble).AS.MEDIUMINT(5, t.typeDouble.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDoubleToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.typeDouble).AS.INTEGER(10, t.typeDouble.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDoubleToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.typeDouble).AS.BIGINT(16, t.typeDouble.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.typeDecimal).AS.FLOAT(t.typeDecimal.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.typeDecimal).AS.DOUBLE(t.typeDecimal.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.typeDecimal).AS.DECIMAL(18, 5, t.typeDecimal.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToTinyInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.typeDecimal).AS.TINYINT(3, t.typeDecimal.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.typeDecimal).AS.MEDIUMINT(5, t.typeDecimal.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.typeDecimal).AS.INTEGER(10, t.typeDecimal.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.typeDecimal).AS.BIGINT(16, t.typeDecimal.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDecimalToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.typeDecimal).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testTinyIntToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.typeSmallint).AS.FLOAT(t.typeSmallint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testTinyIntToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.typeSmallint).AS.DOUBLE(t.typeSmallint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testTinyIntToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.typeSmallint).AS.DECIMAL(18, 5, t.typeSmallint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testTinyIntToTinyInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.typeSmallint).AS.TINYINT(3, t.typeSmallint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testTinyIntToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.typeSmallint).AS.MEDIUMINT(5, t.typeSmallint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testTinyIntToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.typeSmallint).AS.INTEGER(10, t.typeSmallint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testTinyIntToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.typeSmallint).AS.BIGINT(16, t.typeSmallint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testTinyIntToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.typeSmallint).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.typeMediumint).AS.FLOAT(t.typeMediumint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.typeMediumint).AS.DOUBLE(t.typeMediumint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.typeMediumint).AS.DECIMAL(18, 5, t.typeMediumint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToTinyInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.typeMediumint).AS.TINYINT(3, t.typeMediumint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.typeMediumint).AS.MEDIUMINT(5, t.typeMediumint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.typeMediumint).AS.INTEGER(10, t.typeMediumint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.typeMediumint).AS.BIGINT(16, t.typeMediumint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testMediumIntToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.typeMediumint).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.typeLong).AS.FLOAT(t.typeLong.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.typeLong).AS.DOUBLE(t.typeLong.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.typeLong).AS.DECIMAL(18, 5, t.typeLong.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToTinyInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.typeLong).AS.TINYINT(3, t.typeLong.unsigned())).
      FROM(t).
      WHERE(AND(LT(t.typeLong, 255), GT(t.typeLong, -256))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.typeLong).AS.MEDIUMINT(5, t.typeLong.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.typeLong).AS.INTEGER(10, t.typeLong.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.typeLong).AS.BIGINT(16, t.typeLong.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testLongToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.typeLong).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.FLOAT> rows =
      SELECT(
        CAST(t.typeBigint).AS.FLOAT(t.typeBigint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DOUBLE> rows =
      SELECT(
        CAST(t.typeBigint).AS.DOUBLE(t.typeBigint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.typeBigint).AS.DECIMAL(20, 5, t.typeBigint.unsigned())).
      FROM(t).
      WHERE(AND(LT(t.typeBigint, 2147483647), GT(t.typeBigint, -2147483648))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToTinyInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.typeBigint).AS.TINYINT(3, t.typeBigint.unsigned())).
      FROM(t).
      WHERE(AND(LT(t.typeBigint, 255), GT(t.typeBigint, -256))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.typeBigint).AS.MEDIUMINT(5, t.typeBigint.unsigned())).
      FROM(t).
      WHERE(AND(LT(t.typeBigint, 2147483647), GT(t.typeBigint, -2147483648))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.typeBigint).AS.INTEGER(10, t.typeBigint.unsigned())).
      FROM(t).
      WHERE(AND(LT(t.typeBigint, 2147483647), GT(t.typeBigint, -2147483648))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.typeBigint).AS.BIGINT(16, t.typeBigint.unsigned())).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBigIntToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.typeBigint).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DECIMAL> rows =
      SELECT(
        CAST(t.typeChar).AS.DECIMAL(18, 5, false)).
      FROM(t).
      WHERE(AND(LIKE(t.typeChar, "%1%"), NOT.LIKE(t.typeChar, "%-%"), NOT.LIKE(t.typeChar, "%:%"))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToTinyInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TINYINT> rows =
      SELECT(
        CAST(t.typeChar).AS.TINYINT(3, false)).
      FROM(t).
      WHERE(AND(LIKE(t.typeChar, "%1%"), NOT.LIKE(t.typeChar, "%.%"), NOT.LIKE(t.typeChar, "%-%"), NOT.LIKE(t.typeChar, "%:%"))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.MEDIUMINT> rows =
      SELECT(
        CAST(t.typeChar).AS.MEDIUMINT(5, false)).
      FROM(t).
      WHERE(AND(LIKE(t.typeChar, "%1%"), NOT.LIKE(t.typeChar, "%.%"), NOT.LIKE(t.typeChar, "%-%"), NOT.LIKE(t.typeChar, "%:%"))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToLong() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.INT> rows =
      SELECT(
        CAST(t.typeChar).AS.INTEGER(10, false)).
      FROM(t).
      WHERE(AND(LIKE(t.typeChar, "%1%"), NOT.LIKE(t.typeChar, "%.%"), NOT.LIKE(t.typeChar, "%-%"), NOT.LIKE(t.typeChar, "%:%"))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BIGINT> rows =
      SELECT(
        CAST(t.typeChar).AS.BIGINT(16, false)).
      FROM(t).
      WHERE(AND(LIKE(t.typeChar, "%1%"), NOT.LIKE(t.typeChar, "%.%"), NOT.LIKE(t.typeChar, "%-%"), NOT.LIKE(t.typeChar, "%:%"))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.typeChar).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToDate() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DATE> rows =
      SELECT(
        CAST(t.typeChar).AS.DATE()).
      FROM(t).
      WHERE(AND(LIKE(t.typeChar, "%-%-%"), NOT.LIKE(t.typeChar, "%-%-% "))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TIME> rows =
      SELECT(
        CAST(t.typeChar).AS.TIME()).
      FROM(t).
      WHERE(AND(LIKE(t.typeChar, "%:%:%"), NOT.LIKE(t.typeChar, " %:%:%"))).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToDateTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DATETIME> rows =
      SELECT(
        CAST(t.typeChar).AS.DATETIME()).
      FROM(t).
      WHERE(LIKE(t.typeChar, "%-%-% %:%:%")).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testCharToClob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CLOB> rows =
      SELECT(
        CAST(t.typeChar).AS.CLOB(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDateToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.typeDate).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testTimeToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.typeTime).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testTimeToTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TIME> rows =
      SELECT(
        CAST(t.typeTime).AS.TIME()).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDateTimeToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.typeDatetime).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDateTimeToDate() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DATE> rows =
      SELECT(
        CAST(t.typeDatetime).AS.DATE()).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDateTimeToTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TIME> rows =
      SELECT(
        CAST(t.typeDatetime).AS.TIME()).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testDateTimeToDateTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DATETIME> rows =
      SELECT(
        CAST(t.typeDatetime).AS.DATETIME()).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testClobToChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CAST(t.typeClob).AS.CHAR(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testClobToClob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CLOB> rows =
      SELECT(
        CAST(t.typeClob).AS.CLOB(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBlobToBlob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BLOB> rows =
      SELECT(
        CAST(t.typeBlob).AS.BLOB(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBinaryToBlob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BLOB> rows =
      SELECT(
        CAST(t.typeBinary).AS.BLOB(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testBinaryToBinary() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BINARY> rows =
      SELECT(
        CAST(t.typeBinary).AS.BINARY(254)).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }
}