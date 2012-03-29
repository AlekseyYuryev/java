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

package org.safris.maven.plugin.codeguide.javaproj;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.safris.commons.io.Files;
import org.safris.commons.lang.ClassLoaderLocal;
import org.safris.maven.plugin.codeguide.CodeGuideMojo;
import org.safris.maven.plugin.codeguide.StateManager;
import org.safris.maven.plugin.codeguide.sln.Solution;
import org.safris.maven.plugin.dependency.DependencyMojo;
import org.safris.maven.plugin.dependency.GroupArtifact;

/**
 * @goal javaproj
 * @requiresDependencyResolution test
 * @phase process-test-sources
 */
public class JavaProjectMojo extends CodeGuideMojo {
  private static final ClassLoaderLocal<StateManager> classLoaderLocal = new ClassLoaderLocal<StateManager>(ClassLoader.getSystemClassLoader());
  private static final FileFilter resourceFileFilter = new FileFilter() {
    public boolean accept(File pathname) {
      return !pathname.isDirectory() && !pathname.getAbsolutePath().contains(".svn");
    }
  };

  private static final FileFilter sourceFileFilter = new FileFilter() {
    public boolean accept(File pathname) {
      return pathname.getName().endsWith(".java");
    }
  };

  private static Set<GroupArtifact> filterProjectReferences(Collection<GroupArtifact> dependencies, Set<GroupArtifact> inlcudes, Set<GroupArtifact> excludes) {
    if (dependencies == null)
      return null;

    final Set<GroupArtifact> filteredReferences = new HashSet<GroupArtifact>();
    if (inlcudes != null) {
      if (excludes != null) {
        for (GroupArtifact dependency : dependencies)
          if (inlcudes.contains(dependency) && !excludes.contains(dependency))
            filteredReferences.add(dependency);
      }
      else {
        for (GroupArtifact dependency : dependencies)
          if (inlcudes.contains(dependency))
            filteredReferences.add(dependency);
      }
    }
    else if (excludes != null) {
      for (GroupArtifact dependency : dependencies)
        if (!excludes.contains(dependency))
          filteredReferences.add(dependency);
    }
    else {
      filteredReferences.addAll(dependencies);
    }

    return filteredReferences;
  }

  private Set<File> findSources() {
    final Set<File> sourceFiles = new HashSet<File>();

    // Find all files in the <sourceDirectory/>
    final File sourceDirectoryFile = new File(getSourceDirectory());
    final Collection<File> sourceDirectoryFiles = Files.listAll(sourceDirectoryFile, sourceFileFilter);
    if (sourceDirectoryFiles != null)
      sourceFiles.addAll(sourceDirectoryFiles);

    // Find all files in the <testSourceDirectory/>
    final File testSourceDirectoryFile = new File(getTestSourceDirectory());
    final Collection<File> testSourceFiles = Files.listAll(testSourceDirectoryFile, sourceFileFilter);
    if (testSourceFiles != null)
      sourceFiles.addAll(testSourceFiles);

    // Find all files in the <directory/>
    // TODO: This is the output dir! Should there be any more filtering here?
    final File directoryFile = new File(getDirectory());
    final Collection<File> directoryFiles = Files.listAll(directoryFile, sourceFileFilter);
    if (directoryFiles != null)
      sourceFiles.addAll(directoryFiles);

    return sourceFiles;
  }

  private Set<File> findResources() {
    final Set<File> resourceFiles = new HashSet<File>();

    final Collection<Resource> resources = getResources();
    final Collection<Resource> testResources = getTestResources();
    if (testResources != null)
      resources.addAll(testResources);

    for (Resource resource : resources) {
      final Collection<File> directoryFiles = Files.listAll(new File(resource.getDirectory()), resourceFileFilter);
      if (directoryFiles != null)
        resourceFiles.addAll(directoryFiles);
    }

    return resourceFiles;
  }

  private LinkedHashSet<File> filterClasspathReferences(Collection<GroupArtifact> dependencies, Set<GroupArtifact> excludes) {
    if (dependencies == null)
      return null;

    final LinkedHashSet<File> filteredDependencies = new LinkedHashSet<File>();
    if (excludes != null) {
      for (GroupArtifact dependency : dependencies)
        if (!excludes.contains(dependency))
          addJarAndSourceDependency(filteredDependencies, dependency);
    }
    else {
      for (GroupArtifact dependency : dependencies)
        addJarAndSourceDependency(filteredDependencies, dependency);
    }

    return filteredDependencies;
  }

  private void addJarAndSourceDependency(LinkedHashSet<File> dependencies, GroupArtifact dependency) {
    final File jarFile = DependencyMojo.getFile(dependency, getLocal(), getRepositoryPath());
    final File sourceFile = new File(jarFile.getAbsolutePath().replace(".jar", "-sources.jar"));
    // If the source file does not exist, try to download it
    boolean sourcesExist = sourceFile.exists();
    if (!sourcesExist) {
      try {
        final Artifact artifact = getFactory().createArtifact(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion() + "-sources", dependency.getScope(), dependency.getType());
        artifact.setBaseVersion(dependency.getVersion());
        getResolver().resolve(artifact, getRemoteRepos(), getLocal());
        sourcesExist = true;
      }
      catch (Exception e) {
      }
    }

    if (sourcesExist)
      dependencies.add(sourceFile);

    dependencies.add(jarFile);
  }

  private void resolveDependencies(JavaProject project, StateManager stateManager) {
    // Filter the classpath reference by excluding the other projects
    final LinkedHashSet<File> filteredDependencies = filterClasspathReferences(project.getDependencies(), stateManager.getGroupArtifacts());
    project.setClasspathReferences(filteredDependencies);

    final Set<GroupArtifact> excludes = new HashSet<GroupArtifact>();
    excludes.add(stateManager.getGroupArtifact(project));

    final Set<GroupArtifact> filteredReferences = filterProjectReferences(project.getDependencies(), stateManager.getGroupArtifacts(), excludes);
    final Set<JavaProject> references = new HashSet<JavaProject>();
    for (GroupArtifact reference : filteredReferences)
      references.add(stateManager.getJavaProject(reference));

    project.setProjectReferences(references);
  }

  protected StateManager getStateManager() {
    StateManager stateManager;
    if ((stateManager = classLoaderLocal.get()) == null) {
      final GroupArtifact self = new GroupArtifact(getProject().getArtifact());
      stateManager = new StateManager(new Solution(self, getProject().getFile().getParentFile()));
      classLoaderLocal.set(stateManager);
    }

    return stateManager;
  }

  /**
   * File path to a project if it's already been added to a dependency. This
   * is why this code sits here where we first resolve the dependencies and
   * project references.
   */
  private Set<File> filterDuplicateSources(JavaProject javaProject, Set<File> sources) {
    final Map<String,File> sourcesMap = new HashMap<String,File>();
    for (File file : sources)
      sourcesMap.put(Files.relativePath(javaProject.getDir(), file), file);

    final Set<String> addedSources = getStateManager().getAddedSources(javaProject.getProjectReferences());
    for (String addedSource : addedSources)
      if (addedSource.endsWith(".java"))
        sourcesMap.remove(addedSource);

    getStateManager().setAddedSources(javaProject, sourcesMap.keySet());
    return Collections.<File>unmodifiableSet(new HashSet<File>(sourcesMap.values()));
  }

  public void execute() throws MojoExecutionException {
    // This has to be done first to initialize the StateManager.
    final StateManager stateManager = getStateManager();

    // FIXME: If a dependency is listed that points to a
    // FIXME: <packaging>pom</packaging> artifact, then this code has to
    // FIXME: properly resolve those dependencies as project references.
    if ("pom".equals(getProject().getPackaging()))
      return;

    final GroupArtifact self = new GroupArtifact(getProject().getArtifact());
    final JavaProject javaProject = new JavaProject(self, getProject().getFile().getParentFile());
    stateManager.put(self, javaProject);

    // Find the project's resources, and dependencies
    javaProject.setResourceFiles(findResources());
    javaProject.setDependencies(DependencyMojo.getDependencies(this));

    // Cross reference the java project to the solution, and vice versa
    stateManager.getSolution().addJavaProject(javaProject);
    javaProject.setSolution(stateManager.getSolution());

    // Write the JavaProject files
    try {
      for (JavaProject project : stateManager.getJavaProjects()) {
        resolveDependencies(project, stateManager);
        // For the sources, we dont want to add the same relative
        if (project == javaProject)
          javaProject.setSourceFiles(filterDuplicateSources(javaProject, findSources()));

        JavaProjectWriter.write(project);
      }
    }
    catch (Exception e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }
}
