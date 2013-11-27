/*  Copyright Safris Software 2008
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

public class StateManager {
  private final Solution solution;
  private final Map<GroupArtifact,JavaProject> artifactToProject = new HashMap<GroupArtifact,JavaProject>();
  private final Map<JavaProject,GroupArtifact> projectToArtifact = new HashMap<JavaProject,GroupArtifact>();
  private final Map<JavaProject,Set<String>> sourcesMap = new HashMap<JavaProject,Set<String>>();

  public StateManager(Solution solution) {
    this.solution = solution;
  }

  public Solution getSolution() {
    return solution;
  }

  public void put(GroupArtifact artifact, JavaProject project) {
    artifactToProject.put(artifact, project);
    projectToArtifact.put(project, artifact);
  }

  public GroupArtifact getGroupArtifact(JavaProject project) {
    return projectToArtifact.get(project);
  }

  public Set<GroupArtifact> getGroupArtifacts() {
    return artifactToProject.keySet();
  }

  public JavaProject getJavaProject(GroupArtifact artifact) {
    return artifactToProject.get(artifact);
  }

  public Set<JavaProject> getJavaProjects() {
    return projectToArtifact.keySet();
  }

  public Set<String> getAddedSources(Set<JavaProject> javaProjects) {
    final Set<String> addedSources = new HashSet<String>();
    for (final JavaProject javaProject : javaProjects) {
      final Set<String> sources = sourcesMap.get(javaProject);
      if (sources != null)
        addedSources.addAll(sources);
    }

    return Collections.<String>unmodifiableSet(addedSources);
  }

  public Set<String> setAddedSources(JavaProject javaProject, Set<String> sources) {
    return sourcesMap.put(javaProject, sources);
  }
}
