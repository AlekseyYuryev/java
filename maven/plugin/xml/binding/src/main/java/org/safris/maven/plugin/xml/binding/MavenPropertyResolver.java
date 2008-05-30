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

package org.safris.maven.plugin.xml.binding;

import org.apache.maven.project.MavenProject;
import org.safris.xml.toolkit.binding.PropertyResolver;

public class MavenPropertyResolver implements PropertyResolver
{
	private final MavenProject project;

	public MavenPropertyResolver(MavenProject project)
	{
		this.project = project;
	}

	public String resolve(String string)
	{
		if(string == null)
			return null;

		final int start = string.indexOf("${");
		if(start == -1)
			return string;

		final int end = string.indexOf("}", start + 2);
		if(end == -1)
			return string;

		final String resolved = getProperty(string.substring(start + 2, end));
		if(resolved == null)
			return string.substring(0, end + 1) + resolve(string.substring(end + 1, string.length()));

		return string.substring(0, start) + resolved + resolve(string.substring(end + 1, string.length()));
	}

	private String getProperty(String string)
	{
		if("basedir".equals(string))
			return project.getBasedir().getAbsolutePath();

		if("project.build.directory".equals(string))
			return project.getBuild().getDirectory();

		if("project.build.outputDirectory".equals(string))
			return project.getBuild().getOutputDirectory();

		if("project.build.sourceDirectory".equals(string))
			return project.getBuild().getSourceDirectory();

		if("project.build.testSourceDirectory".equals(string))
			return project.getBuild().getTestSourceDirectory();

		return null;
	}
}
