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

package org.safris.maven.mojo;

import java.io.File;

import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.safris.maven.common.AdvancedMojo;
import org.safris.maven.common.Log;

@Mojo(name = "manifest")
public abstract class ManifestMojo extends AdvancedMojo {
  @Parameter(property = "maven.test.skip", defaultValue = "false")
  private boolean mavenTestSkip;

  @Parameter(defaultValue = "${mojoExecution}", readonly = true)
  private MojoExecution execution;

  @Parameter(defaultValue = "${project}", readonly = true)
  protected MavenProject project;

  @Override
  public final void execute() throws MojoExecutionException, MojoFailureException {
    final Manifest manifest = Manifest.parse(project, execution, mavenTestSkip);
    if (manifest == null) {
      Log.info("Tests are skipped.");
      return;
    }

    final File destDir = manifest.getDestdir();
    if (destDir.exists()) {
      if (destDir.isFile())
        throw new MojoFailureException("Destdir points to a file");
    }
    else if (!destDir.mkdirs()) {
      throw new MojoFailureException("Unable to create destination directory: " + destDir.getAbsolutePath());
    }

    if (manifest.getSchemas() == null)
      return;

    project.addTestCompileSourceRoot(destDir.getAbsolutePath());
    project.addCompileSourceRoot(destDir.getAbsolutePath());

    execute(manifest);
  }

  public abstract void execute(final Manifest manifest) throws MojoExecutionException, MojoFailureException;
}