/* Copyright (c) 2017 lib4j
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

package org.libx4j.maven.mojo;

import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.plugin.MojoExecution;

public final class MojoUtil {
  public static PluginExecution getPluginExecution(final MojoExecution mojoExecution) {
    final Plugin plugin = mojoExecution.getPlugin();
    for (final PluginExecution pluginExecution : plugin.getExecutions())
      if (pluginExecution.getId().equals(mojoExecution.getExecutionId()))
        return pluginExecution;

    return null;
  }

  public static boolean shouldSkip(final MojoExecution mojoExecution, final boolean mavenTestSkip) {
    if (mavenTestSkip && mojoExecution.getLifecyclePhase().contains("test"))
      return true;

    final Plugin plugin = mojoExecution.getPlugin();
    plugin.flushExecutionMap();
    return mavenTestSkip && getPluginExecution(mojoExecution).getPhase().contains("test");
  }

  private MojoUtil() {
  }
}