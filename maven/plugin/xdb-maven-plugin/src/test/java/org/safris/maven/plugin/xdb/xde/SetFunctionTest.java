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

import static org.safris.xdb.entities.DML.ALL;
import static org.safris.xdb.entities.DML.AVG;
import static org.safris.xdb.entities.DML.DISTINCT;
import static org.safris.xdb.entities.DML.MAX;
import static org.safris.xdb.entities.DML.MIN;
import static org.safris.xdb.entities.DML.SELECT;
import static org.safris.xdb.entities.DML.SUM;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.safris.xdb.entities.DataType;
import org.safris.xdb.entities.RowIterator;

import xdb.ddl.classicmodels;

public class SetFunctionTest extends IntegratedTest {
  @Test
  public void testSetFunctions() throws IOException, SQLException {
    final classicmodels.Customer c = new classicmodels.Customer();
    final RowIterator<? extends DataType<?>> rows =
      SELECT(
        AVG(ALL, c.phone),
        MAX(c.city),
        MIN(c.country),
        SUM(DISTINCT, c.salesEmployeeNumber)).
      FROM(c).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(BigInteger.valueOf(24367857008l), rows.nextEntity().get());
    Assert.assertEquals("White Plains", rows.nextEntity().get());
    Assert.assertEquals(classicmodels.Address.Country.AU, rows.nextEntity().get());
    Assert.assertEquals(Long.valueOf(21003), rows.nextEntity().get());
  }
}