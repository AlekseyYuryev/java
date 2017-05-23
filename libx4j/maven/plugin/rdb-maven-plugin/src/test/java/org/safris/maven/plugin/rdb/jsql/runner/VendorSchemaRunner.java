/* Copyright (c) 2017 lib4j
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

package org.safris.maven.plugin.rdb.jsql.runner;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.safris.rdb.ddlx.runner.Vendor;
import org.safris.rdb.ddlx.runner.VendorRunner;
import org.safris.rdb.jsql.DBConnector;
import org.safris.rdb.jsql.DBRegistry;

public class VendorSchemaRunner extends VendorRunner {
  @Target({ElementType.TYPE, ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface Schema {
    Class<? extends org.safris.rdb.jsql.Schema>[] value();
  }

  public VendorSchemaRunner(final Class<?> klass) throws InitializationError {
    super(klass);
  }

  @Override
  protected void run(final Class<? extends Vendor> vendorClass, final FrameworkMethod method, final Object test) throws Throwable {
    Schema entityClass = method.getMethod().getAnnotation(Schema.class);
    if (entityClass == null)
      entityClass = method.getMethod().getDeclaringClass().getAnnotation(Schema.class);

    if (entityClass != null) {
      for (final Class<? extends org.safris.rdb.jsql.Schema> schemaClass : entityClass.value()) {
        DBRegistry.registerPrepared(schemaClass, new DBConnector() {
          @Override
          public Connection getConnection() throws SQLException {
            try {
              return VendorSchemaRunner.this.getConnection(vendorClass);
            }
            catch (final IOException | ReflectiveOperationException e) {
              throw new SQLException(e);
            }
          }
        });
      }
    }

    if (method.getMethod().getParameterTypes().length > 0)
      throw new Exception("Method " + method.getName() + " should have no parameters");

    method.invokeExplosively(test);
  }
}