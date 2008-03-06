package org.safris.maven.plugin.task;

import java.util.Arrays;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @goal task-segment
 * @execute phase="validate"
 * @phase validate
 */
public class SegmentMojo extends AbstractMojo implements Initializable
{
	private final String[] goals = new String[]
	{
		"validate",
		"generate-sources",
		"process-sources",
		"generate-resources",
		"process-resources",
		"compile",
		"process-classes",
		"generate-test-sources",
		"process-test-sources",
		"generate-test-resources",
		"process-test-resources",
		"test-compile",
		"test",
		"package",
		"integration-test",
		"verify",
		"install",
		"deploy"
	};

	public void initialize() throws InitializationException
	{
		// Sort the goals so we can binarySearch() on it.
		Arrays.sort(goals);
	}

	/**
	 * @parameter expression="${session}"
	 * @readonly
	 * @required
	 */
	private MavenSession session;

	public void execute() throws MojoExecutionException, MojoFailureException
	{
		for(Object goal : session.getGoals())
			if(Arrays.binarySearch(goals, goal) != -1)
				System.setProperty("task-segment:" + goal, "true");

		for(Object goal : session.getGoals())
			System.out.println("task-segment:" + goal + "=" + System.getProperty("task-segment:" + goal));
	}
}
