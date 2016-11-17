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
import org.safris.commons.util.Translator;
import org.safris.maven.common.AdvancedMojo;
import org.safris.maven.common.Manifest;
import org.safris.maven.project.MavenPropertyTranslator;

@Mojo(name = "manifest")
public abstract class ManifestMojo extends AdvancedMojo {
  @Parameter(property = "maven.test.skip", defaultValue = "false")
  private boolean mavenTestSkip;

  @Parameter(defaultValue = "${mojoExecution}", readonly = true)
  private MojoExecution execution;

  @Parameter(defaultValue = "${project}", readonly = true)
  protected MavenProject project;

  @Parameter(property = "manifest", required = true)
  private Manifest manifest;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (manifest == null)
      return;

    if (manifest.getSchemas() == null)
      return;

    if (manifest.getDestdir() == null)
      throw new MojoFailureException("destdir is required");

    if (mavenTestSkip && execution.getLifecyclePhase().contains("test"))
      return;

    final File outDir = new File(manifest.getDestdir());
    if (outDir.exists()) {
      if (outDir.isFile()) {
        throw new MojoFailureException("Outdir points to a file");
      }
    }
    else if (!outDir.mkdirs()) {
      throw new MojoFailureException("Unable to create output directory: " + outDir.getAbsolutePath());
    }

    project.addTestCompileSourceRoot(outDir.getAbsolutePath());
    project.addCompileSourceRoot(outDir.getAbsolutePath());

    final Translator<String> translator = new MavenPropertyTranslator(project);
    for (final String schema : manifest.getSchemas()) {
      final File file = new File(translator.translate(schema));
      if (!file.exists())
        throw new MojoFailureException("File does not exist: " + file.getAbsolutePath());

      execute(file, outDir);
    }
  }

  public abstract void execute(final File file, final File outDir) throws MojoExecutionException, MojoFailureException;
}