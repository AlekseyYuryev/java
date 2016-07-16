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

package org.safris.maven.common;

public final class Log {
  public static enum Level {
    DEBUG, INFO, WARNING, ERROR
  }

  public static enum Status {
    FAILURE, IGNORED, RUNNING, SKIPPED, SUCCESS
  }

  public static class Record {
    private final Level level;
    private final String message;
    private final Status status;
    private final String sourceClassName;
    private final String sourceMethodName;

    private int maxLevelLength;
    private int maxCallerLength;

    public Record(final Level level, final String message, final Status status, final String sourceClassName, final String sourceMethodName) {
      this.level = level;
      this.message = message;
      this.status = status;
      this.sourceClassName = sourceClassName;
      this.sourceMethodName = sourceMethodName;
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

    public Level getLevel() {
      return level;
    }

    public String getMessage() {
      return message;
    }

    public Status getStatus() {
      return status;
    }

    public String getSourceClassName() {
      return sourceClassName;
    }

    public String getSourceMethodName() {
      return sourceMethodName;
    }
  }

  public static void log(final Level level, final Object message) {
    log(level, message, null);
  }

  public static void log(final Level level, final Throwable error) {
    log(level, null, error);
  }

  public static void log(final Level level, final Object message, final Throwable error) {
    final String string = message != null ? message.toString() : "null";
    final AdvancedMojo mojo = AdvancedMojo.getMojo();
    if (mojo == null || mojo.getLog() == null)
      toStdErr(level, string, error); // FIXME: How do we determine if we are in "DEBUG" mode?
    else if (level == Level.ERROR)
      if (error != null)
        mojo.getLog().error(string, error);
      else
        mojo.getLog().error(string);
    else if (level == Level.WARNING)
      if (error != null)
        mojo.getLog().warn(string, error);
      else
        mojo.getLog().warn(string);
    else if (level == Level.INFO)
      if (error != null)
        mojo.getLog().info(string, error);
      else
        mojo.getLog().info(string);
    else if (mojo.getLog().isDebugEnabled())
      if (error != null)
        mojo.getLog().debug(string, error);
      else
        mojo.getLog().debug(string);
  }

  public static void debug(final Object message) {
    debug(message, null);
  }

  public static void debug(final Throwable error) {
    debug(null, error);
  }

  public static void debug(final Object message, final Throwable error) {
    log(Level.DEBUG, message, error);
  }

  public static void info(final Object message) {
    info(message, null);
  }

  public static void info(final Throwable error) {
    info(null, error);
  }

  public static void info(final Object message, final Throwable error) {
    log(Level.INFO, message, error);
  }

  public static void warn(final Object message) {
    warn(message, null);
  }

  public static void warn(final Throwable error) {
    warn(null, error);
  }

  public static void warn(final Object message, final Throwable error) {
    log(Level.WARNING, message, error);
  }

  public static void error(final Object message) {
    error(message, null);
  }

  public static void error(final Throwable error) {
    error(null, error);
  }

  public static void error(final Object message, final Throwable error) {
    log(Level.ERROR, message, error);
  }

  private static void toStdErr(final Level level, final Object message, final Throwable error) {
    System.err.println("[" + level + "] " + (message != null ? message : ""));
    if (error != null)
      error.printStackTrace();
  }

  private Log() {
  }
}