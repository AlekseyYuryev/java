/* Copyright (c) 2016 lib4j
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

package org.lib4j.logging;

import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Logging {
  public static void setLevel(final Level rootLevel) {
    setLevel(rootLevel, null);
  }

  public static void setLevel(final Level rootLevel, final Map<String,Level> levels) {
    final Logger rootLogger = Logger.getLogger("");
    rootLogger.setLevel(rootLevel);
    for (final Handler handler : rootLogger.getHandlers())
      handler.setLevel(rootLevel);

    if (levels != null)
      for (final Map.Entry<String,Level> entry : levels.entrySet())
        Logger.getLogger(entry.getKey()).setLevel(entry.getValue());
  }

  private Logging() {
  }
}