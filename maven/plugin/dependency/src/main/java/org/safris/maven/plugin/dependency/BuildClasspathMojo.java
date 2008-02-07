package org.safris.maven.plugin.dependency;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.dependency.utils.filters.ArtifactsFilter;

/**
 * This goal will output a classpath string of dependencies from the local
 * repository to a file or log.
 *
 * @goal build-classpath
 * @requiresDependencyResolution runtime
 * @phase generate-sources
 */
public class BuildClasspathMojo extends AbstractDependencyFilterMojo implements Comparator
{
	/**
	 * Strip artifact version during copy (only works if prefix is set)
	 *
	 * @parameter expression="${mdep.stripVersion}" default-value="false"
	 * @parameter
	 */
	private boolean stripVersion = false;

	/**
	 * The prefix to preppend on each dependent artifact. If undefined, the
	 * paths refer to the actual files store in the local repository (the
	 * stipVersion parameter does nothing then).
	 *
	 * @parameter default-value="${settings.localRepository}" expression="${mdep.repositoryPath}"
	 */
	private String repositoryPath;

	/**
	 * @parameter expression="${settings.localRepository}"
	 */
	private String localRepository;

	/**
	 * If 'true', it skips the up-to-date-check, and always regenerates the
	 * classpath file.
	 *
	 * @parameter default-value="false" expression="${mdep.regenerateFile}"
	 */
	private boolean regenerateFile;

	/**
	 * Override the char used between the paths. This field is initialized to
	 * contain the first character of the value of the system property
	 * file.separator. On UNIX systems the value of this field is '/'; on
	 * Microsoft Windows systems it is '\'. The default is File.separator
	 * @since 2.0-alpha-5
	 * @parameter default-value="" expression="${mdep.fileSeparator}"
	 */
	private String fileSeparator;

	/**
	 * Override the char used between path folders. The system-dependent
	 * path-separator character. This field is initialized to contain the first
	 * character of the value of the system property path.separator. This
	 * character is used to separate filenames in a sequence of files given as a
	 * path list. On UNIX systems, this character is ':'; on Microsoft Windows
	 * systems it is ';'.
	 * @since 2.0-alpha-5
	 * @parameter default-value="" expression="${mdep.pathSeparator}"
	 */
	private String pathSeparator;

	boolean isPathSepSet = true;

	/**
	 * Main entry into mojo. Gets the list of dependencies and iterates through
	 * calling copyArtifact.
	 *
	 * @throws MojoExecutionException
	 *			 with a message if an error occurs.
	 *
	 * @see #getDependencies
	 * @see #copyArtifact(Artifact, boolean)
	 */
	public void execute() throws MojoExecutionException
	{
		// initialize the separators.
		if(fileSeparator == null || fileSeparator.length() == 0)
			fileSeparator = File.separator;

		if(pathSeparator == null || pathSeparator.length() == 0)
			pathSeparator = File.pathSeparator;

		final Set<Artifact> artifacts = getResolvedDependencies(true);
		if(artifacts == null || artifacts.isEmpty())
			getLog().info("No dependencies found.");

		final List<Artifact> artifactList = new ArrayList<Artifact>(artifacts);

		final StringBuffer buffer = new StringBuffer();
		final Iterator iterator = artifactList.iterator();

		if(iterator.hasNext())
		{
			appendArtifactPath((Artifact)iterator.next(), buffer);
			while(iterator.hasNext())
			{
				buffer.append(isPathSepSet ? this.pathSeparator : File.pathSeparator);
				appendArtifactPath((Artifact) iterator.next(), buffer);
			}
		}

		String cpString = buffer.toString();

		// if file separator is set, I need to replace the default one from all
		// the file paths the where pulled from the artifacts
		String separator = File.separator;

		// if the file sep is "\" then I need to escape it for the regex
		if(File.separator.equals("\\"))
			separator = "\\\\";

		cpString = cpString.replace(File.separatorChar, fileSeparator.charAt(0));

		System.setProperty("maven.classpath.runtime", cpString);
	}

	/**
	 * Appends the artifact path into the specified stringBuffer.
	 *
	 * @param art
	 * @param sb
	 */
	protected void appendArtifactPath(Artifact artifact, StringBuffer buffer)
	{
		String path = artifact.getFile() != null ? artifact.getFile().toString() : null;
		if(path == null || !path.contains(artifact.getGroupId().replace('.', fileSeparator.charAt(0))))
			path = repositoryPath + fileSeparator + artifact.getGroupId().replace('.', fileSeparator.charAt(0)) + fileSeparator + artifact.getArtifactId() + fileSeparator + artifact.getVersion() + fileSeparator + artifact.getArtifactId() + "-" + artifact.getVersion() + ".jar";
		else if(repositoryPath != null)
			path = repositoryPath + path.substring(localRepository.length());

		buffer.append(path);
	}


	/**
	 * Compares artifacts lexicographically, using pattern
	 * [group_id][artifact_id][version].
	 *
	 * @param arg1
	 *			first object
	 * @param arg2
	 *			second object
	 * @return the value <code>0</code> if the argument string is equal to
	 *		 this string; a value less than <code>0</code> if this string is
	 *		 lexicographically less than the string argument; and a value
	 *		 greater than <code>0</code> if this string is lexicographically
	 *		 greater than the string argument.
	 */
	public int compare(Object arg1, Object arg2)
	{
		if(arg1 instanceof Artifact && arg2 instanceof Artifact)
		{
			if(arg1 == arg2)
				return 0;
			else if(arg1 == null)
				return -1;
			else if(arg2 == null)
				return +1;

			final Artifact art1 = (Artifact)arg1;
			final Artifact art2 = (Artifact)arg2;

			final String s1 = art1.getGroupId() + art1.getArtifactId() + art1.getVersion();
			final String s2 = art2.getGroupId() + art2.getArtifactId() + art2.getVersion();

			return s1.compareTo(s2);
		}
		else
		{
			return 0;
		}
	}

	protected ArtifactsFilter getMarkedArtifactFilter()
	{
		return null;
	}

	/**
	 * @return the fileSeparator
	 */
	public String getFileSeparator()
	{
		return this.fileSeparator;
	}

	/**
	 * @param theFileSeparator
	 *			the fileSeparator to set
	 */
	public void setFileSeparator(String theFileSeparator)
	{
		this.fileSeparator = theFileSeparator;
	}

	/**
	 * @return the pathSeparator
	 */
	public String getPathSeparator()
	{
		return this.pathSeparator;
	}

	/**
	 * @param thePathSeparator
	 *			the pathSeparator to set
	 */
	public void setPathSeparator(String thePathSeparator)
	{
		this.pathSeparator = thePathSeparator;
	}

	/**
	 * @return the regenerateFile
	 */
	public boolean isRegenerateFile()
	{
		return this.regenerateFile;
	}

	/**
	 * @param theRegenerateFile
	 *			the regenerateFile to set
	 */
	public void setRegenerateFile(boolean theRegenerateFile)
	{
		this.regenerateFile = theRegenerateFile;
	}

	/**
	 * @return the stripVersion
	 */
	public boolean isStripVersion()
	{
		return this.stripVersion;
	}

	/**
	 * @param theStripVersion
	 *			the stripVersion to set
	 */
	public void setStripVersion(boolean theStripVersion)
	{
		this.stripVersion = theStripVersion;
	}
}
