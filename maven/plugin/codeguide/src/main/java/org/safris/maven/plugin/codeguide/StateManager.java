package org.safris.maven.plugin.codeguide;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.safris.maven.plugin.codeguide.javaproj.JavaProject;
import org.safris.maven.plugin.codeguide.sln.Solution;
import org.safris.maven.plugin.dependency.GroupArtifact;

public class StateManager
{
	private final Solution solution;
	private final Map<GroupArtifact,JavaProject> artifactToProject = new HashMap<GroupArtifact,JavaProject>();
	private final Map<JavaProject,GroupArtifact> projectToArtifact = new HashMap<JavaProject,GroupArtifact>();

	public StateManager(Solution solution)
	{
		this.solution = solution;
	}

	public Solution getSolution()
	{
		return solution;
	}

	public void put(GroupArtifact artifact, JavaProject project)
	{
		artifactToProject.put(artifact, project);
		projectToArtifact.put(project, artifact);
	}

	public GroupArtifact getGroupArtifact(JavaProject project)
	{
		return projectToArtifact.get(project);
	}

	public Set<GroupArtifact> getGroupArtifacts()
	{
		return artifactToProject.keySet();
	}

	public JavaProject getJavaProject(GroupArtifact artifact)
	{
		return artifactToProject.get(artifact);
	}

	public Set<JavaProject> getJavaProjects()
	{
		return projectToArtifact.keySet();
	}
}
