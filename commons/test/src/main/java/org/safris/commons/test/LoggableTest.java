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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public abstract class LoggableTest {
  private static final Logger logger = LoggerFactory.getLogger(LoggableTest.class);

  private static final List<UnitLogRecord> logRecords = new LinkedList<UnitLogRecord>();
  private static final Map<Class<? extends LoggableTest>,Boolean> classToMutex = new HashMap<Class<? extends LoggableTest>,Boolean>();

  private static class UnitLogRecord {
    public static enum Status {
      FAIL, CALL, SKIP, PASS
    }

    protected final Level level;
    protected final String message;
    protected final Status status;
    protected final String sourceClassName;
    protected final String sourceMethodName;
    protected int maxLevelLength;

    public UnitLogRecord(final Level level, final String message, final Status status, final String sourceClassName, final String sourceMethodName) {
      this.level = level;
      this.message = message;
      this.status = status;
      this.sourceClassName = sourceClassName;
      this.sourceMethodName = sourceMethodName;
    }
  }

  private static void log(final UnitLogRecord record) {
    final String logLevel = record.level.toString();
    final int padLevel = record.maxLevelLength - logLevel.length();
    final String logStatus = record.status != null ? "[" + record.status + "] " + record.sourceMethodName + "()" : "[TEST]";
    final String message = createRepeat(' ', padLevel) + logStatus + (record.message == null ? "" : " " + (record.message.contains("\n") ? record.message.replace("\n", "\n[TEST] ") : record.message));

    if (record.level == Level.INFO)
      logger.info(message);
    else if (record.level == Level.DEBUG)
      logger.debug(message);
    else if (record.level == Level.TRACE)
      logger.trace(message);
    else if (record.level == Level.WARN)
      logger.warn(message);
    else if (record.level == Level.ERROR)
      logger.error(message);
    else
      throw new UnsupportedOperationException("Unexpected Level: " + record.level);
  }

  private static String createRepeat(final char ch, final int length) {
    if (length == 0)
      return "";

    final char[] chars = new char[length];
    Arrays.fill(chars, ch);
    return String.valueOf(chars);
  }

  @AfterClass
  public static final void flushLogs() {
    int maxLevelLength = 0;
    int maxCallerLength = 0;
    for (final UnitLogRecord logRecord : logRecords) {
      final int levelLength = logRecord.level.toString().length();
      if (maxLevelLength < levelLength)
        maxLevelLength = levelLength;

      final int callerLength = logRecord.sourceClassName.substring(logRecord.sourceClassName.lastIndexOf('.') + 1).length() + logRecord.sourceMethodName.length();
      if (maxCallerLength < callerLength)
        maxCallerLength = callerLength;
    }

    while (logRecords.size() > 0) {
      final UnitLogRecord logRecord = logRecords.remove(0);
      logRecord.maxLevelLength = maxLevelLength;
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
      if (!"_init".equals(description.getMethodName()))
        logRecords.add(new UnitLogRecord(Level.INFO, null, UnitLogRecord.Status.PASS, description.getClassName(), description.getMethodName()));
    }

    @Override
    protected void failed(final Throwable e, final Description description) {
      logRecords.add(new UnitLogRecord(Level.ERROR, null, UnitLogRecord.Status.FAIL, description.getClassName(), description.getMethodName()));
    }

    @Override
    protected void skipped(final AssumptionViolatedException e, final Description description) {
      logRecords.add(new UnitLogRecord(Level.WARN, null, UnitLogRecord.Status.SKIP, description.getClassName(), description.getMethodName()));
    }
  };

  @Before
  public final void _checkIgnored() {
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

        logRecords.add(new UnitLogRecord(Level.WARN, ignore.value().length() != 0 ? ignore.value() : null, UnitLogRecord.Status.SKIP, method.getDeclaringClass().getName(), method.getName()));
      }

      classToMutex.put(getClass(), true);
    }
  }

  // This is here to allow the @Before method to run, to get all @Ignore(d) tests and warn on them
  @Test
  public final void _init() {
  }

  protected final void log(final Level level, final Object message) {
    logRecords.add(new UnitLogRecord(level, String.valueOf(message), null, className, methodName));
  }

  protected final void log(final Object message) {
    log(Level.INFO, message);
  }

  protected final void logf(final Level level, final String format, final Object ... args) {
    logRecords.add(new UnitLogRecord(level, String.format(format, args), null, className, methodName));
  }

  protected final void logf(final String format, final Object ... args) {
    logf(Level.INFO, format, args);
  }
}