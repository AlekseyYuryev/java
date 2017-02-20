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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.safris.xdb.entities.EntityDataSource;
import org.safris.xdb.entities.EntityRegistry;
import org.safris.xdb.entities.Schema;
import org.safris.xdb.schema.VendorClassRunner;
import org.safris.xdb.schema.vendor.Vendor;

public class EntityVendorClassRunner extends VendorClassRunner {
  public EntityVendorClassRunner(final Class<?> klass) throws InitializationError {
    super(klass);
  }

  @Override
  protected void run(final Class<? extends Vendor> vendorClass, final FrameworkMethod method, final Object test) throws Throwable {
    EntityClass entityClass = method.getMethod().getAnnotation(EntityClass.class);
    if (entityClass == null)
      entityClass = method.getMethod().getDeclaringClass().getAnnotation(EntityClass.class);

    if (entityClass == null)
      throw new Exception("@" + EntityClass.class.getSimpleName() + " is required on either method or class");

    for (final Class<? extends Schema> cls : entityClass.value()) {
      EntityRegistry.register(cls, PreparedStatement.class, new EntityDataSource() {
        @Override
        public Connection getConnection() throws SQLException {
          try {
            return EntityVendorClassRunner.this.getConnection(vendorClass);
          }
          catch (final IOException | ReflectiveOperationException e) {
            throw new SQLException(e);
          }
        }
      });
    }

    if (method.getMethod().getParameterTypes().length > 0)
      throw new Exception("Method " + method.getName() + " should have no parameters");

    method.invokeExplosively(test);
  }
}