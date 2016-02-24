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

public final class JavaProject {
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

  public JavaProject(final GroupArtifact address, final File dir) {
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

  public void setSourceFiles(final Set<File> sourceFiles) {
    this.sourceFiles = sourceFiles;
  }

  public Set<File> getSourceFiles() {
    return sourceFiles;
  }

  public void setResourceFiles(final Set<File> resourceFiles) {
    this.resourceFiles = resourceFiles;
  }

  public Set<File> getResourceFiles() {
    return resourceFiles;
  }

  public void setDependencies(final Set<GroupArtifact> dependencies) {
    this.dependencies = dependencies;
  }

  public Set<GroupArtifact> getDependencies() {
    return dependencies;
  }

  public void setClasspathReferences(final Set<File> classpathReferences) {
    this.classpathReferences = classpathReferences;
  }

  public Set<File> getClasspathReferences() {
    return classpathReferences;
  }

  public void setProjectReferences(final Set<JavaProject> projectReferences) {
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
        PackageLoader.getSystemPackageLoader().loadPackage(_javaProject3.class.getPackage().getName());
        if (javaprojXML.exists()) {
          final _javaProject3 javaProject3 = (_javaProject3)Bindings.parse(new InputSource(new FileInputStream(javaprojXML)));
          if (javaProject3 != null && javaProject3._startingPoints() != null && javaProject3._startingPoints().size() != 0)
            this.startingPoints = javaProject3._startingPoints(0)._startingPoint();
        }
      }
      catch (final Exception e) {
        throw new MojoExecutionException(e.getMessage(), e);
      }
      finally {
        startingPointsSearched = true;
      }
    }

    return startingPoints;
  }

  public void setSolution(final Solution solution) {
    this.solution = solution;
  }

  public Solution getSolution() {
    return solution;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof JavaProject))
      return false;

    final JavaProject that = (JavaProject)obj;
    return (name != null ? name.equals(that.name) : that.name == null) && (dir != null ? dir.equals(that.dir) : that.dir == null);
  }

  @Override
  public int hashCode() {
    int hashCode = 7;
    hashCode ^= name != null ? name.hashCode() : -1;
    hashCode ^= dir != null ? dir.hashCode() : -1;
    return hashCode;
  }
}