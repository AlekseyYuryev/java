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

import org.apache.maven.project.MavenProject;
import org.safris.commons.maven.AdvancedMojo;

public abstract class CoberturaMojo extends AdvancedMojo {
  /**
   * @parameter expression="${project}"
   * @readonly
   * @required
   */
  private MavenProject project;

  protected MavenProject getProject() {
    return project;
  }

  /**
   * @parameter default-value="${maven.test.skip}"
   * @readonly
   * @required
   */
  private Boolean mavenTestSkip = null;

  protected Boolean getMavenTestSkip() {
    return mavenTestSkip;
  }

  /**
   * @parameter default-value="${basedir}"
   * @readonly
   * @required
   */
  private String basedir = null;

  protected String getBasedir() {
    return basedir;
  }

  /**
   * @parameter default-value="${project.build.directory}"
   * @readonly
   * @required
   */
  private String directory = null;

  protected String getDirectory() {
    return directory;
  }

  /**
   * @parameter default-value="${project.build.sourceDirectory}"
   * @readonly
   * @required
   */
  private String sourceDirectory = null;

  protected String getSourceDirectory() {
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