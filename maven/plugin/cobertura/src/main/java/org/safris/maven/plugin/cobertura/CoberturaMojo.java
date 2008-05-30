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
