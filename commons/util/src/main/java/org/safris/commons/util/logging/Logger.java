package org.safris.commons.util.logging;

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

public final class Logger
{
	private static final Map<String,Logger> instances = new HashMap<String,Logger>();

	public static Logger getLogger(String name)
	{
		Logger logger = instances.get(name);
		if(logger != null)
			return logger;

		synchronized(instances)
		{
			logger = instances.get(name);
			if(logger != null)
				return logger;

			logger = new Logger(name);
			instances.put(name, logger);
			return logger;
		}
	}

	private final Boolean lock = false;
	private java.util.logging.Logger logger = null;
	private final String name;

	private Logger(String name)
	{
		this.name = name;
	}

	public final java.util.logging.Logger logger()
	{
		if(logger != null)
			return logger;

		synchronized(lock)
		{
			if(logger != null)
				return logger;

			logger = new LoggerWrapper(java.util.logging.Logger.getLogger(name));
			reset(logger);
		}

		return logger;
	}

	private void reset(java.util.logging.Logger logger)
	{
		Formatter formatter = null;
		if(logger.getLevel() == null)
			formatter = new ConsoleFormatter();
		else
			formatter = new SimpleFormatter();

		for(Handler handler : logger.getParent().getHandlers())
			logger.getParent().removeHandler(handler);

		Handler handler = new ConsoleHandler();
		handler.setFormatter(formatter);

		logger.getParent().addHandler(handler);
	}

	private class LoggerWrapper extends java.util.logging.Logger
	{
		private final java.util.logging.Logger logger;

		protected LoggerWrapper(java.util.logging.Logger logger)
		{
			super(logger.getName(), logger.getResourceBundleName());
			this.logger = logger;
		}

		public ResourceBundle getResourceBundle()
		{
			return logger.getResourceBundle();
		}

		public String getResourceBundleName()
		{
			return logger.getResourceBundleName();
		}

		public void setFilter(Filter newFilter) throws SecurityException
		{
			logger.setFilter(newFilter);
		}

		public Filter getFilter()
		{
			return logger.getFilter();
		}

		public void log(LogRecord record)
		{
			logger.log(record);
		}

		public void log(Level level, String msg)
		{
			logger.log(level, msg);
		}

		public void log(Level level, String msg, Object param1)
		{
			logger.log(level, msg, param1);
		}

		public void log(Level level, String msg, Object[] params)
		{
			logger.log(level, msg, params);
		}

		public void log(Level level, String msg, Throwable thrown)
		{
			logger.log(level, msg, thrown);
		}

		public void logp(Level level, String sourceClass, String sourceMethod, String msg)
		{
			logger.logp(level, sourceClass, sourceMethod, msg);
		}

		public void logp(Level level, String sourceClass, String sourceMethod, String msg, Object param1)
		{
			logger.logp(level, sourceClass, sourceMethod, msg, param1);
		}

		public void logp(Level level, String sourceClass, String sourceMethod, String msg, Object[] params)
		{
			logger.logp(level, sourceClass, sourceMethod, msg, params);
		}

		public void logp(Level level, String sourceClass, String sourceMethod, String msg, Throwable thrown)
		{
			logger.logp(level, sourceClass, sourceMethod, msg, thrown);
		}

		public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg)
		{
			logger.logrb(level, sourceClass, sourceMethod, bundleName, msg);
		}

		public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object param1)
		{
			logger.logrb(level, sourceClass, sourceMethod, bundleName, msg, param1);
		}

		public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object[] params)
		{
			logger.logrb(level, sourceClass, sourceMethod, bundleName, msg, params);
		}

		public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Throwable thrown)
		{
			logger.logrb(level, sourceClass, sourceMethod, bundleName, msg, thrown);
		}

		public void entering(String sourceClass, String sourceMethod)
		{
			logger.entering(sourceClass, sourceMethod);
		}

		public void entering(String sourceClass, String sourceMethod, Object param1)
		{
			logger.entering(sourceClass, sourceMethod, param1);
		}

		public void entering(String sourceClass, String sourceMethod, Object[] params)
		{
			logger.entering(sourceClass, sourceMethod, params);
		}

		public void exiting(String sourceClass, String sourceMethod)
		{
			logger.exiting(sourceClass, sourceMethod);
		}

		public void exiting(String sourceClass, String sourceMethod, Object result)
		{
			logger.exiting(sourceClass, sourceMethod, result);
		}

		public void throwing(String sourceClass, String sourceMethod, Throwable thrown)
		{
			logger.throwing(sourceClass, sourceMethod, thrown);
		}

		public void severe(String msg)
		{
			logger.severe(msg);
		}

		public void warning(String msg)
		{
			logger.warning(msg);
		}

		public void info(String msg)
		{
			logger.info(msg);
		}

		public void config(String msg)
		{
			logger.config(msg);
		}

		public void fine(String msg)
		{
			logger.fine(msg);
		}

		public void finer(String msg)
		{
			logger.finer(msg);
		}

		public void finest(String msg)
		{
			logger.finest(msg);
		}

		public void setLevel(Level newLevel) throws SecurityException
		{
			logger.setLevel(newLevel);
			reset(logger);
		}

		public Level getLevel()
		{
			return logger.getLevel();
		}

		public boolean isLoggable(Level level)
		{
			return logger.isLoggable(level);
		}

		public String getName()
		{
			return logger.getName();
		}

		public void addHandler(Handler handler) throws SecurityException
		{
			logger.addHandler(handler);
		}

		public void removeHandler(Handler handler) throws SecurityException
		{
			logger.removeHandler(handler);
		}

		public Handler[] getHandlers()
		{
			return logger.getHandlers();
		}

		public void setUseParentHandlers(boolean useParentHandlers)
		{
			logger.setUseParentHandlers(useParentHandlers);
		}

		public boolean getUseParentHandlers()
		{
			return logger.getUseParentHandlers();
		}

		public java.util.logging.Logger getParent()
		{
			return logger.getParent();
		}

		public void setParent(java.util.logging.Logger parent)
		{
			logger.setParent(parent);
		}
	}
}
