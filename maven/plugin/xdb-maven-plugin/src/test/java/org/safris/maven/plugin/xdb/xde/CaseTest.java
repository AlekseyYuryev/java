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

import static org.safris.xdb.entities.DML.CASE;
import static org.safris.xdb.entities.DML.EQ;
import static org.safris.xdb.entities.DML.LT;
import static org.safris.xdb.entities.DML.SELECT;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.commons.test.LoggableTest;
import org.safris.xdb.entities.DML.IS;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.type;
import org.safris.xdb.entities.types;

@RunWith(TypesTestRunner.class)
public class CaseTest extends LoggableTest {
  @Test
  public void testSimpleBoolean() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BOOLEAN> rows =
      SELECT(
        CASE(t.typeBoolean).WHEN(true).THEN(t.typeBoolean).ELSE(t.typeBoolean).END().AS(new type.BOOLEAN()),
        CASE(t.typeBoolean).WHEN(true).THEN(true).ELSE(t.typeBoolean).END().AS(new type.BOOLEAN()),
        CASE(t.typeBoolean).WHEN(true).THEN(t.typeBoolean).ELSE(true).END().AS(new type.BOOLEAN())
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSimpleFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        CASE(t.typeFloat).WHEN(1).THEN(t.typeFloat).ELSE(t.typeFloat).END().AS(new type.FLOAT()),
        CASE(t.typeFloat).WHEN(1).THEN(3f).ELSE(t.typeFloat).END().AS(new type.FLOAT()),
        CASE(t.typeFloat).WHEN(1).THEN(t.typeFloat).ELSE(3f).END().AS(new type.FLOAT()),
        CASE(t.typeFloat).WHEN(1).THEN(t.typeFloat).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE(t.typeFloat).WHEN(1).THEN(3f).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE(t.typeFloat).WHEN(1).THEN(t.typeFloat).ELSE(3d).END().AS(new type.DOUBLE()),
        CASE(t.typeFloat).WHEN(1).THEN(t.typeFloat).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeFloat).WHEN(1).THEN(3f).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeFloat).WHEN(1).THEN(t.typeFloat).ELSE(new BigDecimal("3.4")).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeFloat).WHEN(1).THEN(t.typeFloat).ELSE(t.typeSmallint).END().AS(new type.FLOAT()),
        CASE(t.typeFloat).WHEN(1).THEN(3f).ELSE(t.typeSmallint).END().AS(new type.FLOAT()),
        CASE(t.typeFloat).WHEN(1).THEN(t.typeFloat).ELSE((short)3).END().AS(new type.FLOAT()),
        CASE(t.typeFloat).WHEN(1).THEN(t.typeFloat).ELSE(t.typeMediumint).END().AS(new type.DOUBLE()),
        CASE(t.typeFloat).WHEN(1).THEN(3f).ELSE(t.typeMediumint).END().AS(new type.DOUBLE()),
        CASE(t.typeFloat).WHEN(1).THEN(t.typeFloat).ELSE(3).END().AS(new type.DOUBLE()),
        CASE(t.typeFloat).WHEN(1).THEN(t.typeFloat).ELSE(t.typeLong).END().AS(new type.DOUBLE()),
        CASE(t.typeFloat).WHEN(1).THEN(3f).ELSE(t.typeLong).END().AS(new type.DOUBLE()),
        CASE(t.typeFloat).WHEN(1).THEN(t.typeFloat).ELSE(3l).END().AS(new type.DOUBLE()),
        CASE(t.typeFloat).WHEN(1).THEN(t.typeFloat).ELSE(t.typeBigint).END().AS(new type.DOUBLE()),
        CASE(t.typeFloat).WHEN(1).THEN(3f).ELSE(t.typeBigint).END().AS(new type.DOUBLE()),
        CASE(t.typeFloat).WHEN(1).THEN(t.typeFloat).ELSE(new BigInteger("3")).END().AS(new type.DOUBLE())
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSimpleDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        CASE(t.typeDouble).WHEN(1).THEN(t.typeDouble).ELSE(t.typeFloat).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(3d).ELSE(t.typeFloat).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(t.typeDouble).ELSE(3f).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(t.typeDouble).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(3d).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(t.typeDouble).ELSE(3d).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(t.typeDouble).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDouble).WHEN(1).THEN(3d).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDouble).WHEN(1).THEN(t.typeDouble).ELSE(new BigDecimal("3.4")).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDouble).WHEN(1).THEN(t.typeDouble).ELSE(t.typeSmallint).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(3d).ELSE(t.typeSmallint).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(t.typeDouble).ELSE((short)3).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(t.typeDouble).ELSE(t.typeMediumint).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(3d).ELSE(t.typeMediumint).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(t.typeDouble).ELSE(3).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(t.typeDouble).ELSE(t.typeLong).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(3d).ELSE(t.typeLong).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(t.typeDouble).ELSE(3l).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(t.typeDouble).ELSE(t.typeBigint).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(3d).ELSE(t.typeBigint).END().AS(new type.DOUBLE()),
        CASE(t.typeDouble).WHEN(1).THEN(t.typeDouble).ELSE(new BigInteger("3")).END().AS(new type.DOUBLE())
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSimpleDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        CASE(t.typeDecimal).WHEN(1).THEN(t.typeDecimal).ELSE(t.typeFloat).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(new BigDecimal("3")).ELSE(t.typeFloat).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(t.typeDecimal).ELSE(3f).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(t.typeDecimal).ELSE(t.typeDouble).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(new BigDecimal("3")).ELSE(t.typeDouble).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(t.typeDecimal).ELSE(3d).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(t.typeDecimal).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(new BigDecimal("3")).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(t.typeDecimal).ELSE(new BigDecimal("3.4")).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(t.typeDecimal).ELSE(t.typeSmallint).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(new BigDecimal("3")).ELSE(t.typeSmallint).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(t.typeDecimal).ELSE((short)3).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(t.typeDecimal).ELSE(t.typeMediumint).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(new BigDecimal("3")).ELSE(t.typeMediumint).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(t.typeDecimal).ELSE(3).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(t.typeDecimal).ELSE(t.typeLong).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(new BigDecimal("3")).ELSE(t.typeLong).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(t.typeDecimal).ELSE(3l).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(t.typeDecimal).ELSE(t.typeBigint).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(new BigDecimal("3")).ELSE(t.typeBigint).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeDecimal).WHEN(1).THEN(t.typeDecimal).ELSE(new BigInteger("3")).END().AS(new type.DECIMAL(10, 4))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSimpleSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        CASE(t.typeSmallint).WHEN(1).THEN(t.typeSmallint).ELSE(t.typeFloat).END().AS(new type.FLOAT()),
        CASE(t.typeSmallint).WHEN(1).THEN((short)3).ELSE(t.typeFloat).END().AS(new type.FLOAT()),
        CASE(t.typeSmallint).WHEN(1).THEN(t.typeSmallint).ELSE(3f).END().AS(new type.FLOAT()),
        CASE(t.typeSmallint).WHEN(1).THEN(t.typeSmallint).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE(t.typeSmallint).WHEN(1).THEN((short)3).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE(t.typeSmallint).WHEN(1).THEN(t.typeSmallint).ELSE(3d).END().AS(new type.DOUBLE()),
        CASE(t.typeSmallint).WHEN(1).THEN(t.typeSmallint).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeSmallint).WHEN(1).THEN((short)3).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeSmallint).WHEN(1).THEN(t.typeSmallint).ELSE(new BigDecimal("3.4")).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeSmallint).WHEN(1).THEN(t.typeSmallint).ELSE(t.typeSmallint).END().AS(new type.SMALLINT(3)),
        CASE(t.typeSmallint).WHEN(1).THEN((short)3).ELSE(t.typeSmallint).END().AS(new type.FLOAT()),
        CASE(t.typeSmallint).WHEN(1).THEN(t.typeSmallint).ELSE((short)3).END().AS(new type.SMALLINT(3)),
        CASE(t.typeSmallint).WHEN(1).THEN(t.typeSmallint).ELSE(t.typeMediumint).END().AS(new type.MEDIUMINT(3)),
        CASE(t.typeSmallint).WHEN(1).THEN((short)3).ELSE(t.typeMediumint).END().AS(new type.DOUBLE()),
        CASE(t.typeSmallint).WHEN(1).THEN(t.typeSmallint).ELSE(3).END().AS(new type.MEDIUMINT(3)),
        CASE(t.typeSmallint).WHEN(1).THEN(t.typeSmallint).ELSE(t.typeLong).END().AS(new type.INT(3)),
        CASE(t.typeSmallint).WHEN(1).THEN((short)3).ELSE(t.typeLong).END().AS(new type.DOUBLE()),
        CASE(t.typeSmallint).WHEN(1).THEN(t.typeSmallint).ELSE(3l).END().AS(new type.INT(3)),
        CASE(t.typeSmallint).WHEN(1).THEN(t.typeSmallint).ELSE(t.typeBigint).END().AS(new type.BIGINT(10)),
        CASE(t.typeSmallint).WHEN(1).THEN((short)3).ELSE(t.typeBigint).END().AS(new type.DOUBLE()),
        CASE(t.typeSmallint).WHEN(1).THEN(t.typeSmallint).ELSE(new BigInteger("3")).END().AS(new type.BIGINT(10))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSimpleMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        CASE(t.typeMediumint).WHEN(1).THEN(t.typeMediumint).ELSE(t.typeFloat).END().AS(new type.FLOAT()),
        CASE(t.typeMediumint).WHEN(1).THEN(3).ELSE(t.typeFloat).END().AS(new type.FLOAT()),
        CASE(t.typeMediumint).WHEN(1).THEN(t.typeMediumint).ELSE(3f).END().AS(new type.FLOAT()),
        CASE(t.typeMediumint).WHEN(1).THEN(t.typeMediumint).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE(t.typeMediumint).WHEN(1).THEN(3).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE(t.typeMediumint).WHEN(1).THEN(t.typeMediumint).ELSE(3d).END().AS(new type.DOUBLE()),
        CASE(t.typeMediumint).WHEN(1).THEN(t.typeMediumint).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeMediumint).WHEN(1).THEN(3).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeMediumint).WHEN(1).THEN(t.typeMediumint).ELSE(new BigDecimal("3.4")).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeMediumint).WHEN(1).THEN(t.typeMediumint).ELSE(t.typeSmallint).END().AS(new type.MEDIUMINT(3)),
        CASE(t.typeMediumint).WHEN(1).THEN(3).ELSE(t.typeSmallint).END().AS(new type.MEDIUMINT(3)),
        CASE(t.typeMediumint).WHEN(1).THEN(t.typeMediumint).ELSE((short)3).END().AS(new type.MEDIUMINT(3)),
        CASE(t.typeMediumint).WHEN(1).THEN(t.typeMediumint).ELSE(t.typeMediumint).END().AS(new type.MEDIUMINT(3)),
        CASE(t.typeMediumint).WHEN(1).THEN(3).ELSE(t.typeMediumint).END().AS(new type.MEDIUMINT(3)),
        CASE(t.typeMediumint).WHEN(1).THEN(t.typeMediumint).ELSE(3).END().AS(new type.MEDIUMINT(3)),
        CASE(t.typeMediumint).WHEN(1).THEN(t.typeMediumint).ELSE(t.typeLong).END().AS(new type.INT(3)),
        CASE(t.typeMediumint).WHEN(1).THEN(3).ELSE(t.typeLong).END().AS(new type.INT(3)),
        CASE(t.typeMediumint).WHEN(1).THEN(t.typeMediumint).ELSE(3l).END().AS(new type.INT(3)),
        CASE(t.typeMediumint).WHEN(1).THEN(t.typeMediumint).ELSE(t.typeBigint).END().AS(new type.BIGINT(10)),
        CASE(t.typeMediumint).WHEN(1).THEN(3).ELSE(t.typeBigint).END().AS(new type.BIGINT(10)),
        CASE(t.typeMediumint).WHEN(1).THEN(t.typeMediumint).ELSE(new BigInteger("3")).END().AS(new type.BIGINT(10))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSimpleInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        CASE(t.typeLong).WHEN(1).THEN(t.typeLong).ELSE(t.typeFloat).END().AS(new type.DOUBLE()),
        CASE(t.typeLong).WHEN(1).THEN(3l).ELSE(t.typeFloat).END().AS(new type.DOUBLE()),
        CASE(t.typeLong).WHEN(1).THEN(t.typeLong).ELSE(3f).END().AS(new type.DOUBLE()),
        CASE(t.typeLong).WHEN(1).THEN(t.typeLong).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE(t.typeLong).WHEN(1).THEN(3l).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE(t.typeLong).WHEN(1).THEN(t.typeLong).ELSE(3d).END().AS(new type.DOUBLE()),
        CASE(t.typeLong).WHEN(1).THEN(t.typeLong).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeLong).WHEN(1).THEN(3l).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeLong).WHEN(1).THEN(t.typeLong).ELSE(new BigDecimal("3.4")).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeLong).WHEN(1).THEN(t.typeLong).ELSE(t.typeSmallint).END().AS(new type.INT(3)),
        CASE(t.typeLong).WHEN(1).THEN(3l).ELSE(t.typeSmallint).END().AS(new type.INT(3)),
        CASE(t.typeLong).WHEN(1).THEN(t.typeLong).ELSE((short)3).END().AS(new type.INT(3)),
        CASE(t.typeLong).WHEN(1).THEN(t.typeLong).ELSE(t.typeMediumint).END().AS(new type.INT(3)),
        CASE(t.typeLong).WHEN(1).THEN(3l).ELSE(t.typeMediumint).END().AS(new type.INT(3)),
        CASE(t.typeLong).WHEN(1).THEN(t.typeLong).ELSE(3).END().AS(new type.INT(3)),
        CASE(t.typeLong).WHEN(1).THEN(t.typeLong).ELSE(t.typeLong).END().AS(new type.INT(3)),
        CASE(t.typeLong).WHEN(1).THEN(3l).ELSE(t.typeLong).END().AS(new type.INT(3)),
        CASE(t.typeLong).WHEN(1).THEN(t.typeLong).ELSE(3l).END().AS(new type.INT(3)),
        CASE(t.typeLong).WHEN(1).THEN(t.typeLong).ELSE(t.typeBigint).END().AS(new type.BIGINT(10)),
        CASE(t.typeLong).WHEN(1).THEN(3l).ELSE(t.typeBigint).END().AS(new type.BIGINT(10)),
        CASE(t.typeLong).WHEN(1).THEN(t.typeLong).ELSE(new BigInteger("3")).END().AS(new type.BIGINT(10))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSimpleBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        CASE(t.typeBigint).WHEN(1).THEN(t.typeBigint).ELSE(t.typeFloat).END().AS(new type.DOUBLE()),
        CASE(t.typeBigint).WHEN(1).THEN(new BigInteger("3")).ELSE(t.typeFloat).END().AS(new type.DOUBLE()),
        CASE(t.typeBigint).WHEN(1).THEN(t.typeBigint).ELSE(3f).END().AS(new type.DOUBLE()),
        CASE(t.typeBigint).WHEN(1).THEN(t.typeBigint).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE(t.typeBigint).WHEN(1).THEN(new BigInteger("3")).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE(t.typeBigint).WHEN(1).THEN(t.typeBigint).ELSE(3d).END().AS(new type.DOUBLE()),
        CASE(t.typeBigint).WHEN(1).THEN(t.typeBigint).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeBigint).WHEN(1).THEN(new BigInteger("3")).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeBigint).WHEN(1).THEN(t.typeBigint).ELSE(new BigDecimal("3.4")).END().AS(new type.DECIMAL(10, 4)),
        CASE(t.typeBigint).WHEN(1).THEN(t.typeBigint).ELSE(t.typeSmallint).END().AS(new type.BIGINT(10)),
        CASE(t.typeBigint).WHEN(1).THEN(new BigInteger("3")).ELSE(t.typeSmallint).END().AS(new type.BIGINT(10)),
        CASE(t.typeBigint).WHEN(1).THEN(t.typeBigint).ELSE((short)3).END().AS(new type.BIGINT(10)),
        CASE(t.typeBigint).WHEN(1).THEN(t.typeBigint).ELSE(t.typeMediumint).END().AS(new type.BIGINT(10)),
        CASE(t.typeBigint).WHEN(1).THEN(new BigInteger("3")).ELSE(t.typeMediumint).END().AS(new type.BIGINT(10)),
        CASE(t.typeBigint).WHEN(1).THEN(t.typeBigint).ELSE(3).END().AS(new type.BIGINT(10)),
        CASE(t.typeBigint).WHEN(1).THEN(t.typeBigint).ELSE(t.typeLong).END().AS(new type.BIGINT(10)),
        CASE(t.typeBigint).WHEN(1).THEN(new BigInteger("3")).ELSE(t.typeLong).END().AS(new type.BIGINT(10)),
        CASE(t.typeBigint).WHEN(1).THEN(t.typeBigint).ELSE(3l).END().AS(new type.BIGINT(10)),
        CASE(t.typeBigint).WHEN(1).THEN(t.typeBigint).ELSE(t.typeBigint).END().AS(new type.BIGINT(10)),
        CASE(t.typeBigint).WHEN(1).THEN(new BigInteger("3")).ELSE(t.typeBigint).END().AS(new type.BIGINT(10)),
        CASE(t.typeBigint).WHEN(1).THEN(t.typeBigint).ELSE(new BigInteger("3")).END().AS(new type.BIGINT(10))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSimpleBinary() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BINARY> rows =
      SELECT(
        CASE(t.typeBinary).WHEN("value".getBytes()).THEN(t.typeBinary).ELSE(t.typeBinary).END().AS(new type.BINARY(255)),
        CASE(t.typeBinary).WHEN("value".getBytes()).THEN(new byte[] {0x00, 0x01}).ELSE(t.typeBinary).END().AS(new type.BINARY(255)),
        CASE(t.typeBinary).WHEN("value".getBytes()).THEN(t.typeBinary).ELSE(new byte[] {0x00, 0x01}).END().AS(new type.BINARY(255))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSimpleDate() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DATE> rows =
      SELECT(
        CASE(t.typeDate).WHEN(LocalDate.now()).THEN(t.typeDate).ELSE(t.typeDate).END().AS(new type.DATE()),
        CASE(t.typeDate).WHEN(LocalDate.now()).THEN(LocalDate.now()).ELSE(t.typeDate).END().AS(new type.DATE()),
        CASE(t.typeDate).WHEN(LocalDate.now()).THEN(t.typeDate).ELSE(LocalDate.now()).END().AS(new type.DATE())
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSimpleTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TIME> rows =
      SELECT(
          CASE(t.typeTime).WHEN(LocalTime.now()).THEN(t.typeTime).ELSE(t.typeTime).END().AS(new type.TIME()),
          CASE(t.typeTime).WHEN(LocalTime.now()).THEN(LocalTime.now()).ELSE(t.typeTime).END().AS(new type.TIME()),
          CASE(t.typeTime).WHEN(LocalTime.now()).THEN(t.typeTime).ELSE(LocalTime.now()).END().AS(new type.TIME())
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSimpleDateTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DATETIME> rows =
      SELECT(
          CASE(t.typeDatetime).WHEN(LocalDateTime.now()).THEN(t.typeDatetime).ELSE(t.typeDatetime).END().AS(new type.DATETIME()),
          CASE(t.typeDatetime).WHEN(LocalDateTime.now()).THEN(LocalDateTime.now()).ELSE(t.typeDatetime).END().AS(new type.DATETIME()),
          CASE(t.typeDatetime).WHEN(LocalDateTime.now()).THEN(t.typeDatetime).ELSE(LocalDateTime.now()).END().AS(new type.DATETIME())
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testSimpleChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CASE(t.typeChar).WHEN("").THEN(t.typeChar).ELSE(t.typeChar).END().AS(new type.CHAR(255, false)),
        CASE(t.typeChar).WHEN("").THEN("char").ELSE(t.typeChar).END().AS(new type.CHAR(255, false)),
        CASE(t.typeChar).WHEN("").THEN(t.typeChar).ELSE("char").END().AS(new type.CHAR(255, false)),
        CASE(t.typeChar).WHEN("").THEN(t.typeChar).ELSE(t.typeEnum).END().AS(new type.CHAR(255, false)),
        CASE(t.typeChar).WHEN("").THEN("char").ELSE(t.typeEnum).END().AS(new type.CHAR(255, false)),
        CASE(t.typeChar).WHEN("").THEN(t.typeEnum).ELSE("char").END().AS(new type.CHAR(255, false))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testSimpleEnum() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Textual<?>> rows =
      SELECT(
        CASE(t.typeEnum).WHEN(types.Type.TypeEnum.EIGHT).THEN(t.typeEnum).ELSE(t.typeChar).END().AS(new type.CHAR(255, false)),
        CASE(t.typeEnum).WHEN(types.Type.TypeEnum.EIGHT).THEN(types.Type.TypeEnum.EIGHT).ELSE(t.typeChar).END().AS(new type.CHAR(255, false)),
        CASE(t.typeEnum).WHEN(types.Type.TypeEnum.EIGHT).THEN(t.typeEnum).ELSE(types.Type.TypeEnum.EIGHT).END().AS(new type.ENUM<types.Type.TypeEnum>(types.Type.TypeEnum.class)),
        CASE(t.typeEnum).WHEN(types.Type.TypeEnum.EIGHT).THEN(t.typeEnum).ELSE(t.typeEnum).END().AS(new type.ENUM<types.Type.TypeEnum>(types.Type.TypeEnum.class)),
        CASE(t.typeEnum).WHEN(types.Type.TypeEnum.EIGHT).THEN(types.Type.TypeEnum.EIGHT).ELSE(t.typeEnum).END().AS(new type.ENUM<types.Type.TypeEnum>(types.Type.TypeEnum.class)),
        CASE(t.typeEnum).WHEN(types.Type.TypeEnum.EIGHT).THEN(t.typeEnum).ELSE(types.Type.TypeEnum.EIGHT).END().AS(new type.ENUM<types.Type.TypeEnum>(types.Type.TypeEnum.class))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testSearchBoolean() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BOOLEAN> rows =
      SELECT(
        CASE.WHEN(EQ(t.typeBoolean, true)).THEN(t.typeBoolean).ELSE(t.typeBoolean).END().AS(new type.BOOLEAN()),
        CASE.WHEN(EQ(t.typeBoolean, true)).THEN(true).ELSE(t.typeBoolean).END().AS(new type.BOOLEAN()),
        CASE.WHEN(EQ(t.typeBoolean, true)).THEN(t.typeBoolean).ELSE(true).END().AS(new type.BOOLEAN())
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSearchFloat() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(t.typeFloat).ELSE(t.typeFloat).END().AS(new type.FLOAT()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(3f).ELSE(t.typeFloat).END().AS(new type.FLOAT()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(t.typeFloat).ELSE(3f).END().AS(new type.FLOAT()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(t.typeFloat).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(3f).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(t.typeFloat).ELSE(3d).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(t.typeFloat).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(3f).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(t.typeFloat).ELSE(new BigDecimal("3.4")).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(t.typeFloat).ELSE(t.typeSmallint).END().AS(new type.FLOAT()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(3f).ELSE(t.typeSmallint).END().AS(new type.FLOAT()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(t.typeFloat).ELSE((short)3).END().AS(new type.FLOAT()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(t.typeFloat).ELSE(t.typeMediumint).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(3f).ELSE(t.typeMediumint).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(t.typeFloat).ELSE(3).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(t.typeFloat).ELSE(t.typeLong).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(3f).ELSE(t.typeLong).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(t.typeFloat).ELSE(3l).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(t.typeFloat).ELSE(t.typeBigint).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(3f).ELSE(t.typeBigint).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeFloat, 1)).THEN(t.typeFloat).ELSE(new BigInteger("3")).END().AS(new type.DECIMAL(10, 4))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSearchDouble() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(t.typeDouble).ELSE(t.typeFloat).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(3d).ELSE(t.typeFloat).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(t.typeDouble).ELSE(3f).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(t.typeDouble).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(3d).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(t.typeDouble).ELSE(3d).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(t.typeDouble).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(3d).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(t.typeDouble).ELSE(new BigDecimal("3.4")).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(t.typeDouble).ELSE(t.typeSmallint).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(3d).ELSE(t.typeSmallint).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(t.typeDouble).ELSE((short)3).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(t.typeDouble).ELSE(t.typeMediumint).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(3d).ELSE(t.typeMediumint).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(t.typeDouble).ELSE(3).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(t.typeDouble).ELSE(t.typeLong).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(3d).ELSE(t.typeLong).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(t.typeDouble).ELSE(3l).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(t.typeDouble).ELSE(t.typeBigint).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(3d).ELSE(t.typeBigint).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDouble, 1)).THEN(t.typeDouble).ELSE(new BigInteger("3")).END().AS(new type.DECIMAL(10, 4))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSearchDecimal() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(t.typeDecimal).ELSE(t.typeFloat).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(new BigDecimal("3")).ELSE(t.typeFloat).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(t.typeDecimal).ELSE(3f).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(t.typeDecimal).ELSE(t.typeDouble).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(new BigDecimal("3")).ELSE(t.typeDouble).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(t.typeDecimal).ELSE(3d).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(t.typeDecimal).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(new BigDecimal("3")).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(t.typeDecimal).ELSE(new BigDecimal("3.4")).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(t.typeDecimal).ELSE(t.typeSmallint).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(new BigDecimal("3")).ELSE(t.typeSmallint).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(t.typeDecimal).ELSE((short)3).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(t.typeDecimal).ELSE(t.typeMediumint).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(new BigDecimal("3")).ELSE(t.typeMediumint).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(t.typeDecimal).ELSE(3).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(t.typeDecimal).ELSE(t.typeLong).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(new BigDecimal("3")).ELSE(t.typeLong).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(t.typeDecimal).ELSE(3l).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(t.typeDecimal).ELSE(t.typeBigint).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(new BigDecimal("3")).ELSE(t.typeBigint).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeDecimal, 1)).THEN(t.typeDecimal).ELSE(new BigInteger("3")).END().AS(new type.DECIMAL(10, 4))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSearchSmallInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN(t.typeSmallint).ELSE(t.typeFloat).END().AS(new type.FLOAT()),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN((short)3).ELSE(t.typeFloat).END().AS(new type.FLOAT()),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN(t.typeSmallint).ELSE(3f).END().AS(new type.FLOAT()),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN(t.typeSmallint).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN((short)3).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN(t.typeSmallint).ELSE(3d).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN(t.typeSmallint).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN((short)3).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN(t.typeSmallint).ELSE(new BigDecimal("3.4")).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN(t.typeSmallint).ELSE(t.typeSmallint).END().AS(new type.SMALLINT(3)),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN((short)3).ELSE(t.typeSmallint).END().AS(new type.FLOAT()),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN(t.typeSmallint).ELSE((short)3).END().AS(new type.SMALLINT(3)),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN(t.typeSmallint).ELSE(t.typeMediumint).END().AS(new type.MEDIUMINT(3)),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN((short)3).ELSE(t.typeMediumint).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN(t.typeSmallint).ELSE(3).END().AS(new type.MEDIUMINT(3)),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN(t.typeSmallint).ELSE(t.typeLong).END().AS(new type.INT(3)),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN((short)3).ELSE(t.typeLong).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN(t.typeSmallint).ELSE(3l).END().AS(new type.INT(3)),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN(t.typeSmallint).ELSE(t.typeBigint).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN((short)3).ELSE(t.typeBigint).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeSmallint, 1)).THEN(t.typeSmallint).ELSE(new BigInteger("3")).END().AS(new type.BIGINT(10))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSearchMediumInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(t.typeMediumint).ELSE(t.typeFloat).END().AS(new type.FLOAT()),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(3).ELSE(t.typeFloat).END().AS(new type.FLOAT()),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(t.typeMediumint).ELSE(3f).END().AS(new type.FLOAT()),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(t.typeMediumint).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(3).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(t.typeMediumint).ELSE(3d).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(t.typeMediumint).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(3).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(t.typeMediumint).ELSE(new BigDecimal("3.4")).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(t.typeMediumint).ELSE(t.typeSmallint).END().AS(new type.MEDIUMINT(3)),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(3).ELSE(t.typeSmallint).END().AS(new type.MEDIUMINT(3)),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(t.typeMediumint).ELSE((short)3).END().AS(new type.MEDIUMINT(3)),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(t.typeMediumint).ELSE(t.typeMediumint).END().AS(new type.MEDIUMINT(3)),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(3).ELSE(t.typeMediumint).END().AS(new type.MEDIUMINT(3)),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(t.typeMediumint).ELSE(3).END().AS(new type.MEDIUMINT(3)),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(t.typeMediumint).ELSE(t.typeLong).END().AS(new type.INT(3)),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(3).ELSE(t.typeLong).END().AS(new type.INT(3)),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(t.typeMediumint).ELSE(3l).END().AS(new type.INT(3)),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(t.typeMediumint).ELSE(t.typeBigint).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(3).ELSE(t.typeBigint).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeMediumint, 1)).THEN(t.typeMediumint).ELSE(new BigInteger("3")).END().AS(new type.BIGINT(10))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSearchInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        CASE.WHEN(LT(t.typeLong, 1)).THEN(t.typeLong).ELSE(t.typeFloat).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(3l).ELSE(t.typeFloat).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(t.typeLong).ELSE(3f).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(t.typeLong).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(3l).ELSE(t.typeDouble).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(t.typeLong).ELSE(3d).END().AS(new type.DOUBLE()),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(t.typeLong).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(3l).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(t.typeLong).ELSE(new BigDecimal("3.4")).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(t.typeLong).ELSE(t.typeSmallint).END().AS(new type.INT(3)),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(3l).ELSE(t.typeSmallint).END().AS(new type.INT(3)),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(t.typeLong).ELSE((short)3).END().AS(new type.INT(3)),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(t.typeLong).ELSE(t.typeMediumint).END().AS(new type.INT(3)),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(3l).ELSE(t.typeMediumint).END().AS(new type.INT(3)),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(t.typeLong).ELSE(3).END().AS(new type.INT(3)),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(t.typeLong).ELSE(t.typeLong).END().AS(new type.INT(3)),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(3l).ELSE(t.typeLong).END().AS(new type.INT(3)),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(t.typeLong).ELSE(3l).END().AS(new type.INT(3)),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(t.typeLong).ELSE(t.typeBigint).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(3l).ELSE(t.typeBigint).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeLong, 1)).THEN(t.typeLong).ELSE(new BigInteger("3")).END().AS(new type.BIGINT(10))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSearchBigInt() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Numeric<?>> rows =
      SELECT(
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(t.typeBigint).ELSE(t.typeFloat).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(new BigInteger("3")).ELSE(t.typeFloat).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(t.typeBigint).ELSE(3f).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(t.typeBigint).ELSE(t.typeDouble).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(new BigInteger("3")).ELSE(t.typeDouble).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(t.typeBigint).ELSE(3d).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(t.typeBigint).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(new BigInteger("3")).ELSE(t.typeDecimal).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(t.typeBigint).ELSE(new BigDecimal("3.4")).END().AS(new type.DECIMAL(10, 4)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(t.typeBigint).ELSE(t.typeSmallint).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(new BigInteger("3")).ELSE(t.typeSmallint).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(t.typeBigint).ELSE((short)3).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(t.typeBigint).ELSE(t.typeMediumint).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(new BigInteger("3")).ELSE(t.typeMediumint).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(t.typeBigint).ELSE(3).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(t.typeBigint).ELSE(t.typeLong).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(new BigInteger("3")).ELSE(t.typeLong).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(t.typeBigint).ELSE(3l).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(t.typeBigint).ELSE(t.typeBigint).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(new BigInteger("3")).ELSE(t.typeBigint).END().AS(new type.BIGINT(10)),
        CASE.WHEN(LT(t.typeBigint, 1)).THEN(t.typeBigint).ELSE(new BigInteger("3")).END().AS(new type.BIGINT(10))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSearchClob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CLOB> rows =
      SELECT(
        CASE.WHEN(IS.NOT.NULL(t.typeClob)).THEN(t.typeClob).ELSE(t.typeClob).END().AS(new type.CLOB(255))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSearchBlob() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BLOB> rows =
      SELECT(
        CASE.WHEN(IS.NOT.NULL(t.typeBlob)).THEN(t.typeBlob).ELSE(t.typeBlob).END().AS(new type.BLOB(255))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSearchBinary() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.BINARY> rows =
      SELECT(
        CASE.WHEN(IS.NOT.NULL(t.typeBinary)).THEN(t.typeBinary).ELSE(t.typeBinary).END().AS(new type.BINARY(255)),
        CASE.WHEN(IS.NOT.NULL(t.typeBinary)).THEN(new byte[] {0x00, 0x01}).ELSE(t.typeBinary).END().AS(new type.BINARY(255)),
        CASE.WHEN(IS.NOT.NULL(t.typeBinary)).THEN(t.typeBinary).ELSE(new byte[] {0x00, 0x01}).END().AS(new type.BINARY(255))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSearchDate() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DATE> rows =
      SELECT(
        CASE.WHEN(IS.NOT.NULL(t.typeDate)).THEN(t.typeDate).ELSE(t.typeDate).END().AS(new type.DATE()),
        CASE.WHEN(IS.NOT.NULL(t.typeDate)).THEN(LocalDate.now()).ELSE(t.typeDate).END().AS(new type.DATE()),
        CASE.WHEN(IS.NOT.NULL(t.typeDate)).THEN(t.typeDate).ELSE(LocalDate.now()).END().AS(new type.DATE())
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSearchTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.TIME> rows =
      SELECT(
        CASE.WHEN(IS.NOT.NULL(t.typeTime)).THEN(t.typeTime).ELSE(t.typeTime).END().AS(new type.TIME()),
        CASE.WHEN(IS.NOT.NULL(t.typeTime)).THEN(LocalTime.now()).ELSE(t.typeTime).END().AS(new type.TIME()),
        CASE.WHEN(IS.NOT.NULL(t.typeTime)).THEN(t.typeTime).ELSE(LocalTime.now()).END().AS(new type.TIME())
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  @Ignore
  public void testSearchDateTime() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.DATETIME> rows =
      SELECT(
        CASE.WHEN(IS.NOT.NULL(t.typeDatetime)).THEN(t.typeDatetime).ELSE(t.typeDatetime).END().AS(new type.DATETIME()),
        CASE.WHEN(IS.NOT.NULL(t.typeDatetime)).THEN(LocalDateTime.now()).ELSE(t.typeDatetime).END().AS(new type.DATETIME()),
        CASE.WHEN(IS.NOT.NULL(t.typeDatetime)).THEN(t.typeDatetime).ELSE(LocalDateTime.now()).END().AS(new type.DATETIME())
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testSearchChar() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<type.CHAR> rows =
      SELECT(
        CASE.WHEN(IS.NOT.NULL(t.typeChar)).THEN(t.typeChar).ELSE(t.typeChar).END().AS(new type.CHAR(255, false)),
        CASE.WHEN(IS.NOT.NULL(t.typeChar)).THEN("char").ELSE(t.typeChar).END().AS(new type.CHAR(255, false)),
        CASE.WHEN(IS.NOT.NULL(t.typeChar)).THEN(t.typeChar).ELSE("char").END().AS(new type.CHAR(255, false)),
        CASE.WHEN(IS.NOT.NULL(t.typeChar)).THEN(t.typeChar).ELSE(t.typeEnum).END().AS(new type.CHAR(255, false)),
        CASE.WHEN(IS.NOT.NULL(t.typeChar)).THEN("char").ELSE(t.typeEnum).END().AS(new type.CHAR(255, false)),
        CASE.WHEN(IS.NOT.NULL(t.typeChar)).THEN(t.typeEnum).ELSE("char").END().AS(new type.CHAR(255, false))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }

  @Test
  public void testSearchEnum() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final RowIterator<? extends type.Textual<?>> rows =
      SELECT(
        CASE.WHEN(IS.NOT.NULL(t.typeEnum)).THEN(t.typeEnum).ELSE(t.typeChar).END().AS(new type.CHAR(255, false)),
        CASE.WHEN(IS.NOT.NULL(t.typeEnum)).THEN(types.Type.TypeEnum.EIGHT).ELSE(t.typeChar).END().AS(new type.CHAR(255, false)),
        CASE.WHEN(IS.NOT.NULL(t.typeEnum)).THEN(t.typeEnum).ELSE(types.Type.TypeEnum.EIGHT).END().AS(new type.ENUM<types.Type.TypeEnum>(types.Type.TypeEnum.class)),
        CASE.WHEN(IS.NOT.NULL(t.typeEnum)).THEN(t.typeEnum).ELSE(t.typeEnum).END().AS(new type.ENUM<types.Type.TypeEnum>(types.Type.TypeEnum.class)),
        CASE.WHEN(IS.NOT.NULL(t.typeEnum)).THEN(types.Type.TypeEnum.EIGHT).ELSE(t.typeEnum).END().AS(new type.ENUM<types.Type.TypeEnum>(types.Type.TypeEnum.class)),
        CASE.WHEN(IS.NOT.NULL(t.typeEnum)).THEN(t.typeEnum).ELSE(types.Type.TypeEnum.EIGHT).END().AS(new type.ENUM<types.Type.TypeEnum>(types.Type.TypeEnum.class))
      ).
      FROM(t).
      execute();

    Assert.assertTrue(rows.nextRow());
  }
}