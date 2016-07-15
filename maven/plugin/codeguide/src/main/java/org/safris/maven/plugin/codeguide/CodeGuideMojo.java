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

import java.util.List;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.model.Resource;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.safris.maven.common.AdvancedMojo;
import org.safris.maven.plugin.dependency.DependencyProperties;

public abstract class CodeGuideMojo extends AdvancedMojo implements DependencyProperties {
  @Parameter(property = "project.build.sourceDirectory", readonly = true, required = true)
  private String sourceDirectory;

  public String getSourceDirectory() {
    return sourceDirectory;
  }

  @Parameter(property = "project.build.testSourceDirectory", readonly = true, required = true)
  private String testSourceDirectory;

  public String getTestSourceDirectory() {
    return testSourceDirectory;
  }

  @Parameter(property = "project.build.directory", readonly = true, required = true)
  private String directory;

  public String getDirectory() {
    return directory;
  }

  @Parameter(property = "project.resources", readonly = true, required = true)
  private List<Resource> resources;

  public List<Resource> getResources() {
    return resources;
  }

  @Parameter(property = "project.testResources", readonly = true, required = true)
  private List<Resource> testResources;

  public List<Resource> getTestResources() {
    return testResources;
  }

  @Parameter(property = "mdep.fileSeparator")
  private String fileSeparator;

  /**
   * Override the char used between the paths. This field is initialized to
   * contain the first character of the value of the system property
   * file.separator. On UNIX systems the value of this field is '/'; on
   * Microsoft Windows systems it is '\'. The default is File.separator
   */
  @Override
  public String getFileSeparator() {
    return fileSeparator;
  }

  @Parameter(property = "mdep.pathSeparator")
  private String pathSeparator;

  /**
   * Override the char used between path folders. The system-dependent
   * path-separator character. This field is initialized to contain the first
   * character of the value of the system property path.separator. This
   * character is used to separate filenames in a sequence of files given as a
   * path list. On UNIX systems, this character is ':'; on Microsoft Windows
   * systems it is ';'.
   */
  @Override
  public String getPathSeparator() {
    return pathSeparator;
  }

  @Component
  private ArtifactFactory factory;

  /**
   * Used to look up Artifacts in the remote repository.
   */
  @Override
  public ArtifactFactory getFactory() {
    return factory;
  }

  @Component
  private ArtifactResolver resolver;

  /**
   * Used to look up Artifacts in the remote repository.
   */
  @Override
  public ArtifactResolver getResolver() {
    return resolver;
  }

  @Parameter(property = "settings.localRepository", readonly = true, required = true)
  private String repositoryPath;

  /**
   * The prefix to preppend on each dependent artifact. If undefined, the
   * paths refer to the actual files store in the local repository (the
   * stipVersion parameter does nothing then).
   */
  @Override
  public String getRepositoryPath() {
    return repositoryPath;
  }

  @Parameter(property = "localRepository", readonly = true, required = true)
  private ArtifactRepository local;

  /**
   * Location of the local repository.
   */
  @Override
  public ArtifactRepository getLocal() {
    return local;
  }

  @Parameter(property = "project.remoteArtifactRepositories", readonly = true, required = true)
  private List<ArtifactRepository> remoteRepos;

  /**
   * List of Remote Repositories used by the resolver
   */
  @Override
  public List<ArtifactRepository> getRemoteRepos() {
    return remoteRepos;
  }

  @Parameter(defaultValue = "${project}", readonly = true)
  private MavenProject project;

  /**
   * POM
   */
  @Override
  public MavenProject getProject() {
    return project;
  }

  @Parameter(property = "excludeTransitive", defaultValue = "false")
  private boolean excludeTransitive;

  /**
   * If we should exclude transitive dependencies
   */
  @Override
  public boolean getExcludeTransitive() {
    return excludeTransitive;
  }

  @Parameter(property = "includeTypes", defaultValue = "")
  private String includeTypes;

  /**
   * Comma Separated list of Types to include. Empty String indicates include
   * everything (default).
   */
  @Override
  public String getIncludeTypes() {
    return includeTypes;
  }

  @Parameter(property = "excludeTypes", defaultValue = "")
  private String excludeTypes;

  /**
   * Comma Separated list of Types to exclude. Empty String indicates don't
   * exclude anything (default).
   */
  @Override
  public String getExcludeTypes() {
    return excludeTypes;
  }

  @Parameter(property = "includeScope", defaultValue = "")
  private String includeScope;

  /**
   * Scope to include. An Empty string indicates all scopes (default).
   */
  @Override
  public String getIncludeScope() {
    return includeScope;
  }

  @Parameter(property = "excludeScope", defaultValue = "")
  private String excludeScope;

  /**
   * Scope to exclude. An empty string indicates no scopes (default).
   */
  @Override
  public String getExcludeScope() {
    return excludeScope;
  }

  @Parameter(property = "includeClassifiers", defaultValue = "")
  private String includeClassifiers;

  /**
   * Comma Separated list of Classifiers to include. Empty String indicates
   * include everything (default).
   */
  @Override
  public String getIncludeClassifiers() {
    return includeClassifiers;
  }

  @Parameter(property = "excludeClassifiers", defaultValue = "")
  private String excludeClassifiers;

  /**
   * Comma Separated list of Classifiers to exclude. Empty String indicates
   * don't exclude anything (default).
   */
  @Override
  public String getExcludeClassifiers() {
    return excludeClassifiers;
  }

  @Parameter(property = "classifier", defaultValue = "")
  private String classifier;

  /**
   * Specify classifier to look for. Example: sources
   */
  @Override
  public String getClassifier() {
    return classifier;
  }

  @Parameter(property = "type", defaultValue = "java-source")
  private String type;

  /**
   * Specify type to look for when constructing artifact based on classifier.
   * Example: java-source,jar,war
   */
  @Override
  public String getType() {
    return type;
  }

  @Parameter(property = "excludeArtifactIds", defaultValue = "")
  private String excludeArtifactIds;

  /**
   * Comma separated list of Artifact names too exclude.
   */
  @Override
  public String getExcludeArtifactIds() {
    return excludeArtifactIds;
  }

  @Parameter(property = "includeArtifactIds", defaultValue = "")
  private String includeArtifactIds;

  /**
   * Comma separated list of Artifact names to include.
   */
  @Override
  public String getIncludeArtifactIds() {
    return includeArtifactIds;
  }

  @Parameter(property = "excludeGroupIds", defaultValue = "")
  private String excludeGroupIds;

  /**
   * Comma separated list of GroupId Names to exclude.
   */
  @Override
  public String getExcludeGroupIds() {
    return excludeGroupIds;
  }

  @Parameter(property = "includeGroupIds", defaultValue = "")
  private String includeGroupIds;

  /**
   * Comma separated list of GroupIds to include.
   */
  @Override
  public String getIncludeGroupIds() {
    return includeGroupIds;
  }

  @Parameter(property = "excludeArtifactIds", defaultValue = "")
  private String excludeGroupIdArtifactIds;

  public void setExcludeGroupIdArtifactIds(final String excludeGroupIdArtifactIds) {
    this.excludeGroupIdArtifactIds = excludeGroupIdArtifactIds;
  }

  /**
   * Comma separated list of Artifact names too exclude.
   */
  @Override
  public String getExcludeGroupIdArtifactIds() {
    return excludeGroupIdArtifactIds;
  }

  @Parameter(property = "includeArtifactIds", defaultValue = "")
  private String includeGroupIdArtifactIds;

  /**
   * Comma separated list of Artifact names to include.
   */
  @Override
  public String getIncludeGroupIdArtifactIds() {
    return includeGroupIdArtifactIds;
  }
}