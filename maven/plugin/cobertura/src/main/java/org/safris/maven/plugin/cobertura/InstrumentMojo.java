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
import java.io.IOException;

import net.sourceforge.cobertura.ant.InstrumentTask;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.FileSet;
import org.safris.commons.io.Files;

/**
 * @goal instrument
 * @phase test-compile
 */
public final class InstrumentMojo extends CoberturaMojo {
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (getMavenTestSkip() != null && getMavenTestSkip())
      return;

    if (getProject() == null)
      throw new NullPointerException("project == null");

    final File classesDir = new File(getDirectory(), "classes");
    if ("pom".equals(getProject().getPackaging()) || !classesDir.exists())
      return;

    if (getBasedir() == null)
      throw new NullPointerException("basedir == null");

    if (getDirectory() == null)
      throw new NullPointerException("directory == null");

    final File coberturaClassesDir = new File(getCoberturaDir(), "classes");

    getCoberturaDir().mkdir();
    try {
      Files.copy(classesDir, coberturaClassesDir);
    }
    catch (final IOException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }

    final FileSet fileSet = new FileSet();
    fileSet.setFollowSymlinks(true);
    fileSet.setDir(coberturaClassesDir);
    fileSet.setIncludes("**/*.class");
    fileSet.setExcludes("**/*Test*.class");

    final Project project = new Project();
    project.addTaskDefinition("java", Java.class);
    project.setBasedir(getBasedir());

    final InstrumentTask instrumentTask = new InstrumentTask();
    instrumentTask.setProject(project);
    instrumentTask.setToDir(new File(getDirectory(), "test-classes"));
    instrumentTask.setDataFile(getDataFile().getAbsolutePath());
    instrumentTask.XsetIgnore("org.apache.log4j.*");
    instrumentTask.addFileset(fileSet);
    instrumentTask.execute();

    System.setProperty("net.sourceforge.cobertura.datafile", getDataFile().getAbsolutePath());
  }
}