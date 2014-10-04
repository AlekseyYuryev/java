/* Copyright (c) 2008 Seva Safris
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.maven.plugin.codeguide;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.safris.maven.plugin.codeguide.javaproj.JavaProject;
import org.safris.maven.plugin.codeguide.sln.Solution;
import org.safris.maven.plugin.dependency.GroupArtifact;

public final class StateManager {
  private final Solution solution;
  private final Map<GroupArtifact,JavaProject> artifactToProject = new HashMap<GroupArtifact,JavaProject>();
  private final Map<JavaProject,GroupArtifact> projectToArtifact = new HashMap<JavaProject,GroupArtifact>();
  private final Map<JavaProject,Set<String>> sourcesMap = new HashMap<JavaProject,Set<String>>();

  public StateManager(final Solution solution) {
    this.solution = solution;
  }

  public Solution getSolution() {
    return solution;
  }

  public void put(final GroupArtifact artifact, final JavaProject project) {
    artifactToProject.put(artifact, project);
    projectToArtifact.put(project, artifact);
  }

  public GroupArtifact getGroupArtifact(final JavaProject project) {
    return projectToArtifact.get(project);
  }

  public Set<GroupArtifact> getGroupArtifacts() {
    return artifactToProject.keySet();
  }

  public JavaProject getJavaProject(final GroupArtifact artifact) {
    return artifactToProject.get(artifact);
  }

  public Set<JavaProject> getJavaProjects() {
    return projectToArtifact.keySet();
  }

  public Set<String> getAddedSources(final Set<JavaProject> javaProjects) {
    final Set<String> addedSources = new HashSet<String>();
    for (final JavaProject javaProject : javaProjects) {
      final Set<String> sources = sourcesMap.get(javaProject);
      if (sources != null)
        addedSources.addAll(sources);
    }

    return Collections.<String>unmodifiableSet(addedSources);
  }

  public Set<String> setAddedSources(final JavaProject javaProject, final Set<String> sources) {
    return sourcesMap.put(javaProject, sources);
  }
}