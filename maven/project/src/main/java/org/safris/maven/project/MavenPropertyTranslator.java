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

package org.safris.maven.project;

import org.apache.maven.project.MavenProject;
import org.safris.commons.util.Translator;

public class MavenPropertyTranslator implements Translator<String> {
  private final MavenProject project;

  public MavenPropertyTranslator(final MavenProject project) {
    this.project = project;
  }

  @Override
  public String translate(final String string) {
    if (string == null)
      return null;

    final int start = string.indexOf("${");
    if (start == -1)
      return string;

    final int end = string.indexOf("}", start + 2);
    if (end == -1)
      return string;

    final String translated = getProperty(string.substring(start + 2, end));
    if (translated == null)
      return string.substring(0, end + 1) + translate(string.substring(end + 1, string.length()));

    return string.substring(0, start) + translated + translate(string.substring(end + 1, string.length()));
  }

  private String getProperty(final String string) {
    if ("basedir".equals(string))
      return project.getBasedir().getAbsolutePath();

    if ("project.build.directory".equals(string))
      return project.getBuild().getDirectory();

    if ("project.build.outputDirectory".equals(string))
      return project.getBuild().getOutputDirectory();

    if ("project.build.sourceDirectory".equals(string))
      return project.getBuild().getSourceDirectory();

    if ("project.build.testSourceDirectory".equals(string))
      return project.getBuild().getTestSourceDirectory();

    return null;
  }
}