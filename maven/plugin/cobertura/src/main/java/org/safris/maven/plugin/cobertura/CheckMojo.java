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

package org.safris.maven.plugin.cobertura;

import java.io.File;
import net.sourceforge.cobertura.ant.CheckTask;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;

/**
 * @goal check
 * @phase verify
 */
public class CheckMojo extends CoberturaMojo {
  // FIXME: Finish this!
  public void execute() throws MojoExecutionException, MojoFailureException {
    System.exit(1);
    if (getMavenTestSkip() != null && getMavenTestSkip())
      return;

    if (getBasedir() == null)
      throw new NullPointerException("basedir == null");

    if (getDirectory() == null)
      throw new NullPointerException("projectBuildDirectory == null");

    final File coberturaDir = new File(getDirectory(), "cobertura");
    final File coberturaReportDir = new File(coberturaDir, "report");

    final Project project = new Project();
    project.addTaskDefinition("java", Java.class);
    project.setBasedir(getBasedir());

    final CheckTask checkTask = new CheckTask();
    checkTask.setDataFile(getDataFile().getAbsolutePath());
    checkTask.setHaltOnFailure(true);
    checkTask.setBranchRate("TODO");
    checkTask.setLineRate("TODO");
    checkTask.setPackageBranchRate("TODO");
    checkTask.setPackageLineRate("TODO");
    checkTask.setTotalBranchRate("TODO");
    checkTask.setTotalLineRate("TODO");
    checkTask.execute();
  }
}
