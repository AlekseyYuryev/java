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
