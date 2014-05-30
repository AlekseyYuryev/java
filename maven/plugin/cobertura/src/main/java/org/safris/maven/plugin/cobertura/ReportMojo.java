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

import net.sourceforge.cobertura.ant.ReportTask;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;

/**
 * @goal report
 * @phase verify
 */
public final class ReportMojo extends CoberturaMojo {
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (getMavenTestSkip() != null && getMavenTestSkip())
      return;

    if (getProject() == null)
      throw new NullPointerException("project == null");

    if ("pom".equals(getProject().getPackaging()) || !new File(getSourceDirectory()).exists())
      return;

    if (getBasedir() == null)
      throw new NullPointerException("basedir == null");

    if (getDirectory() == null)
      throw new NullPointerException("directory == null");

    final File coberturaReportDir = new File(getCoberturaDir(), "report");

    final Project project = new Project();
    project.addTaskDefinition("java", Java.class);
    project.setBasedir(getBasedir());

    final ReportTask reportTask = new ReportTask();
    reportTask.setProject(project);
    reportTask.setDestDir(coberturaReportDir);
    reportTask.setSrcDir(getSourceDirectory());
    reportTask.setDataFile(getDataFile().getAbsolutePath());
    reportTask.setFormat("html");
    try {
      reportTask.execute();
    }
    catch (final BuildException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }

    getLog().info("Cobertura report url: file:///" + getCoberturaDir().getAbsolutePath() + "/report/index.html");
  }
}