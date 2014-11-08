/* Copyright (c) 2014 Seva Safris
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

package org.safris.maven.plugin.codegen;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.safris.commons.search.ISTEnumGenerator;

/**
 * @goal istenum
 */
public final class ISTEnumGeneratorMojo extends AbstractMojo {
  /**
   * @parameter default-value="${project}"
   * @readonly
   * @required
   */
  public MavenProject project;

  /**
   * @parameter default-value="${mojoExecution}"
   * @readonly
   * @required
   */
  private MojoExecution execution;

  /**
   * @parameter default-value="${maven.test.skip}"
   */
  private Boolean mavenTestSkip;

  /**
   * @parameter default-value="${file}"
   */
  private String file;

  /**
   * @parameter default-value="${dir}"
   */
  private String dir;

  /**
   * @parameter default-value="${className}"
   */
  private String className;

  /**
   * @parameter default-value="${inheritsFrom}"
   */
  private String inheritsFrom;

  public void execute() throws MojoExecutionException, MojoFailureException {
    if (mavenTestSkip != null && mavenTestSkip && execution.getLifecyclePhase().contains("test"))
      return;

    try {
      ISTEnumGenerator.generate(className, inheritsFrom, new File(dir, className.replace('.', '/') + ".java"), new File(file));
    }
    catch (final IOException e) {
      throw new MojoFailureException(e.getMessage(), e);
    }

    project.addTestCompileSourceRoot(dir);
    project.addCompileSourceRoot(dir);
  }
}