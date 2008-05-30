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

package org.safris.maven.plugin.codeguide.javaproj;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.maven.artifact.repository.ArtifactRepository;
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
public class JavaProjectMojo extends CodeGuideMojo
{
	private static final ClassLoaderLocal<StateManager> classLoaderLocal = new ClassLoaderLocal<StateManager>(ClassLoader.getSystemClassLoader());
	private static final FileFilter sourceFileFilter = new FileFilter()
	{
		public boolean accept(File pathname)
		{
			return pathname.getName().endsWith(".java");
		}
	};

	private static final FileFilter resourceFileFilter = new FileFilter()
	{
		private final String[] includes = new String[]
		{
			".xml",
			".xsd",
			".dtd",
			".txt"
		};

		public boolean accept(File pathname)
		{
			for(String include : includes)
				if(pathname.getName().endsWith(include))
					return true;

			return false;
		}
	};

	private Set<File> findSources()
	{
		final Set<File> sourceFiles = new HashSet<File>();

		// Find all files in the <sourceDirectory/>
		final File sourceDirectoryFile = new File(getSourceDirectory());
		final Collection<File> sourceDirectoryFiles = Files.listAll(sourceDirectoryFile, sourceFileFilter);
		if(sourceDirectoryFiles != null)
			sourceFiles.addAll(sourceDirectoryFiles);

		// Find all files in the <testSourceDirectory/>
		final File testSourceDirectoryFile = new File(getTestSourceDirectory());
		final Collection<File> testSourceFiles = Files.listAll(testSourceDirectoryFile, sourceFileFilter);
		if(testSourceFiles != null)
			sourceFiles.addAll(testSourceFiles);

		// Find all files in the <directory/>
		// TODO: This is the output dir! Should there be any more filtering here?
		final File directoryFile = new File(getDirectory());
		final Collection<File> directoryFiles = Files.listAll(directoryFile, sourceFileFilter);
		if(directoryFiles != null)
			sourceFiles.addAll(directoryFiles);

		return sourceFiles;
	}

	private Set<File> findResources()
	{
		final Set<File> resourceFiles = new HashSet<File>();

		final Collection<Resource> resources = getResources();
		final Collection<Resource> testResources = getTestResources();
		if(testResources != null)
			resources.addAll(testResources);

		for(Resource resource : resources)
		{
			final Collection<File> directoryFiles = Files.listAll(new File(resource.getDirectory()), resourceFileFilter);
			if(directoryFiles != null)
				resourceFiles.addAll(directoryFiles);
		}

		return resourceFiles;
	}

	private static Set<File> filterClasspathReferences(ArtifactRepository localRepository, String repositoryPath, Collection<GroupArtifact> dependencies, Set<GroupArtifact> excludes)
	{
		if(dependencies == null)
			return null;

		final Set<File> filteredDependencies = new HashSet<File>();
		if(excludes != null)
		{
			for(GroupArtifact dependency : dependencies)
				if(!excludes.contains(dependency))
					filteredDependencies.add(DependencyMojo.getFile(dependency, localRepository, repositoryPath));
		}
		else
		{
			for(GroupArtifact dependency : dependencies)
				filteredDependencies.add(DependencyMojo.getFile(dependency, localRepository, repositoryPath));
		}

		return filteredDependencies;
	}

	private static Set<GroupArtifact> filterProjectReferences(Collection<GroupArtifact> dependencies, Set<GroupArtifact> inlcudes, Set<GroupArtifact> excludes)
	{
		if(dependencies == null)
			return null;

		final Set<GroupArtifact> filteredReferences = new HashSet<GroupArtifact>();
		if(inlcudes != null)
		{
			if(excludes != null)
			{
				for(GroupArtifact dependency : dependencies)
					if(inlcudes.contains(dependency) && !excludes.contains(dependency))
						filteredReferences.add(dependency);
			}
			else
			{
				for(GroupArtifact dependency : dependencies)
					if(inlcudes.contains(dependency))
						filteredReferences.add(dependency);
			}
		}
		else if(excludes != null)
		{
			for(GroupArtifact dependency : dependencies)
				if(!excludes.contains(dependency))
					filteredReferences.add(dependency);
		}
		else
		{
			filteredReferences.addAll(dependencies);
		}

		return filteredReferences;
	}

	private static void resolveDependencies(ArtifactRepository localRepository, String repositoryPath, JavaProject project, StateManager stateManager)
	{
		// Filter the classpath reference by excluding the other projects
		final Set<File> filteredDependencies = filterClasspathReferences(localRepository, repositoryPath, project.getDependencies(), stateManager.getGroupArtifacts());
		project.setClasspathReferences(filteredDependencies);

		final Set<GroupArtifact> excludes = new HashSet<GroupArtifact>();
		excludes.add(stateManager.getGroupArtifact(project));

		final Set<GroupArtifact> filteredReferences = filterProjectReferences(project.getDependencies(), stateManager.getGroupArtifacts(), excludes);
		final Set<JavaProject> references = new HashSet<JavaProject>();
		for(GroupArtifact reference : filteredReferences)
			references.add(stateManager.getJavaProject(reference));

		project.setProjectReferences(references);
	}

	protected StateManager getStateManager()
	{
		StateManager stateManager;
		if((stateManager = classLoaderLocal.get()) == null)
		{
			final GroupArtifact self = new GroupArtifact(getProject().getArtifact());
			stateManager = new StateManager(new Solution(self, getProject().getFile().getParentFile()));
			classLoaderLocal.set(stateManager);
		}

		return stateManager;
	}

	public void execute() throws MojoExecutionException
	{
		// This has to be done first to initialize the StateManager.
		final StateManager stateManager = getStateManager();

		// FIXME: If a dependency is listed that points to a
		// FIXME: <packaging>pom</packaging> artifact, then this code has to
		// FIXME: properly resolve those dependencies as project references.
		if("pom".equals(getProject().getPackaging()))
			return;

		final GroupArtifact self = new GroupArtifact(getProject().getArtifact());
		final JavaProject javaProject = new JavaProject(self, getProject().getFile().getParentFile());
		stateManager.put(self, javaProject);

		// Find the project's sources, resources, and dependencies
		javaProject.setSourceFiles(findSources());
		javaProject.setResourceFiles(findResources());
		javaProject.setDependencies(DependencyMojo.getDependencies(this));

		// Cross reference the java project to the solution, and vice versa
		stateManager.getSolution().addJavaProject(javaProject);
		javaProject.setSolution(stateManager.getSolution());

		// Write the JavaProject files
		try
		{
			for(JavaProject project : stateManager.getJavaProjects())
			{
				resolveDependencies(getLocal(), getRepositoryPath(), project, stateManager);
				JavaProjectWriter.write(project);
			}
		}
		catch(Exception e)
		{
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
}
