/* Copyright (c) 2016 Seva Safris
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

package org.safris.commons.test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.safris.commons.maven.Log;

public abstract class LoggableTest {
  private static final List<Log.Record> logRecords = new LinkedList<Log.Record>();
  private static final Map<Class<? extends LoggableTest>,Boolean> classToMutex = new HashMap<Class<? extends LoggableTest>,Boolean>();

  private static void log(final Log.Record record) {
    final String logLevel = record.getLevel().toString();
    final String sourceClassSimpleName = record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf('.') + 1);
    final int callerLength = sourceClassSimpleName.length() + record.getSourceMethodName().length();
    final int padLevel = record.getMaxLevelLength() - logLevel.length();
    final int padCaller = record.getMaxCallerLength() - callerLength;
    final String logStatus = record.getStatus() != null ? " [" + record.getStatus() + "]" : "";
//    final int headerLength = 1 + logLevel.length() + 2 + padLevel + sourceClassSimpleName.length() + 1 + log.getSourceMethodName().length() + 2 + padCaller + logStatus.length() + 1;
    final int headerLength = 1 + logLevel.length() + 2;
    final String newlinePad = createRepeat(' ', headerLength);

    final String message = createRepeat(' ', padLevel) + sourceClassSimpleName + "." + record.getSourceMethodName() + "()" + createRepeat(' ', padCaller) + logStatus + (record.getMessage() != null ? " " + (record.getMessage().contains("\n") ? ("\n" + record.getMessage()).replace("\n", "\n" + newlinePad) : record.getMessage()) : "");
    Log.log(record.getLevel(), message);
  }

  private static String createRepeat(final char ch, final int length) {
    final char[] chars = new char[length];
    Arrays.fill(chars, ch);
    return String.valueOf(chars);
  }

  @AfterClass
  public static final void flushLogs() {
    int maxLevelLength = 0;
    int maxCallerLength = 0;
    for (final Log.Record logRecord : logRecords) {
      final int levelLength = logRecord.getLevel().toString().length();
      if (maxLevelLength < levelLength)
        maxLevelLength = levelLength;

      final int callerLength = logRecord.getSourceClassName().substring(logRecord.getSourceClassName().lastIndexOf('.') + 1).length() + logRecord.getSourceMethodName().length();
      if (maxCallerLength < callerLength)
        maxCallerLength = callerLength;
    }

    while (logRecords.size() > 0) {
      final Log.Record logRecord = logRecords.remove(0);
      logRecord.setMaxLevelLength(maxLevelLength);
      logRecord.setMaxCallerLength(maxCallerLength);
      log(logRecord);
    }
  }

  private String methodName;
  private String className;

  @Rule
  public final TestRule logFlushRule = new TestRule() {
    @Override
    public Statement apply(final Statement base, final Description description) {
      methodName = description.getMethodName();
      className = description.getClassName();
      return base;
    }
  };

  @Rule
  public final TestWatcher watchman = new TestWatcher() {
    @Override
    protected void succeeded(final Description description) {
      if (!"init".equals(description.getMethodName()))
        logRecords.add(new Log.Record(Log.Level.INFO, null, Log.Status.SUCCESS, description.getClassName(), description.getMethodName()));
    }

    @Override
    protected void failed(final Throwable e, final Description description) {
      logRecords.add(new Log.Record(Log.Level.ERROR, null, Log.Status.FAILURE, description.getClassName(), description.getMethodName()));
    }

    @Override
    protected void skipped(final AssumptionViolatedException e, final Description description) {
      logRecords.add(new Log.Record(Log.Level.WARNING, null, Log.Status.SKIPPED, description.getClassName(), description.getMethodName()));
    }
  };

  @Before
  public final void checkIgnored() {
    Boolean mutex = classToMutex.get(getClass());
    if (mutex == null)
      classToMutex.put(getClass(), mutex = false);
    else if (mutex)
      return;

    synchronized (mutex) {
      if (classToMutex.get(getClass()))
        return;

      final Method[] methods = getClass().getDeclaredMethods();
      for (final Method method : methods) {
        final Ignore ignore = method.getAnnotation(Ignore.class);
        if (ignore == null)
          continue;

        logRecords.add(new Log.Record(Log.Level.WARNING, ignore.value().length() != 0 ? ignore.value() : null, Log.Status.IGNORED, method.getDeclaringClass().getName(), method.getName()));
      }

      classToMutex.put(getClass(), true);
    }
  }

  // This is here to allow the @Before method to run, to get all @Ignore(d) tests and warn on them
  @Test
  public final void init() {
  }

  protected final void log(final Log.Level level, final Object message) {
    logRecords.add(new Log.Record(level, message != null ? message.toString() : null, Log.Status.RUNNING, className, methodName));
  }

  protected final void log(final Object message) {
    log(Log.Level.INFO, message);
  }

  protected final void logf(final Log.Level level, final String format, final Object ... args) {
    logRecords.add(new Log.Record(level, String.format(format, args), Log.Status.RUNNING, className, methodName));
  }

  protected final void logf(final String format, final Object ... args) {
    logf(Log.Level.INFO, format, args);
  }
}