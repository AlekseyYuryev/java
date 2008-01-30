package org.safris.xml.toolkit.binding;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.safris.commons.util.Files;
import org.safris.commons.util.Jar;
import org.safris.commons.util.JavaCompiler;
import org.safris.commons.util.Resources;
import org.safris.commons.util.Streams;
import org.safris.commons.util.URLs;
import org.safris.commons.util.logging.ExitSevereError;
import org.safris.commons.util.xml.NamespaceURI;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.lexer.phase.composite.SchemaComposite;
import org.safris.xml.generator.lexer.phase.composite.SchemaModelComposite;
import org.safris.xml.generator.module.phase.GeneratorContext;
import org.safris.xml.generator.module.phase.ElementModule;
import org.safris.xml.generator.module.phase.ProcessorDirectory;
import org.safris.xml.generator.module.phase.ModuleProcessor;
import org.safris.xml.toolkit.binding.Bundle;

public final class BundlePhase extends ModuleProcessor<SchemaComposite, Bundle> implements ElementModule<Bundle>
{
	protected BundlePhase()
	{
	}

	private static void compile(File destDir) throws Throwable
	{
		final Collection<File> javaFiles = Files.listAll(destDir);
		final Collection<File> javaSources = new ArrayList<File>();
		for(File javaFile : javaFiles)
		{
			if(javaFile.isDirectory() || !javaFile.getName().endsWith(".java"))
				continue;

			javaSources.add(javaFile);
		}

		Collection<File> classpath = null;
		final File locationBase = Resources.getLocationBase(Binding.class);
		if(locationBase != null)
			classpath = Arrays.<File>asList(new File[]{locationBase});

		new JavaCompiler(destDir, classpath).compile(javaSources);
	}

	private static Collection<File> jar(File destDir, Collection<SchemaComposite> schemaComposites) throws Exception
	{
		final Map<String,Jar> packageToJar = new HashMap<String,Jar>();
		final Map<NamespaceURI,SchemaModelComposite> namespaceToSchemaComposite = new HashMap<NamespaceURI,SchemaModelComposite>();
		final Map<NamespaceURI,String> namespaceToFileName = new HashMap<NamespaceURI,String>();

		for(SchemaComposite schemaComposite : schemaComposites)
		{
			final SchemaModelComposite schemaModelComposite = (SchemaModelComposite)schemaComposite;
			// The order of the schemas in schemaDocuments is specific! With it we know
			// which schemas originate the targetNamespace. This is important because
			// included schemas have had their targetNamespace changed to the particular
			// namespace for which they are included.
			if(namespaceToSchemaComposite.containsKey(schemaModelComposite.getSchemaDocument().getSchemaReference().getNamespaceURI()))
				continue;

			namespaceToSchemaComposite.put(schemaModelComposite.getSchemaDocument().getSchemaReference().getNamespaceURI(), schemaModelComposite);
			namespaceToFileName.put(schemaModelComposite.getSchemaDocument().getSchemaReference().getNamespaceURI(), schemaModelComposite.getSchemaModel().getTargetNamespaceSchemaLocationName());
		}

		final Collection<File> jars = new HashSet<File>();
		final Collection<File> files = Files.listAll(destDir);
		for(File file : files)
		{
			if(file.isDirectory() || (!file.getName().endsWith(".java") && !file.getName().endsWith(".class")))
				continue;

			String pkgDir = Files.relativePath(destDir.getAbsoluteFile(), file.getAbsoluteFile());
			pkgDir = pkgDir.substring(0, pkgDir.lastIndexOf(File.separator));
			String pkg = pkgDir.replace(File.separatorChar, '.');
			Jar jar = packageToJar.get(pkg);
			if(jar == null)
			{
				SchemaModelComposite schemaComposite = null;
				NamespaceURI namespaceURI = null;
				for(Map.Entry<NamespaceURI,SchemaModelComposite> entry : namespaceToSchemaComposite.entrySet())
				{
					if(pkg.equals(entry.getKey().getPackageName().toString()))
					{
						namespaceURI = entry.getKey();
						schemaComposite = entry.getValue();
					}
				}

				if(schemaComposite == null)
					throw new CompilerError("Cant resolve url by its namespaceURI: {" + namespaceURI + "}. This really shouldn't happen!");

				URL url = schemaComposite.getSchemaDocument().getSchemaReference().getURL();

				String fileName = namespaceToFileName.get(namespaceURI);
				int dot = fileName.lastIndexOf('.');
				String simpleName = fileName;
				if(dot != -1)
					simpleName = fileName.substring(0, dot);

				final File jarFile = new File(destDir, simpleName + ".jar");
				if(jarFile.exists())
					if(!jarFile.delete())
						throw new ExitSevereError("Unable to delete the existing jar: " + jarFile.getAbsolutePath());

				jars.add(jarFile);

				jar = new Jar(jarFile);
				packageToJar.put(pkg, jar);

				// first we include the schema that was used to create the source
				byte[] bytes = Streams.getBytes(url.openStream());

				// Write the schema to disk
//				Files.writeFile(new File(file.getParentFile(), fileName), bytes);

				fileName = pkgDir + File.separator + fileName;
				jar.addEntry(fileName, bytes);

				final Collection<URL> includes = schemaComposite.getSchemaDocument().getIncludes();
				if(includes != null)
				{
					for(URL include : includes)
					{
						fileName = URLs.getName(include);
						bytes = Streams.getBytes(include.openStream());

						// Write the schema to disk too
//						Files.writeFile(new File(file.getParentFile(), fileName), bytes);

						fileName = pkgDir + File.separator + fileName;
						jar.addEntry(fileName, bytes);
					}
				}
			}

			final String fileName = Files.relativePath(destDir.getAbsoluteFile(), file.getAbsoluteFile());
			final byte[] bytes = Files.getBytes(file);
			jar.addEntry(fileName, bytes);
		}

		// Finalize the jar files.
		for(Jar jar : packageToJar.values())
			jar.close();

		return jars;
	}

	public Collection<Bundle> process(Collection<SchemaComposite> documents, GeneratorContext generatorContext, ProcessorDirectory<SchemaComposite, Bundle> directory)
	{
		try
		{
			BundlePhase.compile(generatorContext.getDestDir());
			final Collection<File> jarFiles = BundlePhase.jar(generatorContext.getDestDir(), documents);
			final Collection<Bundle> bundles = new ArrayList<Bundle>(jarFiles.size());
			for(File jarFile : jarFiles)
				bundles.add(new Bundle(jarFile));

			// If we dont care about the exploded files,
			// then delete all of the files only leaving the jars.
			if(!generatorContext.getExplodeJars())
			{
				final FileFilter jarFilter = new FileFilter()
				{
					public boolean accept(File pathname)
					{
						return !pathname.getName().endsWith(".jar");
					}
				};

				final Collection<File> files = Files.listAll(generatorContext.getDestDir(), jarFilter);
				for(File file : files)
					Files.deleteAllOnExit(file);
			}

			return bundles;
		}
		catch(Throwable e)
		{
			throw new CompilerError(e);
		}
	}
}
