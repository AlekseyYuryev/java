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

import net.sourceforge.cobertura.ant.CheckTask;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;

/**
 * @goal check
 * @phase verify
 */
public final class CheckMojo extends CoberturaMojo {
  // FIXME: Finish this!
  public void execute() throws MojoExecutionException, MojoFailureException {
    System.exit(1);
    if (getMavenTestSkip() != null && getMavenTestSkip())
      return;

    if (getBasedir() == null)
      throw new NullPointerException("basedir == null");

    if (getDirectory() == null)
      throw new NullPointerException("projectBuildDirectory == null");

    //final File coberturaDir = new File(getDirectory(), "cobertura");
    //final File coberturaReportDir = new File(coberturaDir, "report");

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