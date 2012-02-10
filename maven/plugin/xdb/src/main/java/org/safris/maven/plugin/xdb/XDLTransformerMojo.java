/*  Copyright Safris Software 2012
 *
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
    String href = null;
    boolean explodeJars = false;
    boolean overwrite = false;
    if (manifest == null)
      return;

    if (manifest.getSchemas() == null)
      return;

    if (manifest.getDestdir() == null)
      throw new MojoExecutionException("Destdir must be specified.");

    final Resolver<String> resolver = new MavenPropertyResolver(project);
    for (String spec : manifest.getSchemas()) {
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
