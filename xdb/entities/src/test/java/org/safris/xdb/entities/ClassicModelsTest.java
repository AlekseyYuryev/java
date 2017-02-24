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

package org.safris.xdb.entities;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.safris.commons.xml.XMLException;
import org.safris.dbx.ddlx.runner.Derby;
import org.safris.dbx.ddlx.runner.VendorRunner;

@RunWith(VendorRunner.class)
@VendorRunner.Test(Derby.class)
public class ClassicModelsTest extends EntitiesTest {
  @Test
  public void create() throws IOException, XMLException {
    createEntities("classicmodels");
  }
}