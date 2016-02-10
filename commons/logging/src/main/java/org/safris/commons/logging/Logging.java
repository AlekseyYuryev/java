package org.safris.commons.logging;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging {
  public static void setLevel(final Level globalLevel, final List<$lg_setting> settings) {
    final Logger rootLogger = Logger.getLogger("");
    rootLogger.setLevel(globalLevel);
    for (final Handler handler : rootLogger.getHandlers())
      handler.setLevel(globalLevel);

    if (settings != null)
      for (final $lg_setting setting : settings)
        Logger.getLogger(setting._name$().text()).setLevel(Level.parse(setting._level$().text()));
  }

  private Logging() {
  }
}