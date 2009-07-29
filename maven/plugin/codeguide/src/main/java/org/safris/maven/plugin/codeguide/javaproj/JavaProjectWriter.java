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

import com.omnicore.javaproject3.jp_buildProperties;
import com.omnicore.javaproject3.jp_javaProject3;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.safris.commons.io.Files;
import org.safris.commons.xml.XMLException;
import org.safris.commons.xml.dom.DOMStyle;
import org.safris.commons.xml.dom.DOMs;
import org.safris.ide.common.startingpoints.sp_startingPoints;

public class JavaProjectWriter
{
	public static void write(JavaProject javaProject) throws XMLException, IOException
	{
		final jp_javaProject3 javaProject3 = createJavaProject(javaProject);
		final String xml = DOMs.domToString(javaProject3.marshal(), DOMStyle.INDENT, DOMStyle.IGNORE_NAMESPACES);
		final FileOutputStream out = new FileOutputStream(new File(javaProject.getDir(), javaProject.getShortName() + ".javaproj"));
		out.write(xml.getBytes());
		out.close();
	}

	private static jp_javaProject3 createJavaProject(JavaProject javaProject)
	{
		final jp_javaProject3 javaProject3 = new jp_javaProject3();
		javaProject3.add_buildProperties(createBuildProperties());
		javaProject3.add_guid(new jp_javaProject3._guid("{" + javaProject.getUUID() + "}"));
		javaProject3.add_includedFiles(createIncludedFiles(javaProject));
		javaProject3.add_name(new jp_javaProject3._name(javaProject.getName()));
		javaProject3.add_projectReferences(createProjectReferences(javaProject));
		javaProject3.add_references(createReferences(javaProject));
		javaProject3.add_startingPoints(createStartingPoints(javaProject));

		return javaProject3;
	}

	private static jp_javaProject3._buildProperties createBuildProperties()
	{
		final jp_javaProject3._buildProperties buildProperties = new jp_javaProject3._buildProperties();
		buildProperties.add_entry(createBuildPropertiesEntry("Release", false));
		buildProperties.add_entry(createBuildPropertiesEntry("Debug", true));

		return buildProperties;
	}

	private static jp_javaProject3._buildProperties._entry createBuildPropertiesEntry(String name, boolean backInTimeDebugging)
	{
		final jp_buildProperties buildProperties = new jp_buildProperties();
		buildProperties.add_backInTimeDebugging(new jp_buildProperties._backInTimeDebugging(backInTimeDebugging));
		buildProperties.add_generateLocalVariableTables(new jp_buildProperties._generateLocalVariableTables(true));
		buildProperties.add_obfuscateFilePrivateMembers(new jp_buildProperties._obfuscateFilePrivateMembers(false));
		buildProperties.add_relativeDestinationPath(new jp_buildProperties._relativeDestinationPath("build\\" + name));
		buildProperties.add_targetVM(new jp_buildProperties._targetVM("1.5"));
		buildProperties.add_useJavac(new jp_buildProperties._useJavac(false));

		final jp_javaProject3._buildProperties._entry entry = new jp_javaProject3._buildProperties._entry();
		entry.add_string(new jp_javaProject3._buildProperties._entry._string(name));
		entry.addjp_buildProperties(buildProperties);

		return entry;
	}

	private static final String resourceBasePackage = "src/????/resources/";
	private static final jp_javaProject3._includedFiles._javaFile2._buildAction COMPILE_ACTION = new jp_javaProject3._includedFiles._javaFile2._buildAction(jp_javaProject3._includedFiles._javaFile2._buildAction.COMPILE);
	private static final jp_javaProject3._includedFiles._javaFile2._buildAction COPY_ACTION = new jp_javaProject3._includedFiles._javaFile2._buildAction(jp_javaProject3._includedFiles._javaFile2._buildAction.COPY);

	private static jp_javaProject3._includedFiles createIncludedFiles(JavaProject javaProject)
	{
		// Add the source files
		final jp_javaProject3._includedFiles includedFiles = new jp_javaProject3._includedFiles();
		for(File sourceFile : javaProject.getSourceFiles())
			includedFiles.add_javaFile2(createJavaFile2(Files.relativePath(javaProject.getDir(), sourceFile), COMPILE_ACTION));

		// Add the resource files
		// Filter the resources and set the test resources as highest priority
		final Map<String,File> resourceFiles = new HashMap<String,File>();
		for(File resourceFile : javaProject.getResourceFiles())
		{
			final String path = Files.relativePath(javaProject.getDir(), resourceFile);
			final String resourceName = path.substring(8);
			final String build = path.substring(4, 8);
			if(!resourceFiles.containsKey(resourceName) || "test".equals(build))
				resourceFiles.put(resourceName, resourceFile);
		}

		// Add the resources to the project
		for(File resourceFile : resourceFiles.values())
		{
			final String dirPath = Files.relativePath(javaProject.getDir(), resourceFile.getParentFile());
			final jp_javaProject3._includedFiles._javaFile2 javaFile2 = createJavaFile2(Files.relativePath(javaProject.getDir(), resourceFile), COPY_ACTION);
			final String _package = dirPath.length() > resourceBasePackage.length() ? dirPath.substring(resourceBasePackage.length()) : null;
			javaFile2.add_packageNameForResource(new jp_javaProject3._includedFiles._javaFile2._packageNameForResource(_package));
			includedFiles.add_javaFile2(javaFile2);
		}

		return includedFiles;
	}

	private static jp_javaProject3._includedFiles._javaFile2 createJavaFile2(String file, jp_javaProject3._includedFiles._javaFile2._buildAction buildAction)
	{
		final jp_javaProject3._includedFiles._javaFile2 javaFile2 = new jp_javaProject3._includedFiles._javaFile2();
		javaFile2.add_buildAction(buildAction);
		javaFile2.add_incPathSpec(new jp_javaProject3._includedFiles._javaFile2._incPathSpec(file));

		return javaFile2;
	}

	private static jp_javaProject3._projectReferences createProjectReferences(JavaProject javaProject)
	{
		final jp_javaProject3._projectReferences projectReferences = new jp_javaProject3._projectReferences();
		if(javaProject.getProjectReferences() != null && javaProject.getProjectReferences().size() != 0)
			for(JavaProject projectReference : javaProject.getProjectReferences())
				projectReferences.add_projectReference(createProjectReference(projectReference));

		return projectReferences;
	}

	private static jp_javaProject3._projectReferences._projectReference createProjectReference(JavaProject javaProject)
	{
		final jp_javaProject3._projectReferences._projectReference projectReference = new jp_javaProject3._projectReferences._projectReference();
		projectReference.add_name(new jp_javaProject3._projectReferences._projectReference._name(javaProject.getName()));
		projectReference.add_packageGuid(new jp_javaProject3._projectReferences._projectReference._packageGuid("{" + javaProject.getSolution().getUUID() + "}"));
		projectReference.add_projectGuid(new jp_javaProject3._projectReferences._projectReference._projectGuid("{" + javaProject.getUUID() + "}"));

		return projectReference;
	}

	private static jp_javaProject3._references createReferences(JavaProject javaProject)
	{
		final jp_javaProject3._references references = new jp_javaProject3._references();
		for(File dependency : javaProject.getClasspathReferences())
			references.add_classpathReference(createClasspathReference(dependency));

		return references;
	}

	private static jp_javaProject3._references._classpathReference createClasspathReference(File dependency)
	{
		final jp_javaProject3._references._classpathReference reference = new jp_javaProject3._references._classpathReference();
		reference.add_relativePath(new jp_javaProject3._references._classpathReference._relativePath(dependency.getAbsolutePath()));

		return reference;
	}

	private static jp_javaProject3._startingPoints createStartingPoints(JavaProject javaProject)
	{
		final jp_javaProject3._startingPoints startingPoints = new jp_javaProject3._startingPoints();
		if(javaProject.getStartingPoints() != null)
			for(jp_javaProject3._startingPoints._startingPoint startingPoint : javaProject.getStartingPoints())
				startingPoints.add_startingPoint(createStartingPoint(startingPoint));

		return startingPoints;
	}

	private static jp_javaProject3._startingPoints._startingPoint createStartingPoint(jp_javaProject3._startingPoints._startingPoint startingPoint)
	{
		final jp_javaProject3._startingPoints._startingPoint point = new jp_javaProject3._startingPoints._startingPoint();
		point.add_commandLineArguments(new jp_javaProject3._startingPoints._startingPoint._commandLineArguments(startingPoint.get_commandLineArguments().get(0).getText()));
		point.add_name(new jp_javaProject3._startingPoints._startingPoint._name(startingPoint.get_name().get(0).getText()));
		point.add_target(new jp_javaProject3._startingPoints._startingPoint._target(startingPoint.get_target().get(0).getText()));
		point.add_vmOtherOptions(new jp_javaProject3._startingPoints._startingPoint._vmOtherOptions(startingPoint.get_vmOtherOptions().get(0).getText()));
		point.add_workingDir(new jp_javaProject3._startingPoints._startingPoint._workingDir(startingPoint.get_workingDir().get(0).getText()));

		return point;
	}
}
