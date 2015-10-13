/* Copyright (c) 2011 Seva Safris
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

package org.safris.maven.plugin.xdb;

import java.io.File;

import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.safris.xdb.xde.generator.EntityGenerator;
import org.safris.xdb.xdl.DBVendor;

/**
 * @goal xde
 * @phase generate-sources
 */
public final class XDEMojo extends XDLTransformerMojo {
  /**
   * @parameter default-value="${maven.test.skip}"
   */
  private Boolean mavenTestSkip = null;

  /**
   * @parameter expression="${mojoExecution}"
   */
  private MojoExecution execution;

  public void transform(final File xdlFile, final DBVendor vendor, final File outDir) throws MojoExecutionException, MojoFailureException {
    if (mavenTestSkip != null && mavenTestSkip && execution.getLifecyclePhase().contains("test"))
      return;

    try {
      EntityGenerator.generate(xdlFile.toURI().toURL(), outDir);
    }
    catch (final Exception e) {
      throw new MojoFailureException(e.getMessage(), e);
    }

    project.addTestCompileSourceRoot(outDir.getAbsolutePath());
    project.addCompileSourceRoot(outDir.getAbsolutePath());
  }
}