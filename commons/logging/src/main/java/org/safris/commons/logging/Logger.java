/*  Copyright Safris Software 2006
 *
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
  public static final Logger global = new Logger(ConfigurableLogger.getLogger().global);
  private static final Map<String,Logger> instances = new HashMap<String,Logger>();

  public static Logger getLogger(LoggerName name) {
    return getLogger(ConfigurableLogger.getLogger().getLogger(name.getName()));
  }

  public static Logger getLogger(LoggerName name, String resourceBundleName) {
    return getLogger(ConfigurableLogger.getLogger().getLogger(name.getName(), resourceBundleName));
  }

  public static Logger getAnonymousLogger() {
    return getLogger(ConfigurableLogger.getLogger().getAnonymousLogger());
  }

  public static Logger getAnonymousLogger(String resourceBundleName) {
    return getLogger(ConfigurableLogger.getLogger().getAnonymousLogger(resourceBundleName));
  }

  private static Logger getLogger(java.util.logging.Logger logger) {
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

  private Logger(java.util.logging.Logger logger) {
    super(logger.getName(), logger.getResourceBundleName());
    this.logger = logger;
    reset(logger);
  }

  private void reset(java.util.logging.Logger logger) {
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
    parent.addHandler(handler);
  }

  public ResourceBundle getResourceBundle() {
    return logger.getResourceBundle();
  }

  public String getResourceBundleName() {
    return logger.getResourceBundleName();
  }

  public void setFilter(Filter newFilter) throws SecurityException {
    logger.setFilter(newFilter);
  }

  public Filter getFilter() {
    return logger.getFilter();
  }

  public void log(LogRecord record) {
    logger.log(record);
  }

  public void log(Level level, String msg) {
    logger.log(level, msg);
  }

  public void log(Level level, String msg, Object param1) {
    logger.log(level, msg, param1);
  }

  public void log(Level level, String msg, Object[] params) {
    logger.log(level, msg, params);
  }

  public void log(Level level, String msg, Throwable thrown) {
    logger.log(level, msg, thrown);
  }

  public void logp(Level level, String sourceClass, String sourceMethod, String msg) {
    logger.logp(level, sourceClass, sourceMethod, msg);
  }

  public void logp(Level level, String sourceClass, String sourceMethod, String msg, Object param1) {
    logger.logp(level, sourceClass, sourceMethod, msg, param1);
  }

  public void logp(Level level, String sourceClass, String sourceMethod, String msg, Object[] params) {
    logger.logp(level, sourceClass, sourceMethod, msg, params);
  }

  public void logp(Level level, String sourceClass, String sourceMethod, String msg, Throwable thrown) {
    logger.logp(level, sourceClass, sourceMethod, msg, thrown);
  }

  public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg) {
    logger.logrb(level, sourceClass, sourceMethod, bundleName, msg);
  }

  public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object param1) {
    logger.logrb(level, sourceClass, sourceMethod, bundleName, msg, param1);
  }

  public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object[] params) {
    logger.logrb(level, sourceClass, sourceMethod, bundleName, msg, params);
  }

  public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Throwable thrown) {
    logger.logrb(level, sourceClass, sourceMethod, bundleName, msg, thrown);
  }

  public void entering(String sourceClass, String sourceMethod) {
    logger.entering(sourceClass, sourceMethod);
  }

  public void entering(String sourceClass, String sourceMethod, Object param1) {
    logger.entering(sourceClass, sourceMethod, param1);
  }

  public void entering(String sourceClass, String sourceMethod, Object[] params) {
    logger.entering(sourceClass, sourceMethod, params);
  }

  public void exiting(String sourceClass, String sourceMethod) {
    logger.exiting(sourceClass, sourceMethod);
  }

  public void exiting(String sourceClass, String sourceMethod, Object result) {
    logger.exiting(sourceClass, sourceMethod, result);
  }

  public void throwing(String sourceClass, String sourceMethod, Throwable thrown) {
    logger.throwing(sourceClass, sourceMethod, thrown);
  }

  public void severe(String msg) {
    logger.severe(msg);
  }

  public void warning(String msg) {
    logger.warning(msg);
  }

  public void info(String msg) {
    logger.info(msg);
  }

  public void config(String msg) {
    logger.config(msg);
  }

  public void fine(String msg) {
    logger.fine(msg);
  }

  public void finer(String msg) {
    logger.finer(msg);
  }

  public void finest(String msg) {
    logger.finest(msg);
  }

  public void setLevel(Level newLevel) throws SecurityException {
    logger.setLevel(newLevel);
    reset(logger);
  }

  public Level getLevel() {
    return logger.getLevel();
  }

  public boolean isLoggable(Level level) {
    return logger.isLoggable(level);
  }

  public String getName() {
    return logger.getName();
  }

  public void addHandler(Handler handler) throws SecurityException {
    logger.addHandler(handler);
  }

  public void removeHandler(Handler handler) throws SecurityException {
    logger.removeHandler(handler);
  }

  public Handler[] getHandlers() {
    return logger.getHandlers();
  }

  public void setUseParentHandlers(boolean useParentHandlers) {
    logger.setUseParentHandlers(useParentHandlers);
  }

  public boolean getUseParentHandlers() {
    return logger.getUseParentHandlers();
  }

  public java.util.logging.Logger getParent() {
    return logger.getParent();
  }

  public void setParent(java.util.logging.Logger parent) {
    logger.setParent(parent);
  }
}
