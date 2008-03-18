package org.safris.maven.plugin.cobertura;

import java.io.File;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;

public abstract class CoberturaMojo extends AbstractMojo
{
	/**
	 * @parameter expression="${project}"
	 * @readonly
	 * @required
	 */
	private MavenProject project;

	protected MavenProject getProject()
	{
		return project;
	}

	/**
	 * @parameter default-value="${maven.test.skip}"
	 * @readonly
	 * @required
	 */
	private Boolean mavenTestSkip = null;

	protected Boolean getMavenTestSkip()
	{
		return mavenTestSkip;
	}

	/**
	 * @parameter default-value="${basedir}"
	 * @readonly
	 * @required
	 */
	private String basedir = null;

	protected String getBasedir()
	{
		return basedir;
	}

	/**
	 * @parameter default-value="${project.build.directory}"
	 * @readonly
	 * @required
	 */
	private String directory = null;

	protected String getDirectory()
	{
		return directory;
	}

	/**
	 * @parameter default-value="${project.build.sourceDirectory}"
	 * @readonly
	 * @required
	 */
	private String sourceDirectory = null;

	protected String getSourceDirectory()
	{
		return sourceDirectory;
	}

	private File dataFile = null;

	protected File getDataFile()
	{
		if(dataFile != null)
			return dataFile;

		return dataFile = new File(getCoberturaDir().getAbsoluteFile(), "cobertura.ser");
	}

	private File coberturaDir = null;

	protected File getCoberturaDir()
	{
		if(coberturaDir != null)
			return coberturaDir;

		return coberturaDir = new File(getDirectory(), "cobertura");
	}
}
