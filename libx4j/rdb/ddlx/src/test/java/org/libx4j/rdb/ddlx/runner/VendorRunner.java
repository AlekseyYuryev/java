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

package org.libx4j.rdb.ddlx.runner;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.lib4j.lang.Arrays;
import org.lib4j.lang.Throwables;
import org.lib4j.logging.DeferredLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

public class VendorRunner extends BlockJUnit4ClassRunner {
  protected static final Logger logger = LoggerFactory.getLogger(VendorRunner.class);

  static {
    final ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    DeferredLogger.defer(logger, logger.iteratorForAppenders().next(), Level.INFO);
  }

  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface RunIn {
    Class<? extends Annotation>[] value();
  }

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface Test {
    Class<? extends Vendor>[] value();
  }

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface Integration {
    Class<? extends Vendor>[] value();
  }

  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface Unsupported {
    Class<? extends Vendor>[] value();
  }

  private static Exception flatten(final SQLException e) {
    final StringBuilder builder = new StringBuilder();
    SQLException next = e;
    while ((next = next.getNextException()) != null)
      builder.append("\n").append(next.getMessage());

    final SQLException exception = new SQLException(e.getMessage() + builder);
    exception.setStackTrace(e.getStackTrace());
    return exception;
  }

  private final boolean integrationTest;

  public VendorRunner(final Class<?> klass) throws InitializationError {
    super(klass);
    this.integrationTest = Boolean.parseBoolean(System.getProperty("integrationTest"));
  }

  private final Map<Class<? extends Vendor>,Vendor> vendors = new HashMap<Class<? extends Vendor>,Vendor>();

  protected Vendor getVendor(final Class<? extends Vendor> vendorClass) throws IllegalAccessException, InstantiationException, IOException, SQLException {
    Vendor vendor = vendors.get(vendorClass);
    if (vendor == null) {
      vendors.put(vendorClass, vendor = vendorClass.newInstance());
      vendor.init();
    }

    return vendor;
  }

  @Override
  protected void validatePublicVoidNoArgMethods(final Class<? extends Annotation> annotation, final boolean isStatic, final List<Throwable> errors) {
    final List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(annotation);
    for (final FrameworkMethod method : methods) {
      method.validatePublicVoid(isStatic, errors);
      checkParameters(method, errors);
    }
  }

  protected void checkParameters(final FrameworkMethod method, final List<Throwable> errors) {
    if (method.getMethod().getParameterTypes().length > 0 && method.getMethod().getParameterTypes()[0] != Connection.class)
      errors.add(new Exception("Method " + method.getName() + " should have no parameters or a " + Connection.class.getName() + " parameter"));
  }

  private final Set<FrameworkMethod> beforeClassMethodsRun = new HashSet<FrameworkMethod>();

  protected void run(final Class<? extends Vendor> vendorClass, final FrameworkMethod method, final Object test) throws Throwable {
    final RunIn runIn = method.getMethod().getAnnotation(RunIn.class);
    if (runIn == null || Arrays.contains(runIn.value(), integrationTest ? Integration.class : Test.class)) {
      if (method.getMethod().getParameterTypes().length > 0) {
        try (final Connection connection = getVendor(vendorClass).getConnection()) {
          logger.info(VendorRunner.class.getSimpleName() + "::" + (integrationTest ? "Integration" : "Test") + "::" + vendorClass.getSimpleName());
          method.invokeExplosively(test, connection);
        }
      }
      else {
        if (test != null) {
          logger.info(VendorRunner.class.getSimpleName() + "::" + (integrationTest ? "Integration" : "Test") + "::" + vendorClass.getSimpleName());
          method.invokeExplosively(test);
          return;
        }
        else if (beforeClassMethodsRun.contains(method))
          return;

        synchronized (beforeClassMethodsRun) {
          if (beforeClassMethodsRun.contains(method))
            return;

          beforeClassMethodsRun.add(method);
          logger.info(VendorRunner.class.getSimpleName() + "::" + (integrationTest ? "Integration" : "Test") + "::" + vendorClass.getSimpleName());
          method.invokeExplosively(test);
        }
      }
    }
  }

  @Override
  protected Statement methodInvoker(final FrameworkMethod method, final Object test) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        DeferredLogger.clear();
        final Class<? extends Vendor> vendorClass = localVendor.get();
        try {
          run(vendorClass, method, test);
        }
        catch (final Throwable e) {
          DeferredLogger.flush(Level.DEBUG);
          final Unsupported unsupported = method.getMethod().getAnnotation(Unsupported.class);
          if (unsupported != null) {
            for (final Class<? extends Vendor> unsupportedVendor : unsupported.value()) {
              if (unsupportedVendor == vendorClass) {
                logger.warn(vendorClass.getSimpleName() + " does not support " + method.getMethod().getDeclaringClass().getSimpleName() + "." + method.getMethod().getName() + "()");
                return;
              }
            }
          }

          Throwables.set(e, "[" + vendorClass.getSimpleName() + "] " + (e.getMessage() != null ? e.getMessage() : ""));
          if (e instanceof SQLException)
            throw flatten((SQLException)e);

          throw e;
        }
      }
    };
  }

  private ThreadLocal<Class<? extends Vendor>> localVendor = new ThreadLocal<Class<? extends Vendor>>();

  private Statement evaluate(final List<FrameworkMethod> befores, final Object target, final Statement statement, final boolean transverse) {
    final Statement vendorStatement = new Statement() {
      @Override
      public void evaluate() throws Throwable {
        final Class<?> testClass = getTestClass().getJavaClass();
        final Test test = testClass.getAnnotation(Test.class);
        final Integration integration = testClass.getAnnotation(Integration.class);
        if (test == null && integration == null)
          throw new Exception("@" + Test.class.getSimpleName() + " or @" + Integration.class.getSimpleName() + " annotation is required on class " + testClass.getSimpleName());

        if (!integrationTest && test != null)
          evaluateVendors(test.value(), befores, target, statement);

        if (integrationTest && integration != null)
          evaluateVendors(integration.value(), befores, target, statement);
      }
    };

    return befores.isEmpty() ? vendorStatement : new RunBefores(statement, befores, target) {
      @Override
      public void evaluate() throws Throwable {
        vendorStatement.evaluate();
      }
    };
  }

  private void evaluateVendors(final Class<? extends Vendor>[] vendorClasses, final List<FrameworkMethod> befores, final Object target, final Statement statement) throws Throwable {
    for (final Class<? extends Vendor> vendorClass : vendorClasses) {
      localVendor.set(vendorClass);
      if (!befores.isEmpty())
        for (final FrameworkMethod before : befores)
          VendorRunner.this.run(vendorClass, before, target);

      statement.evaluate();
    }
  }

  @Override
  protected Statement withBefores(final FrameworkMethod method, final Object target, final Statement statement) {
    return evaluate(getTestClass().getAnnotatedMethods(Before.class), target, statement, false);
  }

  @Override
  public void run(final RunNotifier notifier) {
    notifier.addListener(new RunListener() {
      private Class<?> testClass;

      @Override
      public void testFinished(final Description description) throws Exception {
        testClass = description.getTestClass();
      }

      @Override
      public void testRunFinished(final Result result) throws Exception {
        if (testClass != null) {
          final Class<? extends Vendor>[] vendorClasses;
          if (integrationTest) {
            final Integration vendorIntegration = testClass.getAnnotation(Integration.class);
            vendorClasses = vendorIntegration != null ? vendorIntegration.value() : null;
          }
          else {
            final Test vendorTest = testClass.getAnnotation(Test.class);
            vendorClasses = vendorTest != null ? vendorTest.value() : null;
          }

          if (vendorClasses != null)
            for (final Class<? extends Vendor> vendorClass : vendorClasses)
              getVendor(vendorClass).destroy();
        }
      }
    });
    super.run(notifier);
  }
}