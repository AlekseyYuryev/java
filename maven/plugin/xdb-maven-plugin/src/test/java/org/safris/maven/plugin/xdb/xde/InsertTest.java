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

import static org.safris.xdb.entities.DML.INSERT;
import static org.safris.xdb.entities.DML.SELECT;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.commons.test.LoggableTest;
import org.safris.xdb.entities.DML.IS;
import org.safris.xdb.entities.types;

@RunWith(TypesTestRunner.class)
public class InsertTest extends LoggableTest {
  @Test
  public void testInsertSingle() throws IOException, SQLException {
    final types.Type t = new types.Type();
    t.bigintType.set(8493l);
    t.binaryType.set("abc".getBytes());
    t.blobType.set(new ByteArrayInputStream("abc".getBytes()));
    t.booleanType.set(false);
    t.charType.set("hello");
    t.clobType.set(new StringReader("abc"));
    t.datetimeType.set(LocalDateTime.now());
    t.dateType.set(LocalDate.now());
    t.decimalType.set(new BigDecimal("12.34"));
    t.doubleType.set(32d);
    t.enumType.set(types.Type.EnumType.FOUR);
    t.floatType.set(42f);
    t.intType.set(2345);
    t.mediumintType.set((short)32432);
    t.smallintType.set((byte)127);
    t.timeType.set(LocalTime.now());
    Assert.assertEquals(1, INSERT(t).execute().length);
  }

  @Test
  public void testInsertMultiple() throws IOException, SQLException {
    final types.Type t1 = new types.Type();
    t1.bigintType.set(8493l);
    t1.binaryType.set("abc".getBytes());
    t1.blobType.set(new ByteArrayInputStream("abc".getBytes()));
    t1.booleanType.set(false);
    t1.charType.set("hello");
    t1.clobType.set(new StringReader("abc"));
    t1.datetimeType.set(LocalDateTime.now());
    t1.dateType.set(LocalDate.now());
    t1.decimalType.set(new BigDecimal("12.34"));
    t1.doubleType.set(32d);
    t1.enumType.set(types.Type.EnumType.FOUR);
    t1.floatType.set(42f);
    t1.intType.set(2345);
    t1.mediumintType.set((short)32432);
    t1.smallintType.set((byte)127);
    t1.timeType.set(LocalTime.now());

    final types.Type t2 = new types.Type();
    t2.bigintType.set(843l);
    t2.binaryType.set("abcd".getBytes());
    t2.blobType.set(new ByteArrayInputStream("abcd".getBytes()));
    t2.booleanType.set(true);
    t2.charType.set("hello hi");
    t2.clobType.set(new StringReader("abcd"));
    t2.datetimeType.set(LocalDateTime.now());
    t2.dateType.set(LocalDate.now());
    t2.decimalType.set(new BigDecimal("123.34"));
    t2.doubleType.set(322d);
    t2.enumType.set(types.Type.EnumType.FOUR);
    t2.floatType.set(32f);
    t2.intType.set(1345);
    t2.mediumintType.set((short)22432);
    t2.smallintType.set((byte)-127);
    t2.timeType.set(LocalTime.now());

    Assert.assertEquals(2, INSERT(t1, t2).execute().length);
  }

  @Test
  public void testInsertSelect() throws IOException, SQLException {
    final types.Type t = new types.Type();
    final int[] results = INSERT(SELECT(t).FROM(t).WHERE(IS.NOT.NULL(t.charType))).execute();
    Assert.assertEquals(878, results[0]);
  }
}