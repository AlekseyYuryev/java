/* Copyright (c) 2006 Seva Safris
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

package org.safris.commons.logging;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public final class Logger extends java.util.logging.Logger {
  public static final Logger global = new Logger(java.util.logging.Logger.getGlobal());
  private static final Map<String,Logger> instances = new HashMap<String,Logger>();

  public static Logger getLogger(final LoggerName name) {
    return getLogger(java.util.logging.Logger.getLogger(name.getName()));
  }

  public static Logger getLogger(final LoggerName name, final String resourceBundleName) {
    return getLogger(java.util.logging.Logger.getLogger(name.getName(), resourceBundleName));
  }

  public static Logger getLogger(final String name) {
    return getLogger(java.util.logging.Logger.getLogger(name));
  }

  public static Logger getAnonymousLogger() {
    return getLogger(java.util.logging.Logger.getAnonymousLogger());
  }

  public static Logger getAnonymousLogger(final String resourceBundleName) {
    return getLogger(java.util.logging.Logger.getAnonymousLogger(resourceBundleName));
  }

  private static Logger getLogger(final java.util.logging.Logger logger) {
    Logger instance = instances.get(logger.getName());
    if (instance != null)
      return instance;

    synchronized (instances) {
      instance = instances.get(logger.getName());
      if (instance != null)
        return instance;

      instance = new Logger(logger);
      instances.put(logger.getName(), instance);
      return instance;
    }
  }

  private final java.util.logging.Logger logger;

  private Logger(final java.util.logging.Logger logger) {
    super(logger.getName(), logger.getResourceBundleName());
    this.logger = logger;
    reset(logger);
  }

  private void reset(final java.util.logging.Logger logger) {
    final Formatter formatter;
    if (logger.getLevel() == null)
      formatter = new ConsoleFormatter();
    else
      formatter = new SimpleFormatter();

    java.util.logging.Logger parent = logger.getParent();
    if (parent == null)
      parent = logger;

    for (final Handler handler : parent.getHandlers())
      parent.removeHandler(handler);

    final Handler handler = new ConsoleHandler();
    handler.setFormatter(formatter);
    handler.setLevel(Level.ALL);
    parent.addHandler(handler);
  }

  public ResourceBundle getResourceBundle() {
    return logger.getResourceBundle();
  }

  public String getResourceBundleName() {
    return logger.getResourceBundleName();
  }

  public void setFilter(final Filter newFilter) throws SecurityException {
    logger.setFilter(newFilter);
  }

  public Filter getFilter() {
    return logger.getFilter();
  }

  public void log(final LogRecord record) {
    logger.log(record);
  }

  public void log(final Level level, final String msg) {
    logger.log(level, msg);
  }

  public void log(final Level level, final String msg, final Object param1) {
    logger.log(level, msg, param1);
  }

  public void log(final Level level, final String msg, final Object[] params) {
    logger.log(level, msg, params);
  }

  public void log(final Level level, final String msg, final Throwable thrown) {
    logger.log(level, msg, thrown);
  }

  public void logp(final Level level, final String sourceClass, String sourceMethod, final String msg) {
    logger.logp(level, sourceClass, sourceMethod, msg);
  }

  public void logp(final Level level, final String sourceClass, String sourceMethod, final String msg, final Object param1) {
    logger.logp(level, sourceClass, sourceMethod, msg, param1);
  }

  public void logp(final Level level, final String sourceClass, String sourceMethod, final String msg, final Object[] params) {
    logger.logp(level, sourceClass, sourceMethod, msg, params);
  }

  public void logp(final Level level, final String sourceClass, String sourceMethod, final String msg, final Throwable thrown) {
    logger.logp(level, sourceClass, sourceMethod, msg, thrown);
  }

  public void logrb(final Level level, final String sourceClass, String sourceMethod, final String bundleName, final String msg) {
    logger.logrb(level, sourceClass, sourceMethod, bundleName, msg);
  }

  public void logrb(final Level level, final String sourceClass, String sourceMethod, final String bundleName, String msg, final Object param1) {
    logger.logrb(level, sourceClass, sourceMethod, bundleName, msg, param1);
  }

  public void logrb(final Level level, final String sourceClass, String sourceMethod, final String bundleName, String msg, final Object[] params) {
    logger.logrb(level, sourceClass, sourceMethod, bundleName, msg, params);
  }

  public void logrb(final Level level, final String sourceClass, String sourceMethod, final String bundleName, String msg, final Throwable thrown) {
    logger.logrb(level, sourceClass, sourceMethod, bundleName, msg, thrown);
  }

  public void entering(final String sourceClass, final String sourceMethod) {
    logger.entering(sourceClass, sourceMethod);
  }

  public void entering(final String sourceClass, final String sourceMethod, final Object param1) {
    logger.entering(sourceClass, sourceMethod, param1);
  }

  public void entering(final String sourceClass, final String sourceMethod, final Object[] params) {
    logger.entering(sourceClass, sourceMethod, params);
  }

  public void exiting(final String sourceClass, final String sourceMethod) {
    logger.exiting(sourceClass, sourceMethod);
  }

  public void exiting(final String sourceClass, final String sourceMethod, final Object result) {
    logger.exiting(sourceClass, sourceMethod, result);
  }

  public void throwing(final String sourceClass, final String sourceMethod, final Throwable thrown) {
    logger.throwing(sourceClass, sourceMethod, thrown);
  }

  public void severe(final String msg) {
    logger.severe(msg);
  }

  public void warning(final String msg) {
    logger.warning(msg);
  }

  public void info(final String msg) {
    logger.info(msg);
  }

  public void config(final String msg) {
    logger.config(msg);
  }

  public void fine(final String msg) {
    logger.fine(msg);
  }

  public void finer(final String msg) {
    logger.finer(msg);
  }

  public void finest(final String msg) {
    logger.finest(msg);
  }

  public void setLevel(final Level newLevel) throws SecurityException {
    logger.setLevel(newLevel);
    reset(logger);
  }

  public Level getLevel() {
    return logger.getLevel();
  }

  public boolean isLoggable(final Level level) {
    return logger.isLoggable(level);
  }

  public String getName() {
    return logger.getName();
  }

  public void addHandler(final Handler handler) throws SecurityException {
    logger.addHandler(handler);
  }

  public void removeHandler(final Handler handler) throws SecurityException {
    logger.removeHandler(handler);
  }

  public Handler[] getHandlers() {
    return logger.getHandlers();
  }

  public void setUseParentHandlers(final boolean useParentHandlers) {
    logger.setUseParentHandlers(useParentHandlers);
  }

  public boolean getUseParentHandlers() {
    return logger.getUseParentHandlers();
  }

  public java.util.logging.Logger getParent() {
    return logger.getParent();
  }

  public void setParent(final java.util.logging.Logger parent) {
    logger.setParent(parent);
  }
}