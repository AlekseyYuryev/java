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

public class JavaProjectWriter {
  public static void write(JavaProject javaProject) throws IOException, MarshalException, MojoExecutionException, XMLException {
    final _javaProject3 javaProject3 = createJavaProject(javaProject);
    final String xml = DOMs.domToString(javaProject3.marshal(), DOMStyle.INDENT, DOMStyle.IGNORE_NAMESPACES);
    final FileOutputStream out = new FileOutputStream(new File(javaProject.getDir(), javaProject.getShortName() + ".javaproj"));
    out.write(xml.getBytes());
    out.close();
  }

  private static _javaProject3 createJavaProject(JavaProject javaProject) throws MojoExecutionException {
    final _javaProject3 javaProject3 = new _javaProject3();
    javaProject3.add_buildProperties(createBuildProperties());
    javaProject3.add_guid(new _javaProject3._guid("{" + javaProject.getUUID() + "}"));
    javaProject3.add_includedFiles(createIncludedFiles(javaProject));
    javaProject3.add_name(new _javaProject3._name(javaProject.getName()));
    javaProject3.add_projectReferences(createProjectReferences(javaProject));
    javaProject3.add_references(createReferences(javaProject));
    javaProject3.add_startingPoints(createStartingPoints(javaProject));

    return javaProject3;
  }

  private static _javaProject3._buildProperties createBuildProperties() {
    final _javaProject3._buildProperties buildProperties = new _javaProject3._buildProperties();
    buildProperties.add_entry(createBuildPropertiesEntry("Release", false));
    buildProperties.add_entry(createBuildPropertiesEntry("Debug", true));

    return buildProperties;
  }

  private static _javaProject3._buildProperties._entry createBuildPropertiesEntry(String name, boolean backInTimeDebugging) {
    final _buildProperties buildProperties = new _buildProperties();
    buildProperties.add_backInTimeDebugging(new _buildProperties._backInTimeDebugging(backInTimeDebugging));
    buildProperties.add_generateLocalVariableTables(new _buildProperties._generateLocalVariableTables(true));
    buildProperties.add_obfuscateFilePrivateMembers(new _buildProperties._obfuscateFilePrivateMembers(false));
    buildProperties.add_relativeDestinationPath(new _buildProperties._relativeDestinationPath("build\\" + name));
    buildProperties.add_targetVM(new _buildProperties._targetVM("1.5"));
    buildProperties.add_useJavac(new _buildProperties._useJavac(false));

    final _javaProject3._buildProperties._entry entry = new _javaProject3._buildProperties._entry();
    entry.add_string(new _javaProject3._buildProperties._entry._string(name));
    entry.add_buildProperties(buildProperties);

    return entry;
  }

  private static final String resourceBasePackage = "src/????/resources/";
  private static final _javaProject3._includedFiles._javaFile2._buildAction COMPILE_ACTION = new _javaProject3._includedFiles._javaFile2._buildAction(_javaProject3._includedFiles._javaFile2._buildAction.COMPILE);
  private static final _javaProject3._includedFiles._javaFile2._buildAction COPY_ACTION = new _javaProject3._includedFiles._javaFile2._buildAction(_javaProject3._includedFiles._javaFile2._buildAction.COPY);

  private static _javaProject3._includedFiles createIncludedFiles(JavaProject javaProject) {
    // Add the source files
    final _javaProject3._includedFiles includedFiles = new _javaProject3._includedFiles();
    for (File sourceFile : javaProject.getSourceFiles())
      includedFiles.add_javaFile2(createJavaFile2(Files.relativePath(javaProject.getDir(), sourceFile), COMPILE_ACTION));

    // Add the resource files
    // Filter the resources and set the test resources as highest priority
    final Map<String,File> resourceFiles = new HashMap<String,File>();
    for (File resourceFile : javaProject.getResourceFiles()) {
      final String path = Files.relativePath(javaProject.getDir(), resourceFile);
      final String resourceName = path.substring(8);
      final String build = path.substring(4, 8);
      if (!resourceFiles.containsKey(resourceName) || "test".equals(build))
        resourceFiles.put(resourceName, resourceFile);
    }

    // Add the resources to the project
    for (File resourceFile : resourceFiles.values()) {
      final String dirPath = Files.relativePath(javaProject.getDir(), resourceFile.getParentFile());
      final _javaProject3._includedFiles._javaFile2 javaFile2 = createJavaFile2(Files.relativePath(javaProject.getDir(), resourceFile), COPY_ACTION);
      final String _package = dirPath.length() > resourceBasePackage.length() ? dirPath.substring(resourceBasePackage.length()) : null;
      javaFile2.add_packageNameForResource(new _javaProject3._includedFiles._javaFile2._packageNameForResource(_package));
      includedFiles.add_javaFile2(javaFile2);
    }

    return includedFiles;
  }

  private static _javaProject3._includedFiles._javaFile2 createJavaFile2(String file, _javaProject3._includedFiles._javaFile2._buildAction buildAction) {
    final _javaProject3._includedFiles._javaFile2 javaFile2 = new _javaProject3._includedFiles._javaFile2();
    javaFile2.add_buildAction(buildAction);
    javaFile2.add_incPathSpec(new _javaProject3._includedFiles._javaFile2._incPathSpec(file));

    return javaFile2;
  }

  private static _javaProject3._projectReferences createProjectReferences(JavaProject javaProject) {
    final _javaProject3._projectReferences projectReferences = new _javaProject3._projectReferences();
    if (javaProject.getProjectReferences() != null && javaProject.getProjectReferences().size() != 0)
      for (JavaProject projectReference : javaProject.getProjectReferences())
        projectReferences.add_projectReference(createProjectReference(projectReference));

    return projectReferences;
  }

  private static _javaProject3._projectReferences._projectReference createProjectReference(JavaProject javaProject) {
    final _javaProject3._projectReferences._projectReference projectReference = new _javaProject3._projectReferences._projectReference();
    projectReference.add_name(new _javaProject3._projectReferences._projectReference._name(javaProject.getName()));
    projectReference.add_packageGuid(new _javaProject3._projectReferences._projectReference._packageGuid("{" + javaProject.getSolution().getUUID() + "}"));
    projectReference.add_projectGuid(new _javaProject3._projectReferences._projectReference._projectGuid("{" + javaProject.getUUID() + "}"));

    return projectReference;
  }

  private static _javaProject3._references createReferences(JavaProject javaProject) {
    final _javaProject3._references references = new _javaProject3._references();
    for (File dependency : javaProject.getClasspathReferences())
      references.add_classpathReference(createClasspathReference(dependency));

    return references;
  }

  private static _javaProject3._references._classpathReference createClasspathReference(File dependency) {
    final _javaProject3._references._classpathReference reference = new _javaProject3._references._classpathReference();
    reference.add_relativePath(new _javaProject3._references._classpathReference._relativePath(dependency.getAbsolutePath()));

    return reference;
  }

  private static _javaProject3._startingPoints createStartingPoints(JavaProject javaProject) throws MojoExecutionException {
    final _javaProject3._startingPoints startingPoints = new _javaProject3._startingPoints();
    if (javaProject.getStartingPoints() != null)
      for (_javaProject3._startingPoints._startingPoint startingPoint : javaProject.getStartingPoints())
        startingPoints.add_startingPoint(createStartingPoint(startingPoint));

    return startingPoints;
  }

  private static _javaProject3._startingPoints._startingPoint createStartingPoint(_javaProject3._startingPoints._startingPoint startingPoint) {
    final _javaProject3._startingPoints._startingPoint point = new _javaProject3._startingPoints._startingPoint();
    point.add_commandLineArguments(new _javaProject3._startingPoints._startingPoint._commandLineArguments(startingPoint.get_commandLineArguments(0).getText()));
    point.add_name(new _javaProject3._startingPoints._startingPoint._name(startingPoint.get_name(0).getText()));
    point.add_target(new _javaProject3._startingPoints._startingPoint._target(startingPoint.get_target(0).getText()));
    point.add_vmOtherOptions(new _javaProject3._startingPoints._startingPoint._vmOtherOptions(startingPoint.get_vmOtherOptions(0).getText()));
    point.add_workingDir(new _javaProject3._startingPoints._startingPoint._workingDir(startingPoint.get_workingDir(0).getText()));

    return point;
  }
}
