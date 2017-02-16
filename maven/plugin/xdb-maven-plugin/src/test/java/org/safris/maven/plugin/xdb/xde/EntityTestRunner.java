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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.safris.xdb.data.DataTest;
import org.safris.xdb.entities.EntityDataSource;
import org.safris.xdb.entities.EntityRegistry;
import org.safris.xdb.entities.Schema;

public abstract class EntityTestRunner extends BlockJUnit4ClassRunner {
  public EntityTestRunner(final Class<?> klass) throws InitializationError {
    super(klass);
    try {
      DataTest.initDB();
      EntityRegistry.register(entityClass(), PreparedStatement.class, new EntityDataSource() {
        @Override
        public Connection getConnection() throws SQLException {
          return DataTest.createConnection();
        }
      });
    }
    catch (final ClassNotFoundException | IOException | SQLException e) {
      throw new InitializationError(e);
    }
  }

  protected abstract Class<? extends Schema> entityClass();

  @Override
  public void run(final RunNotifier notifier) {
    notifier.addListener(new RunListener() {
      @Override
      public void testFinished(final Description description) throws Exception {
        new File("derby.log").deleteOnExit();
      }
    });
    super.run(notifier);
  }
}