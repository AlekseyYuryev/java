/* Copyright (c) 2015 Seva Safris
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

package org.safris.maven.plugin.xjb;

import java.io.File;

import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.safris.cf.xjb.Generator;
import org.safris.commons.util.Translator;
import org.safris.maven.common.AdvancedMojo;
import org.safris.maven.common.Manifest;
import org.safris.maven.project.MavenPropertyTranslator;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
@Execute(goal = "generate")
public class XJBMojo extends AdvancedMojo {
  @Parameter(property = "maven.test.skip", defaultValue = "false")
  private Boolean mavenTestSkip = null;

  @Parameter(property = "manifest", required = true)
  protected Manifest manifest;

  @Parameter(defaultValue = "${mojoExecution}", readonly = true)
  private MojoExecution execution;

  @Parameter(defaultValue = "${project}", readonly = true)
  protected MavenProject project;

  @Parameter(defaultValue = "${mojoExecution.lifecyclePhase}")
  private String lifecyclePhase;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (mavenTestSkip != null && mavenTestSkip && execution.getLifecyclePhase() != null && execution.getLifecyclePhase().contains("test"))
      return;

    if (manifest == null)
      return;

    if (manifest.getSchemas() == null)
      return;

    if (manifest.getDestdir() == null)
      throw new MojoFailureException("destdir is required");

    final Translator<String> translator = new MavenPropertyTranslator(project);
    for (final String spec : manifest.getSchemas()) {
      final File xdlFile = new File(translator.translate(spec));
      if (!xdlFile.exists())
        throw new MojoFailureException("XDL file does not exist: " + xdlFile.getAbsolutePath());

      final File outDirFile = new File(manifest.getDestdir());
      if (outDirFile.exists()) {
        if (outDirFile.isFile()) {
          throw new MojoFailureException("Outdir points to a file");
        }
      }
      else if (!outDirFile.mkdirs()) {
        throw new MojoFailureException("Unable to create directory: " + outDirFile.getAbsolutePath());
      }

      try {
        Generator.generate(xdlFile.toURI().toURL(), outDirFile);
      }
      catch (final Exception e) {
        throw new MojoExecutionException(e.getMessage(), e);
      }

      project.addTestCompileSourceRoot(outDirFile.getAbsolutePath());
      project.addCompileSourceRoot(outDirFile.getAbsolutePath());
    }
  }
}