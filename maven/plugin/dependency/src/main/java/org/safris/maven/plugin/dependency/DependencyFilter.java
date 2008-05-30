/*  Copyright 2008 Safris Technologies Inc.
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

public final class DependencyFilter
{
	/**
	 * Retrieves dependencies, either direct only or all including transitive.
	 *
	 * @return	A HashSet of artifacts
	 * @throws	MojoExecutionException	if an error occured.
	 */
	public static Set<Artifact> getResolvedDependencies(DependencyProperties properties) throws MojoExecutionException
	{
		final DependencyStatusSets status = getDependencySets(properties);
		return status.getResolvedDependencies();
	}

	/**
	 * Method creates filters and filters the projects dependencies. This method
	 * also transforms the dependencies if classifier is set. The dependencies
	 * are filtered in least specific to most specific order
	 *
	 * @param	stopOnFailure
	 * @return	DependencyStatusSets - Bean of TreeSets that contains information
	 *			on the projects dependencies
	 * @throws	MojoExecutionException
	 */
	protected static DependencyStatusSets getDependencySets(DependencyProperties properties) throws MojoExecutionException
	{
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
		if(StringUtils.isNotEmpty(properties.getClassifier()))
			status = getClassifierTranslatedDependencies(artifacts, properties);
		else
			status = filterMarkedDependencies(artifacts, properties.getLog());

		return status;
	}

	/**
	 * Transform artifacts
	 *
	 * @param	artifacts
	 * @param	stopOnFailure
	 * @return	DependencyStatusSets - Bean of TreeSets that contains
	 *			information on the projects dependencies
	 * @throws	MojoExecutionException
	 */
	protected static DependencyStatusSets getClassifierTranslatedDependencies(Set<Artifact> artifacts, DependencyProperties properties) throws MojoExecutionException
	{
		final Set<Artifact> unResolvedArtifacts = new HashSet<Artifact>();
		Set<Artifact> resolvedArtifacts = artifacts;
		DependencyStatusSets status = new DependencyStatusSets();

		// possibly translate artifacts into a new set of artifacts based on the
		// classifier and type
		// if this did something, we need to resolve the new artifacts
		if(StringUtils.isNotEmpty(properties.getClassifier()))
		{
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
	 * @param	artifacts
	 * @return	DependencyStatusSets
	 * @throws	MojoExecutionException
	 */
	protected static DependencyStatusSets filterMarkedDependencies(Set<Artifact> artifacts, Log log) throws MojoExecutionException
	{
		// remove files that have markers already
		final FilterArtifacts filter = new FilterArtifacts();
		filter.clearFilters();
//		filter.addFilter(getMarkedArtifactFilter());

		final Set<Artifact> unMarkedArtifacts = filter.filter(artifacts, log);

		// calculate the skipped artifacts
		final Set<Artifact> skippedArtifacts = new HashSet<Artifact>();
		skippedArtifacts.addAll(artifacts);
		skippedArtifacts.removeAll(unMarkedArtifacts);

		return new DependencyStatusSets(unMarkedArtifacts, null, skippedArtifacts);
	}

	private DependencyFilter()
	{
	}
}
