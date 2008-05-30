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
import net.sourceforge.cobertura.ant.CheckTask;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;

/**
 * @goal check
 * @phase test
 */
public class CheckMojo extends CoberturaMojo
{
	// FIXME: Finish this!
	public void execute() throws MojoExecutionException, MojoFailureException
	{
		System.exit(1);
		if(getMavenTestSkip() != null && getMavenTestSkip())
			return;

		if(getBasedir() == null)
			throw new NullPointerException("basedir == null");

		if(getDirectory() == null)
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
