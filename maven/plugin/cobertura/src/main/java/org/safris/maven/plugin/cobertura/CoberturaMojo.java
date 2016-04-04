/* Copyright (c) 2008 Seva Safris
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

package org.safris.maven.plugin.cobertura;

import java.io.File;

import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.safris.commons.maven.AdvancedMojo;

public abstract class CoberturaMojo extends AdvancedMojo {
  @Parameter(defaultValue = "${project}", readonly = true)
  private MavenProject project;

  protected MavenProject getProject() {
    return project;
  }

  @Parameter(property = "maven.test.skip", defaultValue = "false")
  private boolean mavenTestSkip;

  protected boolean getMavenTestSkip() {
    return mavenTestSkip;
  }

  @Parameter(property = "basedir", readonly = true, required = true)
  private File basedir = null;

  protected File getBasedir() {
    return basedir;
  }

  @Parameter(property = "project.build.directory", readonly = true, required = true)
  private File directory = null;

  protected File getDirectory() {
    return directory;
  }

  @Parameter(property = "project.build.sourceDirectory", readonly = true, required = true)
  private File sourceDirectory = null;

  protected File getSourceDirectory() {
    return sourceDirectory;
  }

  private File dataFile = null;

  protected File getDataFile() {
    if (dataFile != null)
      return dataFile;

    return dataFile = new File(getCoberturaDir().getAbsoluteFile(), "cobertura.ser");
  }

  private File coberturaDir = null;

  protected File getCoberturaDir() {
    if (coberturaDir != null)
      return coberturaDir;

    return coberturaDir = new File(getDirectory(), "cobertura");
  }
}