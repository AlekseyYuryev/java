package org.safris.maven.plugin.codeguide;

import com.omnicore.javaproject3.JpBuildProperties;
import com.omnicore.javaproject3.JpJavaProject3;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.safris.commons.io.Files;
import org.safris.ide.common.startingpoints.SpStartingPoints;
import org.safris.xml.generator.compiler.runtime.BindingConfig;
import org.safris.xml.generator.compiler.runtime.BindingException;
import org.safris.xml.generator.compiler.runtime.Bindings;

public class JavaProjectWriter
{
	static
	{
		BindingConfig bindingConfig = new BindingConfig();
		bindingConfig.setIndent(true);
		Bindings.bootstrapConfig(bindingConfig);
	}

	public static void write(JavaProject javaProject) throws BindingException, IOException
	{
		final JpJavaProject3 javaProject3 = createJavaProject(javaProject);
		final String xml = Bindings.domToStringNoNamepsaces(javaProject3.marshal());
		final FileOutputStream out = new FileOutputStream(new File(javaProject.getDir(), javaProject.getShortName() + ".javaproj"));
		out.write(xml.getBytes());
		out.close();
	}

	private static JpJavaProject3 createJavaProject(JavaProject javaProject)
	{
		final JpJavaProject3 javaProject3 = new JpJavaProject3();
		javaProject3.setJpBuildProperties(createBuildProperties());
		javaProject3.setJpGuid(new JpJavaProject3.JpGuid("{" + javaProject.getUUID() + "}"));
		javaProject3.setJpIncludedFiles(createIncludedFiles(javaProject));
		javaProject3.setJpName(new JpJavaProject3.JpName(javaProject.getName()));
		javaProject3.setJpProjectReferences(createProjectReferences(javaProject));
		javaProject3.setJpReferences(createReferences(javaProject));
		javaProject3.setJpStartingPoints(createStartingPoints(javaProject));

		return javaProject3;
	}

	private static JpJavaProject3.JpBuildProperties createBuildProperties()
	{
		final JpJavaProject3.JpBuildProperties buildProperties = new JpJavaProject3.JpBuildProperties();
		buildProperties.addJpEntry(createBuildPropertiesEntry("Release", false));
		buildProperties.addJpEntry(createBuildPropertiesEntry("Debug", true));

		return buildProperties;
	}

	private static JpJavaProject3.JpBuildProperties.JpEntry createBuildPropertiesEntry(String name, boolean backInTimeDebugging)
	{
		final JpBuildProperties buildProperties = new JpBuildProperties();
		buildProperties.setJpBackInTimeDebugging(new JpBuildProperties.JpBackInTimeDebugging(backInTimeDebugging));
		buildProperties.setJpGenerateLocalVariableTables(new JpBuildProperties.JpGenerateLocalVariableTables(true));
		buildProperties.setJpObfuscateFilePrivateMembers(new JpBuildProperties.JpObfuscateFilePrivateMembers(false));
		buildProperties.setJpRelativeDestinationPath(new JpBuildProperties.JpRelativeDestinationPath("build\\" + name));
		buildProperties.setJpTargetVM(new JpBuildProperties.JpTargetVM("1.5"));
		buildProperties.setJpUseJavac(new JpBuildProperties.JpUseJavac(false));

		final JpJavaProject3.JpBuildProperties.JpEntry entry = new JpJavaProject3.JpBuildProperties.JpEntry();
		entry.setJpString(new JpJavaProject3.JpBuildProperties.JpEntry.JpString(name));
		entry.setJpBuildProperties(buildProperties);

		return entry;
	}

	private static JpJavaProject3.JpIncludedFiles createIncludedFiles(JavaProject javaProject)
	{
		// Add the source files
		final JpJavaProject3.JpIncludedFiles includedFiles = new JpJavaProject3.JpIncludedFiles();
		final JpJavaProject3.JpIncludedFiles.JpJavaFile2.JpBuildAction compileAction = new JpJavaProject3.JpIncludedFiles.JpJavaFile2.JpBuildAction(JpJavaProject3.JpIncludedFiles.JpJavaFile2.JpBuildAction.COMPILE);
		for(File sourceFile : javaProject.getSourceFiles())
			includedFiles.addJpJavaFile2(createJavaFile2(Files.relativePath(javaProject.getDir(), sourceFile), compileAction));

		// Add the resource files
		final JpJavaProject3.JpIncludedFiles.JpJavaFile2.JpBuildAction noneAction = new JpJavaProject3.JpIncludedFiles.JpJavaFile2.JpBuildAction(JpJavaProject3.JpIncludedFiles.JpJavaFile2.JpBuildAction.NONE);
		for(File resourceFile : javaProject.getResourceFiles())
			includedFiles.addJpJavaFile2(createJavaFile2(Files.relativePath(javaProject.getDir(), resourceFile), noneAction));

		return includedFiles;
	}

	private static JpJavaProject3.JpIncludedFiles.JpJavaFile2 createJavaFile2(String file, JpJavaProject3.JpIncludedFiles.JpJavaFile2.JpBuildAction buildAction)
	{
		final JpJavaProject3.JpIncludedFiles.JpJavaFile2 javaFile2 = new JpJavaProject3.JpIncludedFiles.JpJavaFile2();
		javaFile2.setJpBuildAction(buildAction);
		javaFile2.setJpIncPathSpec(new JpJavaProject3.JpIncludedFiles.JpJavaFile2.JpIncPathSpec(file));

		return javaFile2;
	}

	private static JpJavaProject3.JpProjectReferences createProjectReferences(JavaProject javaProject)
	{
		final JpJavaProject3.JpProjectReferences projectReferences = new JpJavaProject3.JpProjectReferences();
		if(javaProject.getProjectReferences() != null && javaProject.getProjectReferences().size() != 0)
			for(JavaProject projectReference : javaProject.getProjectReferences())
				projectReferences.addJpProjectReference(createProjectReference(projectReference));

		return projectReferences;
	}

	private static JpJavaProject3.JpProjectReferences.JpProjectReference createProjectReference(JavaProject javaProject)
	{
		final JpJavaProject3.JpProjectReferences.JpProjectReference projectReference = new JpJavaProject3.JpProjectReferences.JpProjectReference();
		projectReference.setJpName(new JpJavaProject3.JpProjectReferences.JpProjectReference.JpName(javaProject.getName()));
		projectReference.setJpPackageGuid(new JpJavaProject3.JpProjectReferences.JpProjectReference.JpPackageGuid("{" + javaProject.getSolution().getUUID() + "}"));
		projectReference.setJpProjectGuid(new JpJavaProject3.JpProjectReferences.JpProjectReference.JpProjectGuid("{" + javaProject.getUUID() + "}"));

		return projectReference;
	}

	private static JpJavaProject3.JpReferences createReferences(JavaProject javaProject)
	{
		final JpJavaProject3.JpReferences references = new JpJavaProject3.JpReferences();
		for(File dependency : javaProject.getClasspathReferences())
			references.addJpClasspathReference(createClasspathReference(dependency));

		return references;
	}

	private static JpJavaProject3.JpReferences.JpClasspathReference createClasspathReference(File dependency)
	{
		final JpJavaProject3.JpReferences.JpClasspathReference reference = new JpJavaProject3.JpReferences.JpClasspathReference();
		reference.setJpRelativePath(new JpJavaProject3.JpReferences.JpClasspathReference.JpRelativePath(dependency.getAbsolutePath()));

		return reference;
	}

	private static JpJavaProject3.JpStartingPoints createStartingPoints(JavaProject javaProject)
	{
		final JpJavaProject3.JpStartingPoints startingPoints = new JpJavaProject3.JpStartingPoints();
		if(javaProject.getStartingPoints() != null)
			for(SpStartingPoints.SpStartingPoint startingPoint : javaProject.getStartingPoints())
				startingPoints.addJpStartingPoint(createStartingPoint(startingPoint));

		return startingPoints;
	}

	private static JpJavaProject3.JpStartingPoints.JpStartingPoint createStartingPoint(SpStartingPoints.SpStartingPoint startingPoint)
	{
		final JpJavaProject3.JpStartingPoints.JpStartingPoint point = new JpJavaProject3.JpStartingPoints.JpStartingPoint();
		point.setJpCommandLineArguments(new JpJavaProject3.JpStartingPoints.JpStartingPoint.JpCommandLineArguments(startingPoint.getSpCommandLineArguments().getTEXT()));
		point.setJpName(new JpJavaProject3.JpStartingPoints.JpStartingPoint.JpName(startingPoint.getSpName().getTEXT()));
		point.setJpTarget(new JpJavaProject3.JpStartingPoints.JpStartingPoint.JpTarget(startingPoint.getSpTarget().getTEXT()));
		point.setJpVmOtherOptions(new JpJavaProject3.JpStartingPoints.JpStartingPoint.JpVmOtherOptions(startingPoint.getSpVmOtherOptions().getTEXT()));
		point.setJpWorkingDir(new JpJavaProject3.JpStartingPoints.JpStartingPoint.JpWorkingDir(startingPoint.getSpWorkingDir().getTEXT()));

		return point;
	}
}
