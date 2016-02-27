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
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

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

public abstract class LoggableTest {
  private static final Logger logger = Logger.getLogger(LoggableTest.class.getName());
  private static final List<CustomLogRecord> logRecords = new LinkedList<CustomLogRecord>();
  private static final Map<Class<? extends LoggableTest>,Boolean> classToMutex = new HashMap<Class<? extends LoggableTest>,Boolean>();

  static {
    if (logger.getLevel() == null)
      logger.setLevel(Level.INFO);

    final ConsoleHandler handler = new ConsoleHandler();
    handler.setFormatter(new Formatter() {
      @Override
      public String format(final LogRecord record) {
        final CustomLogRecord log = (CustomLogRecord)record;
        final String logLevel = log.getLevel().toString();
        final String sourceClassSimpleName = log.getSourceClassName().substring(log.getSourceClassName().lastIndexOf('.') + 1);
        final int callerLength = sourceClassSimpleName.length() + log.getSourceMethodName().length();
        final int padLevel = log.getMaxLevelLength() - logLevel.length();
        final int padCaller = log.getMaxCallerLength() - callerLength;
        final String logStatus = log.status != null ? " [" + log.status + "]" : "";
//        final int headerLength = 1 + logLevel.length() + 2 + padLevel + sourceClassSimpleName.length() + 1 + log.getSourceMethodName().length() + 2 + padCaller + logStatus.length() + 1;
        final int headerLength = 1 + logLevel.length() + 2;
        final String newlinePad = createRepeat(' ', headerLength);

        return "[" + logLevel + "] " + createRepeat(' ', padLevel) + sourceClassSimpleName + "." + log.getSourceMethodName() + "()" + createRepeat(' ', padCaller) + logStatus + (log.getMessage() != null ? " " + (log.getMessage().contains("\n") ? ("\n" + log.getMessage()).replace("\n", "\n" + newlinePad) : log.getMessage()) : "") + "\n";
      }
    });

    logger.setUseParentHandlers(false);
    logger.addHandler(handler);
  }

  private static class CustomLogRecord extends LogRecord {
    private static final long serialVersionUID = 2537795956151950685L;

    public final String status;
    private int maxLevelLength;
    private int maxCallerLength;

    public CustomLogRecord(final Level level, final String msg, final String status) {
      super(level, msg);
      this.status = status;
    }

    public int getMaxLevelLength() {
      return maxLevelLength;
    }

    public void setMaxLevelLength(final int maxLevelLength) {
      this.maxLevelLength = maxLevelLength;
    }

    public int getMaxCallerLength() {
      return maxCallerLength;
    }

    public void setMaxCallerLength(final int maxCallerLength) {
      this.maxCallerLength = maxCallerLength;
    }
  }

  private static String createRepeat(final char ch, final int length) {
    if (length < 0)
      throw new IllegalArgumentException("length = " + length + " < 0");

    final char[] chars = new char[length];
    Arrays.fill(chars, ch);
    return String.valueOf(chars);
  }

  @AfterClass
  public static final void flushLogs() {
    int maxLevelLength = 0;
    int maxCallerLength = 0;
    for (final CustomLogRecord logRecord : logRecords) {
      final int levelLength = logRecord.getLevel().toString().length();
      if (maxLevelLength < levelLength)
        maxLevelLength = levelLength;

      final int callerLength = logRecord.getSourceClassName().substring(logRecord.getSourceClassName().lastIndexOf('.') + 1).length() + logRecord.getSourceMethodName().length();
      if (maxCallerLength < callerLength)
        maxCallerLength = callerLength;
    }

    while (logRecords.size() > 0) {
      final CustomLogRecord logRecord = logRecords.remove(0);
      logRecord.setMaxLevelLength(maxLevelLength);
      logRecord.setMaxCallerLength(maxCallerLength);
      logger.log(logRecord);
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
      if ("init".equals(description.getMethodName()))
        return;

      final CustomLogRecord logRecord = new CustomLogRecord(logger.getLevel(), null, "SUCCESS");
      logRecord.setSourceMethodName(description.getMethodName());
      logRecord.setSourceClassName(description.getClassName());
      logRecords.add(logRecord);
    }

    @Override
    protected void failed(final Throwable e, final Description description) {
      final CustomLogRecord logRecord = new CustomLogRecord(Level.SEVERE, null, "FAILURE");
      logRecord.setSourceMethodName(description.getMethodName());
      logRecord.setSourceClassName(description.getClassName());
      logRecords.add(logRecord);
    }

    @Override
    protected void skipped(final AssumptionViolatedException e, final Description description) {
      final CustomLogRecord logRecord = new CustomLogRecord(Level.WARNING, null, "SKIPPED");
      logRecord.setSourceMethodName(description.getMethodName());
      logRecord.setSourceClassName(description.getClassName());
      logRecords.add(logRecord);
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

        final CustomLogRecord logRecord = new CustomLogRecord(Level.WARNING, ignore.value().length() != 0 ? ignore.value() : null, "IGNORED");
        logRecord.setSourceMethodName(method.getName());
        logRecord.setSourceClassName(method.getDeclaringClass().getName());
        logRecords.add(logRecord);
      }

      classToMutex.put(getClass(), true);
    }
  }

  // This is here to allow the @Before method to run, to get all @Ignore(d) tests and warn on them
  @Test
  public final void init() {
  }

  protected final void log(final Level level, final Object message) {
    final CustomLogRecord logRecord = new CustomLogRecord(level, message != null ? message.toString() : null, "RUNNING");
    logRecord.setSourceMethodName(methodName);
    logRecord.setSourceClassName(className);
    logRecords.add(logRecord);
  }

  protected final void log(final Object message) {
    log(logger.getLevel(), message);
  }

  protected final void logf(final Level level, final String format, final Object ... args) {
    final CustomLogRecord logRecord = new CustomLogRecord(level, String.format(format, args), "RUNNING");
    logRecord.setSourceMethodName(methodName);
    logRecord.setSourceClassName(className);
    logRecords.add(logRecord);
  }

  protected final void logf(final String format, final Object ... args) {
    logf(logger.getLevel(), format, args);
  }
}