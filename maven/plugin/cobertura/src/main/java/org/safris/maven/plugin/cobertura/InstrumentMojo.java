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