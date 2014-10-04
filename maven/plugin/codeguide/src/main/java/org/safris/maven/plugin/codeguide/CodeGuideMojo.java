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
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;
import org.safris.maven.plugin.dependency.DependencyProperties;

public abstract class CodeGuideMojo extends AbstractMojo implements DependencyProperties {
  /**
   * @parameter expression="${project.build.sourceDirectory}"
   */
  private String sourceDirectory;

  public String getSourceDirectory() {
    return sourceDirectory;
  }

  /**
   * @parameter expression="${project.build.testSourceDirectory}"
   */
  private String testSourceDirectory;

  public String getTestSourceDirectory() {
    return testSourceDirectory;
  }

  /**
   * @parameter expression="${project.build.directory}"
   */
  private String directory;

  public String getDirectory() {
    return directory;
  }

  /**
   * @parameter expression="${project.resources}"
   */
  private List<Resource> resources;

  public List<Resource> getResources() {
    return resources;
  }

  /**
   * @parameter expression="${project.testResources}"
   */
  private List<Resource> testResources;

  public List<Resource> getTestResources() {
    return testResources;
  }

  /**
   * @parameter default-value="" expression="${mdep.fileSeparator}"
   */
  private String fileSeparator;

  /**
   * Override the char used between the paths. This field is initialized to
   * contain the first character of the value of the system property
   * file.separator. On UNIX systems the value of this field is '/'; on
   * Microsoft Windows systems it is '\'. The default is File.separator
   */
  public String getFileSeparator() {
    return fileSeparator;
  }

  /**
   * @parameter default-value="" expression="${mdep.pathSeparator}"
   */
  private String pathSeparator;

  /**
   * Override the char used between path folders. The system-dependent
   * path-separator character. This field is initialized to contain the first
   * character of the value of the system property path.separator. This
   * character is used to separate filenames in a sequence of files given as a
   * path list. On UNIX systems, this character is ':'; on Microsoft Windows
   * systems it is ';'.
   */
  public String getPathSeparator() {
    return pathSeparator;
  }

  /**
   * @component role="org.apache.maven.artifact.factory.ArtifactFactory"
   * @required
   * @readonly
   */
  private ArtifactFactory factory;

  /**
   * Used to look up Artifacts in the remote repository.
   */
  public ArtifactFactory getFactory() {
    return factory;
  }

  /**
   * @component role="org.apache.maven.artifact.resolver.ArtifactResolver"
   * @required
   * @readonly
   */
  private ArtifactResolver resolver;

  /**
   * Used to look up Artifacts in the remote repository.
   */
  public ArtifactResolver getResolver() {
    return resolver;
  }

  /**
   * @parameter default-value="${settings.localRepository}" expression="${mdep.repositoryPath}"
   */
  private String repositoryPath;

  /**
   * The prefix to preppend on each dependent artifact. If undefined, the
   * paths refer to the actual files store in the local repository (the
   * stipVersion parameter does nothing then).
   */
  public String getRepositoryPath() {
    return repositoryPath;
  }

  /**
   * @parameter expression="${localRepository}"
   * @readonly
   * @required
   */
  private ArtifactRepository local;

  /**
   * Location of the local repository.
   */
  public ArtifactRepository getLocal() {
    return local;
  }

  /**
   * @parameter expression="${project.remoteArtifactRepositories}"
   * @readonly
   * @required
   */
  private List<ArtifactRepository> remoteRepos;

  /**
   * List of Remote Repositories used by the resolver
   */
  public List<ArtifactRepository> getRemoteRepos() {
    return remoteRepos;
  }

  /**
   * @parameter expression="${project}"
   * @readonly
   * @required
   */
  private MavenProject project;

  /**
   * POM
   */
  public MavenProject getProject() {
    return project;
  }

  /**
   * @optional
   * @parameter expression="${excludeTransitive}" default-value="false"
   */
  private boolean excludeTransitive;

  /**
   * If we should exclude transitive dependencies
   */
  public boolean getExcludeTransitive() {
    return excludeTransitive;
  }

  /**
   * @parameter expression="${includeTypes}" default-value=""
   * @optional
   */
  private String includeTypes;

  /**
   * Comma Separated list of Types to include. Empty String indicates include
   * everything (default).
   */
  public String getIncludeTypes() {
    return includeTypes;
  }

  /**
   * @parameter expression="${excludeTypes}" default-value=""
   * @optional
   */
  private String excludeTypes;

  /**
   * Comma Separated list of Types to exclude. Empty String indicates don't
   * exclude anything (default).
   */
  public String getExcludeTypes() {
    return excludeTypes;
  }

  /**
   * @parameter expression="${includeScope}" default-value=""
   * @optional
   */
  private String includeScope;

  /**
   * Scope to include. An Empty string indicates all scopes (default).
   */
  public String getIncludeScope() {
    return includeScope;
  }

  /**
   * @parameter expression="${excludeScope}" default-value=""
   * @optional
   */
  private String excludeScope;

  /**
   * Scope to exclude. An empty string indicates no scopes (default).
   */
  public String getExcludeScope() {
    return excludeScope;
  }

  /**
   * @parameter expression="${includeClassifiers}" default-value=""
   * @optional
   */
  private String includeClassifiers;

  /**
   * Comma Separated list of Classifiers to include. Empty String indicates
   * include everything (default).
   */
  public String getIncludeClassifiers() {
    return includeClassifiers;
  }

  /**
   * @parameter expression="${excludeClassifiers}" default-value=""
   * @optional
   */
  private String excludeClassifiers;

  /**
   * Comma Separated list of Classifiers to exclude. Empty String indicates
   * don't exclude anything (default).
   */
  public String getExcludeClassifiers() {
    return excludeClassifiers;
  }

  /**
   * @optional
   * @parameter expression="${classifier}" default-value=""
   */
  private String classifier;

  /**
   * Specify classifier to look for. Example: sources
   */
  public String getClassifier() {
    return classifier;
  }

  /**
   * @optional
   * @parameter expression="${type}" default-value="java-source"
   */
  private String type;

  /**
   * Specify type to look for when constructing artifact based on classifier.
   * Example: java-source,jar,war
   */
  public String getType() {
    return type;
  }

  /**
   * @optional
   * @parameter expression="${excludeArtifactIds}" default-value=""
   */
  private String excludeArtifactIds;

  /**
   * Comma separated list of Artifact names too exclude.
   */
  public String getExcludeArtifactIds() {
    return excludeArtifactIds;
  }

  /**
   * @optional
   * @parameter expression="${includeArtifactIds}" default-value=""
   */
  private String includeArtifactIds;

  /**
   * Comma separated list of Artifact names to include.
   */
  public String getIncludeArtifactIds() {
    return includeArtifactIds;
  }

  /**
   * @optional
   * @parameter expression="${excludeGroupIds}" default-value=""
   */
  private String excludeGroupIds;

  /**
   * Comma separated list of GroupId Names to exclude.
   */
  public String getExcludeGroupIds() {
    return excludeGroupIds;
  }

  /**
   * @optional
   * @parameter expression="${includeGroupIds}" default-value=""
   */
  private String includeGroupIds;

  /**
   * Comma separated list of GroupIds to include.
   */
  public String getIncludeGroupIds() {
    return includeGroupIds;
  }

  /**
   * @optional
   * @parameter expression="${excludeArtifactIds}" default-value=""
   */
  private String excludeGroupIdArtifactIds;

  public void setExcludeGroupIdArtifactIds(final String excludeGroupIdArtifactIds) {
    this.excludeGroupIdArtifactIds = excludeGroupIdArtifactIds;
  }

  /**
   * Comma separated list of Artifact names too exclude.
   */
  public String getExcludeGroupIdArtifactIds() {
    return excludeGroupIdArtifactIds;
  }

  /**
   * @optional
   * @parameter expression="${includeArtifactIds}" default-value=""
   */
  private String includeGroupIdArtifactIds;

  /**
   * Comma separated list of Artifact names to include.
   */
  public String getIncludeGroupIdArtifactIds() {
    return includeGroupIdArtifactIds;
  }
}