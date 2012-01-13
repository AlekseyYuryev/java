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

package org.safris.maven.plugin.dependency;

import java.util.HashSet;
import java.util.Set;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.dependency.utils.DependencyStatusSets;
import org.apache.maven.plugin.dependency.utils.filters.ArtifactIdFilter;
import org.apache.maven.plugin.dependency.utils.filters.ClassifierFilter;
import org.apache.maven.plugin.dependency.utils.filters.FilterArtifacts;
import org.apache.maven.plugin.dependency.utils.filters.GroupIdFilter;
import org.apache.maven.plugin.dependency.utils.filters.ScopeFilter;
import org.apache.maven.plugin.dependency.utils.filters.TransitivityFilter;
import org.apache.maven.plugin.dependency.utils.filters.TypeFilter;
import org.apache.maven.plugin.dependency.utils.resolvers.ArtifactsResolver;
import org.apache.maven.plugin.dependency.utils.resolvers.DefaultArtifactsResolver;
import org.apache.maven.plugin.dependency.utils.translators.ArtifactTranslator;
import org.apache.maven.plugin.dependency.utils.translators.ClassifierTypeTranslator;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.StringUtils;
import org.safris.maven.plugin.dependency.filter.GroupIdArtifactIdFilter;

public final class DependencyFilter {
  /**
   * Retrieves dependencies, either direct only or all including transitive.
   *
   * @return  A HashSet of artifacts
   * @throws  MojoExecutionException  if an error occured.
   */
  public static Set<Artifact> getResolvedDependencies(DependencyProperties properties) throws MojoExecutionException {
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
   */
  protected static DependencyStatusSets getDependencySets(DependencyProperties properties) throws MojoExecutionException {
    // start with all artifacts.
    Set<Artifact> artifacts = new HashSet<Artifact>(properties.getProject().getArtifacts());

    // add filters in well known order, lezast specific to most specific
    final FilterArtifacts filter = new FilterArtifacts();
    filter.addFilter(new TransitivityFilter(properties.getProject().getDependencyArtifacts(), properties.getExcludeTransitive()));
    filter.addFilter(new ScopeFilter(properties.getIncludeScope(), properties.getExcludeScope()));
    filter.addFilter(new TypeFilter(properties.getIncludeTypes(), properties.getExcludeTypes()));
    filter.addFilter(new ClassifierFilter(properties.getIncludeClassifiers(), properties.getExcludeClassifiers()));
    filter.addFilter(new GroupIdFilter(properties.getIncludeGroupIds(), properties.getExcludeGroupIds()));
    filter.addFilter(new ArtifactIdFilter(properties.getIncludeArtifactIds(), properties.getExcludeArtifactIds()));
    filter.addFilter(new GroupIdArtifactIdFilter(properties.getIncludeGroupIdArtifactIds(), properties.getExcludeGroupIdArtifactIds()));

    // perform filtering
    artifacts = filter.filter(artifacts, properties.getLog());
    artifacts.add(properties.getProject().getArtifact());
    // transform artifacts if classifier is set
    DependencyStatusSets status = null;
    if (StringUtils.isNotEmpty(properties.getClassifier()))
      status = getClassifierTranslatedDependencies(artifacts, properties);
    else
      status = filterMarkedDependencies(artifacts, properties.getLog());

    return status;
  }

  /**
   * Transform artifacts
   *
   * @param   artifacts
   * @param   stopOnFailure
   * @return  DependencyStatusSets - Bean of TreeSets that contains
   *          information on the projects dependencies
   * @throws  MojoExecutionException
   */
  protected static DependencyStatusSets getClassifierTranslatedDependencies(Set<Artifact> artifacts, DependencyProperties properties) throws MojoExecutionException {
    final Set<Artifact> unResolvedArtifacts = new HashSet<Artifact>();
    Set<Artifact> resolvedArtifacts = artifacts;
    DependencyStatusSets status = new DependencyStatusSets();

    // possibly translate artifacts into a new set of artifacts based on the
    // classifier and type
    // if this did something, we need to resolve the new artifacts
    if (StringUtils.isNotEmpty(properties.getClassifier())) {
      final ArtifactTranslator artifactTranslator = new ClassifierTypeTranslator(properties.getClassifier(), properties.getType(), properties.getFactory());
      artifacts = artifactTranslator.translate(artifacts, properties.getLog());

      status = filterMarkedDependencies(artifacts, properties.getLog());

      // the unskipped artifacts are in the resolved set.
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
   */
  protected static DependencyStatusSets filterMarkedDependencies(Set<Artifact> artifacts, Log log) throws MojoExecutionException {
    // remove files that have markers already
    final FilterArtifacts filter = new FilterArtifacts();
    filter.clearFilters();
//      filter.addFilter(getMarkedArtifactFilter());

    final Set<Artifact> unMarkedArtifacts = filter.filter(artifacts, log);

    // calculate the skipped artifacts
    final Set<Artifact> skippedArtifacts = new HashSet<Artifact>();
    skippedArtifacts.addAll(artifacts);
    skippedArtifacts.removeAll(unMarkedArtifacts);

    return new DependencyStatusSets(unMarkedArtifacts, null, skippedArtifacts);
  }

  private DependencyFilter() {
  }
}
