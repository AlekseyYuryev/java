/* Copyright (c) 2011 lib4j
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

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mojo(name = "manifest")
public abstract class ManifestMojo extends AbstractMojo {
  private static final Logger logger = LoggerFactory.getLogger(ManifestMojo.class);

  @Parameter(property = "maven.test.skip", defaultValue = "false")
  private boolean mavenTestSkip;

  @Parameter(defaultValue = "${mojoExecution}", readonly = true)
  private MojoExecution mojoExecution;

  @Parameter(defaultValue = "${project}", readonly = true)
  protected MavenProject project;

  @Override
  public final void execute() throws MojoExecutionException, MojoFailureException {
    if (Mojos.shouldSkip(mojoExecution, mavenTestSkip)) {
      logger.info("Tests are skipped.");
      return;
    }

    final Manifest manifest = Manifest.parse(project, mojoExecution);
    // A null manifest means there is no configuration for this plugin, and it is being executed because of extensions
    if (manifest == null)
      return;

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