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

package org.safris.maven.plugin.xml.binding;

import java.io.File;
import java.util.StringTokenizer;

import org.codehaus.classworlds.Launcher;

public final class MavenLauncher {
  public static void main(final String[] args) {
    final String M2_HOME = System.getenv("M2_HOME");
    if (M2_HOME == null)
      throw new RuntimeException("M2_HOME is not defined.");

    final File m2 = new File(M2_HOME, "bin/m2.conf");
    if (!m2.exists()) {
      System.err.println("Cannot find $M2_HOME/bin/m2.conf: " + m2.getAbsolutePath());
      System.exit(1);
    }

    final String MAVEN_OPTS = System.getenv("MAVEN_OPTS");
    if (MAVEN_OPTS != null) {
      final StringTokenizer tokenizer = new StringTokenizer(MAVEN_OPTS, "-D");
      while (tokenizer.hasMoreTokens()) {
        final String token = tokenizer.nextToken();
        if (token == null)
          continue;

        final String[] values = token.split("=");
        if (values.length == 2)
          System.setProperty(values[0], values[1]);
      }
    }

    System.setProperty("maven.home", M2_HOME);
    System.setProperty("classworlds.conf", m2.getAbsolutePath());
    Launcher.main(args);
  }

  private MavenLauncher() {
  }
}