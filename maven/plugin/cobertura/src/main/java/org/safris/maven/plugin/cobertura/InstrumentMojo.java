/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
public class InstrumentMojo extends CoberturaMojo
{
	public void execute() throws MojoExecutionException, MojoFailureException
	{
		if(getMavenTestSkip() != null && getMavenTestSkip())
			return;

		if(getProject() == null)
			throw new NullPointerException("project == null");

		final File classesDir = new File(getDirectory(), "classes");
		if("pom".equals(getProject().getPackaging()) || !classesDir.exists())
			return;

		if(getBasedir() == null)
			throw new NullPointerException("basedir == null");

		if(getDirectory() == null)
			throw new NullPointerException("directory == null");

		final File coberturaClassesDir = new File(getCoberturaDir(), "classes");

		getCoberturaDir().mkdir();
		try
		{
			Files.copy(classesDir, coberturaClassesDir);
		}
		catch(IOException e)
		{
			throw new MojoExecutionException(e.getMessage(), e);
		}

		final FileSet fileSet = new FileSet();
		fileSet.setFollowSymlinks(true);
		fileSet.setDir(coberturaClassesDir);
		fileSet.setIncludes("**/*.class");
		fileSet.setExcludes("**/*Test.class");

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
