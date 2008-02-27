package org.safris.xml.toolkit.processor.bundle;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.safris.commons.io.Files;
import org.safris.commons.io.Streams;
import org.safris.commons.jci.JavaCompiler;
import org.safris.commons.lang.Resources;
import org.safris.commons.net.URLs;
import org.safris.commons.util.jar.Jar;
import org.safris.commons.xml.NamespaceURI;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingError;
import org.safris.xml.generator.lexer.processor.composite.SchemaComposite;
import org.safris.xml.generator.lexer.processor.composite.SchemaModelComposite;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;

public final class BundleProcessor implements ElementModule<Bundle>, ModuleProcessor<SchemaComposite,Bundle>
{
	protected BundleProcessor()
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
		final Collection<String> packagePaths = new ArrayList<String>();

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
			packagePaths.add(schemaModelComposite.getSchemaDocument().getSchemaReference().getNamespaceURI().getPackageName().toString().replace('.', File.separatorChar));
		}

		final Collection<File> files = new ArrayList<File>();
		for(String packagePath : packagePaths)
		{
			final Collection<File> list = Files.listAll(new File(destDir, packagePath));
			if(list != null)
				files.addAll(list);
		}

		final Collection<File> jars = new HashSet<File>();
		for(File file : files)
		{
			if(file.isDirectory() || (!file.getName().endsWith(".java") && !file.getName().endsWith(".class")))
				continue;

			String pkgDir = Files.relativePath(destDir.getAbsoluteFile(), file.getAbsoluteFile());
			pkgDir = pkgDir.substring(0, pkgDir.lastIndexOf(File.separator));
			final String pkg = pkgDir.replace(File.separatorChar, '.');
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
						break;
					}
				}

				if(schemaComposite == null)
					throw new CompilerError("Cant resolve url by its namespaceURI: {" + namespaceURI + "}. This really shouldn't happen!");

				URL url = schemaComposite.getSchemaDocument().getSchemaReference().getURL();

				final String simpleName = file.getParentFile().getName();
				final String jarName = simpleName + ".jar";
				String xsdName = simpleName + ".xsd";

				final File jarFile = new File(destDir, jarName);
				if(jarFile.exists())
					if(!jarFile.delete())
						throw new BindingError("Unable to delete the existing jar: " + jarFile.getAbsolutePath());

				jars.add(jarFile);

				jar = new Jar(jarFile);
				packageToJar.put(pkg, jar);

				// first we include the schema that was used to create the source
				byte[] bytes = Streams.getBytes(url.openStream());

				xsdName = pkgDir + File.separator + xsdName;
				jar.addEntry(xsdName, bytes);

				// Write the schema to disk
				Files.writeFile(new File(destDir, xsdName), bytes);

				final Collection<URL> includes = schemaComposite.getSchemaDocument().getIncludes();
				if(includes != null)
				{
					for(URL include : includes)
					{
						xsdName = URLs.getName(include);
						bytes = Streams.getBytes(include.openStream());

						xsdName = pkgDir + File.separator + xsdName;
						jar.addEntry(xsdName, bytes);

						// Write the schema to disk too
						Files.writeFile(new File(destDir, xsdName), bytes);
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
			BundleProcessor.compile(generatorContext.getDestDir());
			final Collection<File> jarFiles = BundleProcessor.jar(generatorContext.getDestDir(), documents);
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