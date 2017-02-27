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
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.dbb.ddlx.runner.Derby;
import org.safris.dbb.ddlx.runner.MySQL;
import org.safris.dbb.ddlx.runner.PostgreSQL;
import org.safris.dbb.ddlx.runner.SQLite;
import org.safris.dbb.jsql.Interval;
import org.safris.dbb.jsql.Interval.Unit;
import org.safris.dbb.jsql.RowIterator;
import org.safris.dbb.jsql.classicmodels;
import org.safris.dbb.jsql.type;
import org.safris.dbb.jsql.types;
import org.safris.maven.plugin.dbb.jsql.runner.VendorSchemaRunner;

@RunWith(VendorSchemaRunner.class)
@VendorSchemaRunner.Schema({classicmodels.class, types.class})
@VendorSchemaRunner.Test({Derby.class, SQLite.class})
@VendorSchemaRunner.Integration({MySQL.class, PostgreSQL.class})
public class DateTimeValueExpressionTest {
  @Test
  @VendorSchemaRunner.Unsupported(SQLite.class)
  public void testMicros() throws IOException, SQLException {
    final types.Type p = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        p.datetimeType,
        ADD(p.datetimeType, new Interval(2, Unit.MICROS)),
        SUB(p.datetimeType, new Interval(2, Unit.MICROS))).
      FROM(p).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      final LocalDateTime dateTime = rows.nextEntity().get();
      Assert.assertEquals(dateTime.plus(2, Unit.MICROS.unit()), rows.nextEntity().get());
      Assert.assertEquals(dateTime.minus(2, Unit.MICROS.unit()), rows.nextEntity().get());
    }
  }

  @Test
  @VendorSchemaRunner.Unsupported(SQLite.class)
  public void testMillis() throws IOException, SQLException {
    final types.Type p = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        p.datetimeType,
        ADD(p.datetimeType, new Interval(2, Unit.MILLIS)),
        SUB(p.datetimeType, new Interval(2, Unit.MILLIS))).
      FROM(p).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      final LocalDateTime dateTime = rows.nextEntity().get();
      Assert.assertEquals(dateTime.plus(2, Unit.MILLIS.unit()), rows.nextEntity().get());
      Assert.assertEquals(dateTime.minus(2, Unit.MILLIS.unit()), rows.nextEntity().get());
    }
  }

  @Test
  public void testSeconds() throws IOException, SQLException {
    final types.Type p = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        p.datetimeType,
        ADD(p.datetimeType, new Interval(2, Unit.SECONDS)),
        SUB(p.datetimeType, new Interval(2, Unit.SECONDS))).
      FROM(p).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      final LocalDateTime dateTime = rows.nextEntity().get();
      Assert.assertEquals(dateTime.plus(2, Unit.SECONDS.unit()), rows.nextEntity().get());
      Assert.assertEquals(dateTime.minus(2, Unit.SECONDS.unit()), rows.nextEntity().get());
    }
  }

  @Test
  public void testMinutes() throws IOException, SQLException {
    final types.Type p = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        p.datetimeType,
        ADD(p.datetimeType, new Interval(2, Unit.MINUTES)),
        SUB(p.datetimeType, new Interval(2, Unit.MINUTES))).
      FROM(p).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      final LocalDateTime dateTime = rows.nextEntity().get();
      Assert.assertEquals(dateTime.plus(2, Unit.MINUTES.unit()), rows.nextEntity().get());
      Assert.assertEquals(dateTime.minus(2, Unit.MINUTES.unit()), rows.nextEntity().get());
    }
  }

  @Test
  public void testHours() throws IOException, SQLException {
    final types.Type p = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        p.datetimeType,
        ADD(p.datetimeType, new Interval(2, Unit.HOURS)),
        SUB(p.datetimeType, new Interval(2, Unit.HOURS))).
      FROM(p).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      final LocalDateTime dateTime = rows.nextEntity().get();
      Assert.assertEquals(dateTime.plus(2, Unit.HOURS.unit()), rows.nextEntity().get());
      Assert.assertEquals(dateTime.minus(2, Unit.HOURS.unit()), rows.nextEntity().get());
    }
  }

  @Test
  public void testDays() throws IOException, SQLException {
    final types.Type p = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        p.datetimeType,
        ADD(p.datetimeType, new Interval(2, Unit.DAYS)),
        SUB(p.datetimeType, new Interval(2, Unit.DAYS))).
      FROM(p).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      final LocalDateTime dateTime = rows.nextEntity().get();
      Assert.assertEquals(dateTime.plus(2, Unit.DAYS.unit()), rows.nextEntity().get());
      Assert.assertEquals(dateTime.minus(2, Unit.DAYS.unit()), rows.nextEntity().get());
    }
  }

  @Test
  public void testWeeks() throws IOException, SQLException {
    final types.Type p = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        p.datetimeType,
        ADD(p.datetimeType, new Interval(2, Unit.WEEKS)),
        SUB(p.datetimeType, new Interval(2, Unit.WEEKS))).
      FROM(p).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      final LocalDateTime dateTime = rows.nextEntity().get();
      Assert.assertEquals(dateTime.plus(2, Unit.WEEKS.unit()), rows.nextEntity().get());
      Assert.assertEquals(dateTime.minus(2, Unit.WEEKS.unit()), rows.nextEntity().get());
    }
  }

  @Test
  public void testMonths() throws IOException, SQLException {
    final types.Type p = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        p.datetimeType,
        ADD(p.datetimeType, new Interval(2, Unit.MONTHS)),
        SUB(p.datetimeType, new Interval(2, Unit.MONTHS))).
      FROM(p).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      final LocalDateTime dateTime = rows.nextEntity().get();
      Assert.assertEquals(dateTime.plus(2, Unit.MONTHS.unit()), rows.nextEntity().get());
      Assert.assertEquals(dateTime.minus(2, Unit.MONTHS.unit()), rows.nextEntity().get());
    }
  }

  @Test
  public void testQuarters() throws IOException, SQLException {
    final types.Type p = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        p.datetimeType,
        ADD(p.datetimeType, new Interval(2, Unit.QUARTERS)),
        SUB(p.datetimeType, new Interval(2, Unit.QUARTERS))).
      FROM(p).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      final LocalDateTime dateTime = rows.nextEntity().get();
      Assert.assertEquals(dateTime.plus(2, Unit.QUARTERS.unit()), rows.nextEntity().get());
      Assert.assertEquals(dateTime.minus(2, Unit.QUARTERS.unit()), rows.nextEntity().get());
    }
  }

  @Test
  public void testYears() throws IOException, SQLException {
    final types.Type p = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        p.datetimeType,
        ADD(p.datetimeType, new Interval(2, Unit.YEARS)),
        SUB(p.datetimeType, new Interval(2, Unit.YEARS))).
      FROM(p).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      final LocalDateTime dateTime = rows.nextEntity().get();
      Assert.assertEquals(dateTime.plus(2, Unit.YEARS.unit()), rows.nextEntity().get());
      Assert.assertEquals(dateTime.minus(2, Unit.YEARS.unit()), rows.nextEntity().get());
    }
  }

  @Test
  public void testDecades() throws IOException, SQLException {
    final types.Type p = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        p.datetimeType,
        ADD(p.datetimeType, new Interval(2, Unit.DECADES)),
        SUB(p.datetimeType, new Interval(2, Unit.DECADES))).
      FROM(p).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      final LocalDateTime dateTime = rows.nextEntity().get();
      Assert.assertEquals(dateTime.plus(2, Unit.DECADES.unit()), rows.nextEntity().get());
      Assert.assertEquals(dateTime.minus(2, Unit.DECADES.unit()), rows.nextEntity().get());
    }
  }

  @Test
  public void testCenturies() throws IOException, SQLException {
    final types.Type p = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        p.datetimeType,
        ADD(p.datetimeType, new Interval(2, Unit.CENTURIES)),
        SUB(p.datetimeType, new Interval(2, Unit.CENTURIES))).
      FROM(p).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      final LocalDateTime dateTime = rows.nextEntity().get();
      Assert.assertEquals(dateTime.plus(2, Unit.CENTURIES.unit()), rows.nextEntity().get());
      Assert.assertEquals(dateTime.minus(2, Unit.CENTURIES.unit()), rows.nextEntity().get());
    }
  }

  @Test
  public void testMillenia() throws IOException, SQLException {
    final types.Type p = new types.Type();
    try (final RowIterator<type.DATETIME> rows =
      SELECT(
        p.datetimeType,
        ADD(p.datetimeType, new Interval(2, Unit.MILLENNIA)),
        SUB(p.datetimeType, new Interval(2, Unit.MILLENNIA))).
      FROM(p).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      final LocalDateTime dateTime = rows.nextEntity().get();
      Assert.assertEquals(dateTime.plus(2, Unit.MILLENNIA.unit()), rows.nextEntity().get());
      Assert.assertEquals(dateTime.minus(2, Unit.MILLENNIA.unit()), rows.nextEntity().get());
    }
  }

  @Test
  public void testInWhere() throws IOException, SQLException {
    final classicmodels.Purchase p = new classicmodels.Purchase();
    try (final RowIterator<type.INT> rows =
      SELECT(COUNT()).
      FROM(p).
      WHERE(GT(p.shippedDate, ADD(p.requiredDate, new Interval(2, Unit.DAYS)))).
      execute()) {
      Assert.assertTrue(rows.nextRow());
      Assert.assertEquals(Integer.valueOf(1), rows.nextEntity().get());
    }
  }
}