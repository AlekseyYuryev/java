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

package org.safris.maven.plugin.codeguide.sln;

import java.io.IOException;
import org.apache.maven.plugin.MojoExecutionException;
import org.safris.maven.plugin.codeguide.javaproj.JavaProjectMojo;

/**
 * @goal sln
 * @requiresDependencyResolution test
 * @phase process-test-sources
 */
public final class SolutionMojo extends JavaProjectMojo {
  public void execute() throws MojoExecutionException {
    super.execute();

    // Write the Solution file
    try {
      SolutionWriter.write(getStateManager().getSolution());
    }
    catch (IOException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }
}
