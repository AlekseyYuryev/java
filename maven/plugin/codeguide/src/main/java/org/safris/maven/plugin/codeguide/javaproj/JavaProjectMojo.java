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
import org.apache.maven.artifact.resolver.ArtifactResolutionRequest;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.safris.commons.io.Files;
import org.safris.commons.lang.ClassLoaderLocal;
import org.safris.maven.plugin.codeguide.CodeGuideMojo;
import org.safris.maven.plugin.codeguide.StateManager;
import org.safris.maven.plugin.codeguide.sln.Solution;
import org.safris.maven.plugin.dependency.DependencyMojo;
import org.safris.maven.plugin.dependency.GroupArtifact;

@Mojo(name = "javaproj", requiresDependencyResolution = ResolutionScope.TEST, defaultPhase = LifecyclePhase.PROCESS_TEST_SOURCES)
@Execute(goal = "javaproj")
public class JavaProjectMojo extends CodeGuideMojo {
  private static final ClassLoaderLocal<StateManager> classLoaderLocal = new ClassLoaderLocal<StateManager>(ClassLoader.getSystemClassLoader());
  private static final FileFilter resourceFileFilter = new FileFilter() {
    @Override
    public boolean accept(final File pathname) {
      return !pathname.isDirectory() && !pathname.getAbsolutePath().contains(".svn");
    }
  };

  private static final FileFilter sourceFileFilter = new FileFilter() {
    @Override
    public boolean accept(final File pathname) {
      return pathname.getName().endsWith(".java");
    }
  };

  private static Set<GroupArtifact> filterProjectReferences(final Collection<GroupArtifact> dependencies, final Set<GroupArtifact> inlcudes, final Set<GroupArtifact> excludes) {
    if (dependencies == null)
      return null;

    final Set<GroupArtifact> filteredReferences = new HashSet<GroupArtifact>();
    if (inlcudes != null) {
      if (excludes != null) {
        for (final GroupArtifact dependency : dependencies)
          if (inlcudes.contains(dependency) && !excludes.contains(dependency))
            filteredReferences.add(dependency);
      }
      else {
        for (final GroupArtifact dependency : dependencies)
          if (inlcudes.contains(dependency))
            filteredReferences.add(dependency);
      }
    }
    else if (excludes != null) {
      for (final GroupArtifact dependency : dependencies)
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

    for (final Resource resource : resources) {
      final Collection<File> directoryFiles = Files.listAll(new File(resource.getDirectory()), resourceFileFilter);
      if (directoryFiles != null)
        resourceFiles.addAll(directoryFiles);
    }

    return resourceFiles;
  }

  private LinkedHashSet<File> filterClasspathReferences(final Collection<GroupArtifact> dependencies, final Set<GroupArtifact> excludes) {
    if (dependencies == null)
      return null;

    final LinkedHashSet<File> filteredDependencies = new LinkedHashSet<File>();
    if (excludes != null) {
      for (final GroupArtifact dependency : dependencies)
        if (!excludes.contains(dependency))
          addJarAndSourceDependency(filteredDependencies, dependency);
    }
    else {
      for (final GroupArtifact dependency : dependencies)
        addJarAndSourceDependency(filteredDependencies, dependency);
    }

    return filteredDependencies;
  }

  private void addJarAndSourceDependency(final LinkedHashSet<File> dependencies, final GroupArtifact dependency) {
    final File jarFile = DependencyMojo.getFile(dependency, getLocal(), getRepositoryPath());
    final File sourceFile = new File(jarFile.getAbsolutePath().replace(".jar", "-sources.jar"));
    // If the source file does not exist, try to download it
    boolean sourcesExist = sourceFile.exists();
    if (!sourcesExist) {
      try {
        final Artifact artifact = getFactory().createArtifact(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion() + "-sources", dependency.getScope(), dependency.getType());
        artifact.setBaseVersion(dependency.getVersion());
        final ArtifactResolutionRequest request = new ArtifactResolutionRequest();
        request.setArtifact(artifact);
        request.setRemoteRepostories(getRemoteRepos());
        request.setLocalRepository(getLocal());
        getResolver().resolve(request);
        sourcesExist = true;
      }
      catch (final Exception e) {
      }
    }

    if (sourcesExist)
      dependencies.add(sourceFile);

    dependencies.add(jarFile);
  }

  private void resolveDependencies(final JavaProject project, final StateManager stateManager) {
    // Filter the classpath reference by excluding the other projects
    final LinkedHashSet<File> filteredDependencies = filterClasspathReferences(project.getDependencies(), stateManager.getGroupArtifacts());
    project.setClasspathReferences(filteredDependencies);

    final Set<GroupArtifact> excludes = new HashSet<GroupArtifact>();
    excludes.add(stateManager.getGroupArtifact(project));

    final Set<GroupArtifact> filteredReferences = filterProjectReferences(project.getDependencies(), stateManager.getGroupArtifacts(), excludes);
    final Set<JavaProject> references = new HashSet<JavaProject>();
    for (final GroupArtifact reference : filteredReferences)
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
  private Set<File> filterDuplicateSources(final JavaProject javaProject, final Set<File> sources) {
    final Map<String,File> sourcesMap = new HashMap<String,File>();
    for (final File file : sources)
      sourcesMap.put(Files.relativePath(javaProject.getDir(), file), file);

    final Set<String> addedSources = getStateManager().getAddedSources(javaProject.getProjectReferences());
    for (final String addedSource : addedSources)
      if (addedSource.endsWith(".java"))
        sourcesMap.remove(addedSource);

    getStateManager().setAddedSources(javaProject, sourcesMap.keySet());
    return Collections.<File>unmodifiableSet(new HashSet<File>(sourcesMap.values()));
  }

  @Override
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
      for (final JavaProject project : stateManager.getJavaProjects()) {
        resolveDependencies(project, stateManager);
        // For the sources, we dont want to add the same relative
        if (project == javaProject)
          javaProject.setSourceFiles(filterDuplicateSources(javaProject, findSources()));

        JavaProjectWriter.write(project);
      }
    }
    catch (final Exception e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }
}