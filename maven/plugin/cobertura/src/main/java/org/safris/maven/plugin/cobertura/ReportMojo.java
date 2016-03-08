/* Copyright (c) 2008 Seva Safris
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

package org.safris.maven.plugin.cobertura;

import java.io.File;

import net.sourceforge.cobertura.ant.ReportTask;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;

@Mojo(name = "report", defaultPhase = LifecyclePhase.VERIFY)
@Execute(goal = "report")
public final class ReportMojo extends CoberturaMojo {
  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (getMavenTestSkip())
      return;

    if (getProject() == null)
      throw new NullPointerException("project == null");

    if ("pom".equals(getProject().getPackaging()) || !getSourceDirectory().exists())
      return;

    if (getBasedir() == null)
      throw new NullPointerException("basedir == null");

    if (getDirectory() == null)
      throw new NullPointerException("directory == null");

    final File coberturaReportDir = new File(getCoberturaDir(), "report");

    final Project project = new Project();
    project.addTaskDefinition("java", Java.class);
    project.setBasedir(getBasedir().getAbsolutePath());

    final ReportTask reportTask = new ReportTask();
    reportTask.setProject(project);
    reportTask.setDestDir(coberturaReportDir);
    reportTask.setSrcDir(getSourceDirectory().getAbsolutePath());
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