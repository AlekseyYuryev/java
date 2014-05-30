/*  Copyright Safris Software 2009
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import org.safris.commons.el.ELs;
import org.safris.commons.exec.Processes;
import org.safris.commons.io.Streams;
import org.safris.commons.io.file.FileEventListener;
import org.safris.commons.io.file.FileMonitor;
import org.safris.commons.lang.Resource;
import org.safris.commons.lang.Resources;
import org.safris.commons.test.TestRuntime;

public final class ConfigurableLogger implements FileEventListener {
  private static final Formatter formatter = new Formatter() {
    public String format(final LogRecord record) {
      return new SimpleDateFormat("yyMMdd HH:mm:ss").format(new Date()) + " " + record.getLevel().toString().toUpperCase() + ": " + record.getMessage() + "\n";
    }
  };

  private static String applicationName = null;
  private static final List<LogRecord> constructRecord = new ArrayList<LogRecord>();
  private static final String loggerName = "";
  private static ConfigurableLogger logger = null;

  static {
    final Class<?> executedClass = Processes.getExecutedClass();
    applicationName = executedClass != null ? executedClass.getSimpleName() : null;
  }

  public static java.util.logging.Logger getLogger() {
    if (logger != null)
      return logger.getWrappedLogger();

    synchronized (constructRecord) {
      if (logger != null)
        return logger.getWrappedLogger();

      logger = new ConfigurableLogger();
      for (final LogRecord record : constructRecord)
        ConfigurableLogger.getLogger().log(record);
    }

    return logger.getWrappedLogger();
  }

  private final java.util.logging.Logger wrappedLogger = java.util.logging.Logger.getLogger(loggerName);

  protected ConfigurableLogger() {
    URL url = null;
    try {
      if (TestRuntime.isInTest() || TestRuntime.isInIDE()) {
        File loggingPropertiesFile = new File("src/test/resources/logging.properties");
        if (!loggingPropertiesFile.exists())
          loggingPropertiesFile = new File("../commons/src/test/resources/logging.properties");

        if (!loggingPropertiesFile.exists())
          loggingPropertiesFile = new File("../../commons/src/test/resources/logging.properties");

        if (!loggingPropertiesFile.exists())
          loggingPropertiesFile = new File("../../../commons/src/test/resources/logging.properties");

        if (loggingPropertiesFile.exists())
          url = loggingPropertiesFile.toURI().toURL();

        if (url == null)
          constructRecord.add(new LogRecord(Level.WARNING, "Could not find test logging.properties"));

        constructRecord.add(new LogRecord(Level.FINE, "Loading test logging.properties"));
      }
      else {
        final Resource resource = Resources.getResource("logging.properties");
        url = resource != null ? resource.getURL() : null;
        constructRecord.add(new LogRecord(Level.FINE, "Loading main logging.properties"));
      }

      if (url == null) {
        constructRecord.add(new LogRecord(Level.WARNING, "Unable to load logging.properties"));
        return;
      }

      File file = null;
      try {
        file = new File(url.toURI());
      }
      catch (final URISyntaxException e) {
      }

      if (file != null && file.exists()) {
        final FileMonitor fileMonitor = new FileMonitor(file, 5000);
        fileMonitor.addListener(this);
        fileMonitor.start();
      }
      else {
        constructRecord.add(new LogRecord(Level.WARNING, "Starting logger without FileMonitor: " + url));
      }

      final InputStream in = url.openStream();
      final String loggingProperties = new String(Streams.getBytes(in));
      in.close();
      initConfig(loggingProperties);
    }
    catch (final Exception e) {
      constructRecord.add(new LogRecord(Level.WARNING, "Unable to load logging.properties: " + e.getMessage()));
    }
  }

  public void onModify(final File file) {
    try {
      final InputStream in = file.toURI().toURL().openStream();
      final String loggingProperties = new String(Streams.getBytes(in));
      in.close();
      initConfig(loggingProperties);
    }
    catch (final IOException e) {
      ConfigurableLogger.getLogger().warning("Unable to absorb " + file.getName() + " changes due to: " + e.getMessage());
    }
  }

  public void onDelete(final File file) {
    ConfigurableLogger.getLogger().warning(file.getName() + " has been deleted.");
  }

  private void initConfig(String loggingProperties) {
    try {
      synchronized (wrappedLogger) {
        final Map<String,String> env = new HashMap<String,String>(System.getenv());
        env.put("APPLICATION_NAME", applicationName != null ? applicationName : "web");

        //env.put("DATE", DateFormat.format(new Date()));
        env.put("PID", String.valueOf(Processes.getPID()));
        loggingProperties = ELs.dereference(loggingProperties, env);
        LogManager.getLogManager().readConfiguration(new ByteArrayInputStream(loggingProperties.getBytes()));
        final Enumeration<String> loggerNames = LogManager.getLogManager().getLoggerNames();
        while (loggerNames.hasMoreElements()) {
          final String loggerName = loggerNames.nextElement();
          if (!ConfigurableLogger.loggerName.equals(loggerName))
            continue;

          final java.util.logging.Logger logger = LogManager.getLogManager().getLogger(loggerName);
          final Handler[] handlers = logger.getHandlers();
          for (final Handler handler : handlers) {
            handler.setFormatter(formatter);
            if (!(handler instanceof FileHandler))
              continue;

            final FileHandler fileHandler = (FileHandler)handler;
            final Field lockFileNameField = fileHandler.getClass().getDeclaredField("lockFileName");
            lockFileNameField.setAccessible(true);
            final String lockFileName = (String)lockFileNameField.get(fileHandler);
            final File lockFile = new File(lockFileName);
            final File lockFileDir = lockFile.getParentFile();
            if (lockFileDir != null && !lockFileDir.exists())
              lockFileDir.mkdirs();

            lockFile.delete();
          }
        }
      }
    }
    catch (final Exception e) {
      constructRecord.add(new LogRecord(Level.WARNING, "Unable to load logging.properties: " + e.getMessage()));
    }
  }

  private java.util.logging.Logger getWrappedLogger() {
    return wrappedLogger;
  }
}