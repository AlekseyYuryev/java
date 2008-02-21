package org.safris.maven.plugin.codeguide;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import org.safris.maven.plugin.dependency.GroupArtifact;

public class Solution
{
	private final LinkedHashSet<JavaProject> javaProjects = new LinkedHashSet<JavaProject>();
	private final String name;
	private final String shortName;
	private final String uuid;
	private final File dir;

	public Solution(GroupArtifact address, File dir)
	{
		this.name = address.getGroupId() + ":" + address.getArtifactId();
		this.shortName = address.getArtifactId();
		// FIXME: Figure out what the fuck is up with this crap!
		this.uuid = "85B0C3CB-4F8C-465B-A944-62ABB0F7F898";
		this.dir = dir;
	}

	public String getName()
	{
		return name;
	}

	public String getShortName()
	{
		return shortName;
	}

	public String getUUID()
	{
		return uuid;
	}

	public File getDir()
	{
		return dir;
	}

	public void addJavaProject(JavaProject javaProject)
	{
		javaProjects.add(javaProject);
	}

	public Set<JavaProject> getJavaProjects()
	{
		return javaProjects;
	}
}
