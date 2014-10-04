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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.safris.commons.util.Resolver;
import org.safris.maven.plugin.xml.binding.Manifest;
import org.safris.maven.plugin.xml.binding.MavenPropertyResolver;

public abstract class XDLTransformerMojo extends AbstractMojo {
  /**
   * @parameter default-value="${project}"
   * @required
   */
  protected MavenProject project = null;

  /**
   * @parameter default-value="${basedir}"
   */
  protected String basedir = null;

  /**
   * @parameter
   */
  protected Manifest manifest;

  public void execute() throws MojoExecutionException, MojoFailureException {
    if (manifest == null)
      return;

    if (manifest.getSchemas() == null)
      return;

    if (manifest.getDestdir() == null)
      throw new MojoExecutionException("Destdir must be specified.");

    final Resolver<String> resolver = new MavenPropertyResolver(project);
    for (final String spec : manifest.getSchemas()) {
      final File xdlFile = new File(resolver.resolve(spec));
      if (!xdlFile.exists())
        throw new MojoExecutionException("XDL file does not exist: " + xdlFile.getAbsolutePath());

      final File outDirFile = new File(manifest.getDestdir());
      if (outDirFile.exists()) {
        if (outDirFile.isFile()) {
          throw new MojoExecutionException("Outdir points to a file.");
        }
      }
      else {
        if (!outDirFile.mkdirs())
          throw new MojoExecutionException("Unable to create directory: " + outDirFile.getAbsolutePath());
      }

      transform(xdlFile, outDirFile);
    }
  }

  public abstract void transform(final File xdlFile, final File outDir);
}