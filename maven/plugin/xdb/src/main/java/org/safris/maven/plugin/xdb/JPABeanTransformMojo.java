/*  Copyright Safris Software 2011
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

import org.safris.xdb.xdl.JPABeanTransform;

/**
 * @goal jpa
 * @phase generate-sources
 */
public final class JPABeanTransformMojo extends XDLTransformerMojo {
  public void transform(final File xdlFile, final File outDir) {
    JPABeanTransform.createJPABeans(xdlFile, outDir);
    project.addTestCompileSourceRoot(outDir.getAbsolutePath());
    project.addCompileSourceRoot(outDir.getAbsolutePath());
  }
}