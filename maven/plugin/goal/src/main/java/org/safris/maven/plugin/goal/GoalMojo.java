package org.safris.maven.plugin.goal;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.PlexusConstants;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.context.Context;
import org.codehaus.plexus.context.ContextException;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Contextualizable;

/**
 * @goal validate
 * @requiresDependencyResolution test
 * @execute phase="compile"
 * @phase compile
 */
public class GoalMojo extends AbstractMojo implements Contextualizable
{
	public void contextualize(Context context) throws ContextException
	{
		PlexusContainer container = (PlexusContainer)context.get(PlexusConstants.PLEXUS_KEY);
		int i = 0;
		// TODO: Implement this method
	}

	/**
	 * @parameter expression="${project}"
	 * @readonly
	 * @required
	 */
	private MavenProject project;

	/**
	 * POM
	 */
	public MavenProject getProject()
	{
		return project;
	}

	public void execute() throws MojoExecutionException, MojoFailureException
	{
		int i = 0;
	}
}
