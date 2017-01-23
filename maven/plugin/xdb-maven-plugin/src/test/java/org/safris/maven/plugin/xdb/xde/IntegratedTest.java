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

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.safris.commons.test.LoggableTest;
import org.safris.xdb.entities.EntitiesTest;

public abstract class IntegratedTest extends LoggableTest {
  @BeforeClass
  public static void createConnection() throws ClassNotFoundException, IOException, SQLException {
    EntitiesTest.createConnection("classicmodels");
  }

//  @AfterClass // FIXME: This is commented out because the DB is being closed before all test classes are run
  public static void destroy() throws SQLException {
    new File("derby.log").deleteOnExit();
    try {
      DriverManager.getConnection("jdbc:derby:;shutdown=true");
    }
    catch (final SQLException e) {
      if (!"XJ015".equals(e.getSQLState()))
        throw e;
    }
  }
}