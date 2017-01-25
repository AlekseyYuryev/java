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

import static org.safris.xdb.entities.DML.EQ;
import static org.safris.xdb.entities.DML.GT;
import static org.safris.xdb.entities.DML.SELECT;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.safris.xdb.entities.DataType;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.Subject;

import xdb.ddl.classicmodels;

public class SelectTest extends IntegratedTest {
  @Test
  public void testSELECT() throws IOException, SQLException {
    final classicmodels.Office o = new classicmodels.Office();
    final RowIterator<classicmodels.Office> rows =
      SELECT(o).
      FROM(o).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals("100 Market Street", rows.nextEntity().address1.get());
    Assert.assertTrue(rows.nextRow() && rows.nextRow() && rows.nextRow() && rows.nextRow() && rows.nextRow() && rows.nextRow());
    Assert.assertEquals("25 Old Broad Street", rows.nextEntity().address1.get());
    Assert.assertTrue(!rows.nextRow());
  }

  @Test
  public void testWHERE() throws IOException, SQLException {
    final classicmodels.Office o = new classicmodels.Office();
    final RowIterator<? extends DataType<?>> rows =
      SELECT(o.address1, o.latitude).
      FROM(o).
      WHERE(EQ(o.phone, 81332245000l)).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals("4-1 Kioicho", rows.nextEntity().get());
    Assert.assertEquals(35.6811759, rows.nextEntity().get());
  }

  @Test
  public void testMixedSELECT() throws IOException, SQLException {
    final classicmodels.Office o = new classicmodels.Office();
    final RowIterator<? extends Subject<?>> rows =
      SELECT(
        o.latitude,
        o.longitude,
        o,
        o.latitude,
        o.longitude,
        o,
        o.latitude,
        o.longitude).
      FROM(o).
      WHERE(GT(o.latitude, 0)).
      execute();

    Assert.assertTrue(rows.nextRow());
    Assert.assertEquals(37.7942635, ((DataType<?>)rows.nextEntity()).get());
    Assert.assertEquals(-122.3955861d, ((DataType<?>)rows.nextEntity()).get());
    Assert.assertEquals("San Francisco", ((classicmodels.Office)rows.nextEntity()).city.get());
    Assert.assertEquals(37.7942635, ((DataType<?>)rows.nextEntity()).get());
    Assert.assertEquals(-122.3955861d, ((DataType<?>)rows.nextEntity()).get());
    Assert.assertEquals("San Francisco", ((classicmodels.Office)rows.nextEntity()).city.get());
    Assert.assertEquals(37.7942635, ((DataType<?>)rows.nextEntity()).get());
    Assert.assertEquals(-122.3955861d, ((DataType<?>)rows.nextEntity()).get());
  }
}