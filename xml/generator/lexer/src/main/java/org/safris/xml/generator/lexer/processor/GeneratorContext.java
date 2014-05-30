/*  Copyright Safris Software 2008
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

package org.safris.xml.generator.lexer.processor;

import java.io.File;
import java.io.IOException;

import org.safris.commons.pipeline.PipelineContext;

public final class GeneratorContext implements PipelineContext {
  private final long manifestLastModified;
  private final File destDir;
  private final boolean explodeJars;
  private final boolean overwrite;

  public GeneratorContext(final long manifestLastModified, final File destDir, boolean explodeJars, final boolean overwrite) {
    this.manifestLastModified = manifestLastModified;
    File tempDestDir;
    try {
      tempDestDir = destDir.getCanonicalFile();
    }
    catch (final IOException e) {
      tempDestDir = destDir;
    }
    this.destDir = tempDestDir;
    this.explodeJars = explodeJars;
    this.overwrite = overwrite;
  }

  public long getManifestLastModified() {
    return manifestLastModified;
  }

  public File getDestDir() {
    return destDir;
  }

  public boolean getExplodeJars() {
    return explodeJars;
  }

  public boolean getOverwrite() {
    return overwrite;
  }
}