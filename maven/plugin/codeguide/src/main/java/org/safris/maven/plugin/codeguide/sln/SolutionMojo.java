package org.safris.maven.plugin.codeguide.sln;

import java.io.IOException;
import org.apache.maven.plugin.MojoExecutionException;
import org.safris.maven.plugin.codeguide.javaproj.JavaProjectMojo;

/**
 * @goal sln
 * @requiresDependencyResolution test
 * @phase process-test-sources
 */
public class SolutionMojo extends JavaProjectMojo
{
	public void execute() throws MojoExecutionException
	{
		super.execute();

		// Write the Solution file
		try
		{
			SolutionWriter.write(getStateManager().getSolution());
		}
		catch(IOException e)
		{
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
}
