/*  Copyright 2010 Safris Technologies Inc.
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
        for (JavaProject javaProject : javaProjects) {
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
