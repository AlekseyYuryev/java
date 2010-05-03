/*  Copyright 2010 Safris Technologies Inc.
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
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import noNamespace._javaProject3;
import org.apache.maven.plugin.MojoExecutionException;
import org.safris.commons.lang.PackageLoader;
import org.safris.maven.plugin.codeguide.sln.Solution;
import org.safris.maven.plugin.dependency.GroupArtifact;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.xml.sax.InputSource;

public class JavaProject {
    private final String name;
    private final String shortName;
    private final String uuid;
    private final File dir;
    private Set<File> sourceFiles = null;
    private Set<File> resourceFiles = null;
    private Set<GroupArtifact> dependencies = null;
    private Set<File> classpathReferences = null;
    private Set<JavaProject> projectReferences = null;
    private Collection<_javaProject3._startingPoints._startingPoint> startingPoints = null;
    private boolean startingPointsSearched = false;
    private Solution solution = null;

    public JavaProject(GroupArtifact address, File dir) {
        this.name = address.getGroupId() + ":" + address.getArtifactId();
        this.shortName = address.getArtifactId();
        this.uuid = UUID.nameUUIDFromBytes(name.getBytes()).toString().toUpperCase();
        this.dir = dir;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getUUID() {
        return uuid;
    }

    public File getDir() {
        return dir;
    }

    public void setSourceFiles(Set<File> sourceFiles) {
        this.sourceFiles = sourceFiles;
    }

    public Set<File> getSourceFiles() {
        return sourceFiles;
    }

    public void setResourceFiles(Set<File> resourceFiles) {
        this.resourceFiles = resourceFiles;
    }

    public Set<File> getResourceFiles() {
        return resourceFiles;
    }

    public void setDependencies(Set<GroupArtifact> dependencies) {
        this.dependencies = dependencies;
    }

    public Set<GroupArtifact> getDependencies() {
        return dependencies;
    }

    public void setClasspathReferences(Set<File> classpathReferences) {
        this.classpathReferences = classpathReferences;
    }

    public Set<File> getClasspathReferences() {
        return classpathReferences;
    }

    public void setProjectReferences(Set<JavaProject> projectReferences) {
        this.projectReferences = projectReferences;
    }

    public Set<JavaProject> getProjectReferences() {
        return projectReferences;
    }

    // FIXME: Fix all this here...
    public Collection<_javaProject3._startingPoints._startingPoint> getStartingPoints() throws MojoExecutionException {
        if (startingPointsSearched)
            return startingPoints;

        synchronized (name) {
            if (startingPointsSearched)
                return startingPoints;

            final File javaprojXML = new File(getDir(), getShortName() + ".javaproj");
            try {
				final Set<Class<?>> classes = PackageLoader.getSystemPackageLoader().loadPackage(_javaProject3.class.getPackage().getName());
                if (javaprojXML.exists()) {
                    final _javaProject3 javaProject3 = (_javaProject3)Bindings.parse(new InputSource(new FileInputStream(javaprojXML)));
                    if (javaProject3 != null && javaProject3.get_startingPoints() != null && javaProject3.get_startingPoints().size() != 0)
                        this.startingPoints = javaProject3.get_startingPoints().get(0).get_startingPoint();
                }
            }
            catch (Exception e) {
				throw new MojoExecutionException(e.getMessage(), e);
            }
            finally {
                startingPointsSearched = true;
            }
        }

        return startingPoints;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public Solution getSolution() {
        return solution;
    }

	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if(!(obj instanceof JavaProject))
			return false;

		final JavaProject that = (JavaProject)obj;
		return (name != null ? name.equals(that.name) : that.name == null) && (dir != null ? dir.equals(that.dir) : that.dir == null);
	}

	public int hashCode() {
		int hashCode = 7;
		hashCode ^= name != null ? name.hashCode() : -1;
		hashCode ^= dir != null ? dir.hashCode() : -1;
		return hashCode;
	}
}
