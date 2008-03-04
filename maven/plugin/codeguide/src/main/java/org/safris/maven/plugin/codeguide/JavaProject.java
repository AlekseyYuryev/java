package org.safris.maven.plugin.codeguide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import org.safris.commons.xml.validation.ValidationException;
import org.safris.ide.common.startingpoints.SpStartingPoints;
import org.safris.maven.plugin.dependency.GroupArtifact;
import org.safris.xml.generator.compiler.runtime.BindingException;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.xml.sax.InputSource;

public class JavaProject
{
	private final String name;
	private final String shortName;
	private final String uuid;
	private final File dir;
	private Set<File> sourceFiles = null;
	private Set<File> resourceFiles = null;
	private Set<GroupArtifact> dependencies = null;
	private Set<File> classpathReferences = null;
	private Set<JavaProject> projectReferences = null;
	private Collection<SpStartingPoints.SpStartingPoint> startingPoints = null;
	private boolean startingPointsSearched = false;
	private Solution solution = null;

	public JavaProject(GroupArtifact address, File dir)
	{
		this.name = address.getGroupId() + ":" + address.getArtifactId();
		this.shortName = address.getArtifactId();
		this.uuid = UUID.nameUUIDFromBytes(name.getBytes()).toString().toUpperCase();
		this.dir = dir;
	}

	public String getName()
	{
		return name;
	}

	public String getShortName()
	{
		return shortName;
	}

	public String getUUID()
	{
		return uuid;
	}

	public File getDir()
	{
		return dir;
	}

	public void setSourceFiles(Set<File> sourceFiles)
	{
		this.sourceFiles = sourceFiles;
	}

	public Set<File> getSourceFiles()
	{
		return sourceFiles;
	}

	public void setResourceFiles(Set<File> resourceFiles)
	{
		this.resourceFiles = resourceFiles;
	}

	public Set<File> getResourceFiles()
	{
		return resourceFiles;
	}

	public void setDependencies(Set<GroupArtifact> dependencies)
	{
		this.dependencies = dependencies;
	}

	public Set<GroupArtifact> getDependencies()
	{
		return dependencies;
	}

	public void setClasspathReferences(Set<File> classpathReferences)
	{
		this.classpathReferences = classpathReferences;
	}

	public Set<File> getClasspathReferences()
	{
		return classpathReferences;
	}

	public void setProjectReferences(Set<JavaProject> projectReferences)
	{
		this.projectReferences = projectReferences;
	}

	public Set<JavaProject> getProjectReferences()
	{
		return projectReferences;
	}

	// FIXME: Fix all this here...
	public Collection<SpStartingPoints.SpStartingPoint> getStartingPoints()
	{
		if(startingPointsSearched)
			return startingPoints;

		synchronized(name)
		{
			if(startingPointsSearched)
				return startingPoints;

			final File startingPointsXML = new File(getDir(), "starting-points.xml");
			try
			{
				if(startingPointsXML.exists())
				{
					final SpStartingPoints startingPoints = (SpStartingPoints)Bindings.parse(new InputSource(new FileInputStream(startingPointsXML)));
					if(startingPoints != null)
						this.startingPoints = startingPoints.getSpStartingPoint();
				}
			}
			catch(BindingException e)
			{
				System.err.println(e.getMessage());
			}
			catch(ValidationException e)
			{
				System.err.println(e.getMessage());
			}
			catch(FileNotFoundException e)
			{
				System.err.println(e.getMessage());
			}
			finally
			{
				startingPointsSearched = true;
			}
		}

		return startingPoints;
	}

	public void setSolution(Solution solution)
	{
		this.solution = solution;
	}

	public Solution getSolution()
	{
		return solution;
	}
}
