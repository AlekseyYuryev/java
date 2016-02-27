package org.safris.commons.test;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

@RunWith(LoggableRunner.class)
public class LoggableTest {
  private static final Logger logger = Logger.getLogger(LoggableTest.class.getName());

  static {
    if (logger.getLevel() == null)
      logger.setLevel(TestRuntime.isInIDE() ? Level.INFO : Level.FINE);

    final ConsoleHandler handler = new ConsoleHandler();
    handler.setFormatter(new Formatter() {
      @Override
      public String format(final LogRecord record) {
        return "[" + record.getLevel() + "] " + record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf('.') + 1) + "." + record.getSourceMethodName() + "(): " + record.getMessage() + "\n";
      }
    });

    logger.setUseParentHandlers(false);
    logger.addHandler(handler);
  }

  private final List<String> logs = new LinkedList<String>();

  protected final void log(final String message) {
    logs.add(message);
  }

  @Rule
  private TestWatcher watchman = new TestWatcher() {
    @Override
    protected void failed(final Throwable e, final Description description) {
      if (logs.size() == 0)
        return;

      synchronized (logs) {
        for (int i = 0; i < logs.size(); i++)
          logger.log(Level.SEVERE, logs.remove(i));
      }
    }

    @Override
    protected void succeeded(final Description description) {
    }
  };

  private String methodName;
  private String className;

  @Rule
  private TestRule logFlushRule = new TestRule() {
    @Override
    public Statement apply(final Statement base, final Description description) {
      methodName = description.getMethodName();
      className = description.getClassName();
      return base;
    }
  };

  @After
  private void flushLog() {
    if (logs.size() == 0)
      return;

    synchronized (logs) {
      for (int i = 0; i < logs.size(); i++) {
        final LogRecord logRecord = new LogRecord(logger.getLevel(), logs.remove(i));
        logRecord.setSourceMethodName(methodName);
        logRecord.setSourceClassName(className);
        logger.log(logRecord);
      }
    }
  }
}