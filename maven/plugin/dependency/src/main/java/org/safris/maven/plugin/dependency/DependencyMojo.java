package org.safris.maven.plugin.dependency;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * This goal will output a classpath string of dependencies from the local
 * repository to a file or log.
 *
 * @goal get-classpath
 * @requiresDependencyResolution test
 * @execute phase="generate-sources"
 * @phase generate-sources
 */
public class DependencyMojo extends PropertiesMojo
{
	/**
	 * Method getPath
	 *
	 * @param	artifact			an Artifact
	 * @param	repositoryPath		a String
	 * @param	localRepository		a String
	 * @param	fileSeparator		a char
	 *
	 * @return	a String
	 */
	public static Set<GroupArtifact> getDependencies(DependencyProperties properties) throws MojoExecutionException
	{
		final Set<Artifact> resolvedDependencies = DependencyFilter.getResolvedDependencies(properties);
		final List<Artifact> artifactList = new ArrayList<Artifact>(resolvedDependencies);
		final Set<GroupArtifact> dependencies = new HashSet<GroupArtifact>(artifactList.size());
		for(Artifact artifact : artifactList)
			dependencies.add(new GroupArtifact(artifact));

		return dependencies;
	}

	/**
	 * Main entry into mojo. Gets the list of dependencies and iterates through
	 * calling copyArtifact.
	 *
	 * @throws	MojoExecutionException	with a message if an error occurs.
	 */
	public void execute() throws MojoExecutionException
	{
		String pathSeparator = null;
		if(getPathSeparator() != null && getPathSeparator().length() != 0)
			pathSeparator = getPathSeparator();
		else
			pathSeparator = File.pathSeparator;

		final char fileSeparatorChar;
		if(getFileSeparator() != null && getFileSeparator().length() != 0)
			fileSeparatorChar = getFileSeparator().charAt(0);
		else
			fileSeparatorChar = File.separatorChar;

		final Collection<GroupArtifact> dependencies = getDependencies(this);
		if(dependencies == null)
		{
			getLog().info("No dependencies found.");
			return;
		}

		final StringBuffer buffer = new StringBuffer();
		for(GroupArtifact dependency : dependencies)
		{
			buffer.append(pathSeparator);
			String relativePath = dependency.getRelativePath();
			if(File.separatorChar != fileSeparatorChar)
				relativePath.replace(File.separatorChar, fileSeparatorChar);

			buffer.append(getRepositoryPath() + fileSeparatorChar + relativePath);
		}

		System.setProperty("maven.classpath.runtime", buffer.substring(1));
		getLog().info(buffer.substring(1));
	}
}
