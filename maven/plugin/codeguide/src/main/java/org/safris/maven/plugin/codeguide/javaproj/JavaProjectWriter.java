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

package org.safris.maven.plugin.codeguide.javaproj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import noNamespace._buildProperties;
import noNamespace._javaProject3;

import org.apache.maven.plugin.MojoExecutionException;
import org.safris.commons.io.Files;
import org.safris.commons.xml.XMLException;
import org.safris.commons.xml.dom.DOMStyle;
import org.safris.commons.xml.dom.DOMs;
import org.safris.xml.generator.compiler.runtime.MarshalException;

public final class JavaProjectWriter {
  public static void write(final JavaProject javaProject) throws IOException, MarshalException, MojoExecutionException, XMLException {
    final _javaProject3 javaProject3 = createJavaProject(javaProject);
    final String xml = DOMs.domToString(javaProject3.marshal(), DOMStyle.INDENT, DOMStyle.IGNORE_NAMESPACES);
    final FileOutputStream out = new FileOutputStream(new File(javaProject.getDir(), javaProject.getShortName() + ".javaproj"));
    out.write(xml.getBytes());
    out.close();
  }

  private static _javaProject3 createJavaProject(final JavaProject javaProject) throws MojoExecutionException {
    final _javaProject3 javaProject3 = new _javaProject3();
    javaProject3._buildProperties(createBuildProperties());
    javaProject3._guid(new _javaProject3._guid("{" + javaProject.getUUID() + "}"));
    javaProject3._includedFiles(createIncludedFiles(javaProject));
    javaProject3._name(new _javaProject3._name(javaProject.getName()));
    javaProject3._projectReferences(createProjectReferences(javaProject));
    javaProject3._references(createReferences(javaProject));
    javaProject3._startingPoints(createStartingPoints(javaProject));

    return javaProject3;
  }

  private static _javaProject3._buildProperties createBuildProperties() {
    final _javaProject3._buildProperties buildProperties = new _javaProject3._buildProperties();
    buildProperties._entry(createBuildPropertiesEntry("Release", false));
    buildProperties._entry(createBuildPropertiesEntry("Debug", true));

    return buildProperties;
  }

  private static _javaProject3._buildProperties._entry createBuildPropertiesEntry(final String name, final boolean backInTimeDebugging) {
    final _buildProperties buildProperties = new _buildProperties();
    buildProperties._backInTimeDebugging(new _buildProperties._backInTimeDebugging(backInTimeDebugging));
    buildProperties._generateLocalVariableTables(new _buildProperties._generateLocalVariableTables(true));
    buildProperties._obfuscateFilePrivateMembers(new _buildProperties._obfuscateFilePrivateMembers(false));
    buildProperties._relativeDestinationPath(new _buildProperties._relativeDestinationPath("build\\" + name));
    buildProperties._targetVM(new _buildProperties._targetVM("1.5"));
    buildProperties._useJavac(new _buildProperties._useJavac(false));

    final _javaProject3._buildProperties._entry entry = new _javaProject3._buildProperties._entry();
    entry._string(new _javaProject3._buildProperties._entry._string(name));
    entry._buildProperties(buildProperties);

    return entry;
  }

  private static final String resourceBasePackage = "src/????/resources/";
  private static final _javaProject3._includedFiles._javaFile2._buildAction COMPILE_ACTION = new _javaProject3._includedFiles._javaFile2._buildAction(_javaProject3._includedFiles._javaFile2._buildAction.COMPILE);
  private static final _javaProject3._includedFiles._javaFile2._buildAction COPY_ACTION = new _javaProject3._includedFiles._javaFile2._buildAction(_javaProject3._includedFiles._javaFile2._buildAction.COPY);

  private static _javaProject3._includedFiles createIncludedFiles(final JavaProject javaProject) {
    // Add the source files
    final _javaProject3._includedFiles includedFiles = new _javaProject3._includedFiles();
    for (final File sourceFile : javaProject.getSourceFiles())
      includedFiles._javaFile2(createJavaFile2(Files.relativePath(javaProject.getDir(), sourceFile), COMPILE_ACTION));

    // Add the resource files
    // Filter the resources and set the test resources as highest priority
    final Map<String,File> resourceFiles = new HashMap<String,File>();
    for (final File resourceFile : javaProject.getResourceFiles()) {
      final String path = Files.relativePath(javaProject.getDir(), resourceFile);
      final String resourceName = path.substring(8);
      final String build = path.substring(4, 8);
      if (!resourceFiles.containsKey(resourceName) || "test".equals(build))
        resourceFiles.put(resourceName, resourceFile);
    }

    // Add the resources to the project
    for (final File resourceFile : resourceFiles.values()) {
      final String dirPath = Files.relativePath(javaProject.getDir(), resourceFile.getParentFile());
      final _javaProject3._includedFiles._javaFile2 javaFile2 = createJavaFile2(Files.relativePath(javaProject.getDir(), resourceFile), COPY_ACTION);
      final String _package = dirPath.length() > resourceBasePackage.length() ? dirPath.substring(resourceBasePackage.length()) : null;
      javaFile2._packageNameForResource(new _javaProject3._includedFiles._javaFile2._packageNameForResource(_package));
      includedFiles._javaFile2(javaFile2);
    }

    return includedFiles;
  }

  private static _javaProject3._includedFiles._javaFile2 createJavaFile2(final String file, final _javaProject3._includedFiles._javaFile2._buildAction buildAction) {
    final _javaProject3._includedFiles._javaFile2 javaFile2 = new _javaProject3._includedFiles._javaFile2();
    javaFile2._buildAction(buildAction);
    javaFile2._incPathSpec(new _javaProject3._includedFiles._javaFile2._incPathSpec(file));

    return javaFile2;
  }

  private static _javaProject3._projectReferences createProjectReferences(final JavaProject javaProject) {
    final _javaProject3._projectReferences projectReferences = new _javaProject3._projectReferences();
    if (javaProject.getProjectReferences() != null && javaProject.getProjectReferences().size() != 0)
      for (final JavaProject projectReference : javaProject.getProjectReferences())
        projectReferences._projectReference(createProjectReference(projectReference));

    return projectReferences;
  }

  private static _javaProject3._projectReferences._projectReference createProjectReference(final JavaProject javaProject) {
    final _javaProject3._projectReferences._projectReference projectReference = new _javaProject3._projectReferences._projectReference();
    projectReference._name(new _javaProject3._projectReferences._projectReference._name(javaProject.getName()));
    projectReference._packageGuid(new _javaProject3._projectReferences._projectReference._packageGuid("{" + javaProject.getSolution().getUUID() + "}"));
    projectReference._projectGuid(new _javaProject3._projectReferences._projectReference._projectGuid("{" + javaProject.getUUID() + "}"));

    return projectReference;
  }

  private static _javaProject3._references createReferences(final JavaProject javaProject) {
    final _javaProject3._references references = new _javaProject3._references();
    for (final File dependency : javaProject.getClasspathReferences())
      references._classpathReference(createClasspathReference(dependency));

    return references;
  }

  private static _javaProject3._references._classpathReference createClasspathReference(final File dependency) {
    final _javaProject3._references._classpathReference reference = new _javaProject3._references._classpathReference();
    reference._relativePath(new _javaProject3._references._classpathReference._relativePath(dependency.getAbsolutePath()));

    return reference;
  }

  private static _javaProject3._startingPoints createStartingPoints(final JavaProject javaProject) throws MojoExecutionException {
    final _javaProject3._startingPoints startingPoints = new _javaProject3._startingPoints();
    if (javaProject.getStartingPoints() != null)
      for (final _javaProject3._startingPoints._startingPoint startingPoint : javaProject.getStartingPoints())
        startingPoints._startingPoint(createStartingPoint(startingPoint));

    return startingPoints;
  }

  private static _javaProject3._startingPoints._startingPoint createStartingPoint(final _javaProject3._startingPoints._startingPoint startingPoint) {
    final _javaProject3._startingPoints._startingPoint point = new _javaProject3._startingPoints._startingPoint();
    point._commandLineArguments(new _javaProject3._startingPoints._startingPoint._commandLineArguments(startingPoint._commandLineArguments(0).text()));
    point._name(new _javaProject3._startingPoints._startingPoint._name(startingPoint._name(0).text()));
    point._target(new _javaProject3._startingPoints._startingPoint._target(startingPoint._target(0).text()));
    point._vmOtherOptions(new _javaProject3._startingPoints._startingPoint._vmOtherOptions(startingPoint._vmOtherOptions(0).text()));
    point._workingDir(new _javaProject3._startingPoints._startingPoint._workingDir(startingPoint._workingDir(0).text()));

    return point;
  }
}