package org.safris.maven.plugin.cobertura;

import java.io.File;
import net.sourceforge.cobertura.ant.ReportTask;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;

/**
 * @goal report
 * @phase test
 */
public class ReportMojo extends CoberturaMojo
{
	public void execute() throws MojoExecutionException, MojoFailureException
	{
		if(getMavenTestSkip() != null && getMavenTestSkip())
			return;

		if(getProject() == null)
			throw new NullPointerException("project == null");

		if("pom".equals(getProject().getPackaging()))
			return;

		if(getBasedir() == null)
			throw new NullPointerException("basedir == null");

		if(getDirectory() == null)
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
		reportTask.execute();

		getLog().info("Writing Report: file:///" + getDirectory() + "/cobertura/report/index.html");
	}
}
