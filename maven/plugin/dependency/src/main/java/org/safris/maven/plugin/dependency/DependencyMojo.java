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

package org.safris.maven.plugin.dependency;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.MojoExecutionException;
import org.safris.commons.lang.Paths;

/**
 * This goal will output a classpath string of dependencies from the local
 * repository to a System property.
 *
 * @goal get-classpath
 * @requiresDependencyResolution test
 * @phase generate-sources
 */
public final class DependencyMojo extends PropertiesMojo {
  /**
   * Get dependencies given the <code>DependencyProperties</code> object
   * parameter.
   *
   * @param properties <code>DependencyProperties</code> object with desired
   * values injected by the maven runtime.
   * @return The set of <code>GroupArtifact</code> dependencies.
   */
  public static Set<GroupArtifact> getDependencies(final DependencyProperties properties) throws MojoExecutionException {
    final Set<Artifact> resolvedDependencies = DependencyFilter.getResolvedDependencies(properties);
    final List<Artifact> artifactList = new ArrayList<Artifact>(resolvedDependencies);
    final Set<GroupArtifact> dependencies = new HashSet<GroupArtifact>(artifactList.size());
    for (final Artifact artifact : artifactList)
      dependencies.add(new GroupArtifact(artifact));

    return dependencies;
  }

  /**
   * Get the <code>File</code> object representing the location of the
   * dependency, given the localRepository and repositoryPath
   * <code>String</code>.
   *
   * @param dependency The <code>GroupArtifact</code> object representing the dependency.
   * @param localRepository The <code>ArtifactRepository</code> object representing the
   * location of the local repository.
   * @param repositoryPath A path to a repository that should be used instead
   * of the <code>localRepository</code> object.
   * @return The set of GroupArtifact dependencies.
   */
  public static File getFile(final GroupArtifact dependency, final ArtifactRepository localRepository, final String repositoryPath) {
    final String relativePath = Paths.relativePath(localRepository.getBasedir(), dependency.getPath());
    if (relativePath == null)
      return null;

    if (!Paths.isAbsolute(relativePath))
      return new File(repositoryPath, relativePath);
    else
      return new File(relativePath);
  }

  public void execute() throws MojoExecutionException {
    String pathSeparator = null;
    if (getPathSeparator() != null && getPathSeparator().length() != 0)
      pathSeparator = getPathSeparator();
    else
      pathSeparator = File.pathSeparator;

    final char fileSeparatorChar;
    if (getFileSeparator() != null && getFileSeparator().length() != 0)
      fileSeparatorChar = getFileSeparator().charAt(0);
    else
      fileSeparatorChar = File.separatorChar;

    final Collection<GroupArtifact> dependencies = getDependencies(this);
    if (dependencies == null) {
      getLog().info("No dependencies found.");
      return;
    }

    final StringBuffer buffer = new StringBuffer();
    for (final GroupArtifact dependency : dependencies) {
      buffer.append(pathSeparator);
      final File file = getFile(dependency, getLocal(), getRepositoryPath());
      if (file == null)
        continue;

      String absolutePath = file.getAbsolutePath();
      if (File.separatorChar != fileSeparatorChar)
        absolutePath = absolutePath.replace(File.separatorChar, fileSeparatorChar);

      buffer.append(absolutePath);
    }

    // FIXME: Here we should distinguish between the different scopes
    // FIXME: and set the value to the appropriate property name.
    System.setProperty("maven.classpath.runtime", buffer.substring(1));
    getLog().info(buffer.substring(1));
  }
}