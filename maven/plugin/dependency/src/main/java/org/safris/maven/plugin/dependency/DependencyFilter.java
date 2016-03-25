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

import java.util.HashSet;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.dependency.utils.DependencyStatusSets;
import org.apache.maven.plugin.dependency.utils.resolvers.ArtifactsResolver;
import org.apache.maven.plugin.dependency.utils.resolvers.DefaultArtifactsResolver;
import org.apache.maven.plugin.dependency.utils.translators.ArtifactTranslator;
import org.apache.maven.plugin.dependency.utils.translators.ClassifierTypeTranslator;
import org.apache.maven.shared.artifact.filter.collection.ArtifactFilterException;
import org.apache.maven.shared.artifact.filter.collection.ArtifactIdFilter;
import org.apache.maven.shared.artifact.filter.collection.ClassifierFilter;
import org.apache.maven.shared.artifact.filter.collection.FilterArtifacts;
import org.apache.maven.shared.artifact.filter.collection.GroupIdFilter;
import org.apache.maven.shared.artifact.filter.collection.ScopeFilter;
import org.apache.maven.shared.artifact.filter.collection.TypeFilter;
import org.codehaus.plexus.util.StringUtils;
import org.safris.maven.plugin.dependency.filter.GroupIdArtifactIdFilter;

public final class DependencyFilter {
  /**
   * Retrieves dependencies, either direct only or all including transitive.
   *
   * @return  A HashSet of artifacts
   * @throws  MojoExecutionException  if an error occurred.
   */
  public static Set<Artifact> getResolvedDependencies(final DependencyProperties properties) throws MojoExecutionException {
    final DependencyStatusSets status = getDependencySets(properties);
    return status.getResolvedDependencies();
  }

  /**
   * Method creates filters and filters the projects dependencies. This method
   * also transforms the dependencies if classifier is set. The dependencies
   * are filtered in least specific to most specific order
   *
   * @param   stopOnFailure
   * @return  DependencyStatusSets - Bean of TreeSets that contains information
   *          on the projects dependencies
   * @throws  MojoExecutionException
   * @throws  ArtifactFilterException
   */
  protected static DependencyStatusSets getDependencySets(final DependencyProperties properties) throws MojoExecutionException {
    // start with all artifacts.
    Set<Artifact> artifacts = new HashSet<Artifact>(properties.getProject().getArtifacts());

    // add filters in well known order, least specific to most specific
    final FilterArtifacts filter = new FilterArtifacts();
    filter.addFilter(new ScopeFilter(properties.getIncludeScope(), properties.getExcludeScope()));
    filter.addFilter(new TypeFilter(properties.getIncludeTypes(), properties.getExcludeTypes()));
    filter.addFilter(new ClassifierFilter(properties.getIncludeClassifiers(), properties.getExcludeClassifiers()));
    filter.addFilter(new GroupIdFilter(properties.getIncludeGroupIds(), properties.getExcludeGroupIds()));
    filter.addFilter(new ArtifactIdFilter(properties.getIncludeArtifactIds(), properties.getExcludeArtifactIds()));
    filter.addFilter(new GroupIdArtifactIdFilter(properties.getIncludeGroupIdArtifactIds(), properties.getExcludeGroupIdArtifactIds()));

    // perform filtering
    try {
      artifacts = filter.filter(artifacts);
      artifacts.add(properties.getProject().getArtifact());
      // transform artifacts if classifier is set
      return StringUtils.isNotEmpty(properties.getClassifier()) ? getClassifierTranslatedDependencies(artifacts, properties) : filterMarkedDependencies(artifacts);
    }
    catch (final ArtifactFilterException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }

  /**
   * Transform artifacts
   *
   * @param   artifacts
   * @param   stopOnFailure
   * @return  DependencyStatusSets - Bean of TreeSets that contains
   *          information on the projects dependencies
   * @throws  MojoExecutionException
   * @throws  ArtifactFilterException
   */
  protected static DependencyStatusSets getClassifierTranslatedDependencies(Set<Artifact> artifacts, final DependencyProperties properties) throws ArtifactFilterException, MojoExecutionException {
    final Set<Artifact> unResolvedArtifacts = new HashSet<Artifact>();
    Set<Artifact> resolvedArtifacts = artifacts;
    DependencyStatusSets status = new DependencyStatusSets();

    // possibly translate artifacts into a new set of artifacts based on the
    // classifier and type
    // if this did something, we need to resolve the new artifacts
    if (StringUtils.isNotEmpty(properties.getClassifier())) {
      final ArtifactTranslator artifactTranslator = new ClassifierTypeTranslator(properties.getClassifier(), properties.getType(), properties.getFactory());
      artifacts = artifactTranslator.translate(artifacts, properties.getLog());

      status = filterMarkedDependencies(artifacts);

      // the not skipped artifacts are in the resolved set.
      artifacts = status.getResolvedDependencies();

      // resolve the rest of the artifacts
      final ArtifactsResolver artifactsResolver = new DefaultArtifactsResolver(properties.getResolver(), properties.getLocal(), properties.getRemoteRepos(), true);
      resolvedArtifacts = artifactsResolver.resolve(artifacts, properties.getLog());

      // calculate the artifacts not resolved.
      unResolvedArtifacts.addAll(artifacts);
      unResolvedArtifacts.removeAll(resolvedArtifacts);
    }

    // return a bean of all 3 sets.
    status.setResolvedDependencies(resolvedArtifacts);
    status.setUnResolvedDependencies(unResolvedArtifacts);

    return status;
  }

  /**
   * Filter the marked dependencies
   *
   * @param   artifacts
   * @return  DependencyStatusSets
   * @throws  MojoExecutionException
   * @throws ArtifactFilterException
   */
  protected static DependencyStatusSets filterMarkedDependencies(final Set<Artifact> artifacts) throws ArtifactFilterException, MojoExecutionException {
    // remove files that have markers already
    final FilterArtifacts filter = new FilterArtifacts();
    filter.clearFilters();
//      filter.addFilter(getMarkedArtifactFilter());

    final Set<Artifact> unMarkedArtifacts = filter.filter(artifacts);

    // calculate the skipped artifacts
    final Set<Artifact> skippedArtifacts = new HashSet<Artifact>();
    skippedArtifacts.addAll(artifacts);
    skippedArtifacts.removeAll(unMarkedArtifacts);

    return new DependencyStatusSets(unMarkedArtifacts, null, skippedArtifacts);
  }

  private DependencyFilter() {
  }
}