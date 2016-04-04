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

package org.safris.commons.logging;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.safris.commons.logging.xe.$lg_setting;

public final class Logging {
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