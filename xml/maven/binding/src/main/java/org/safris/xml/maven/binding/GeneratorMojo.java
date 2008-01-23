package org.safris.xml.maven.binding;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import org.apache.maven.model.Build;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.safris.commons.util.Files;
import org.safris.commons.util.URLs;
import org.safris.commons.util.Zips;
import org.safris.commons.util.xml.DOMParsers;
import org.safris.commons.util.xml.SchemaReference;
import org.safris.xml.generator.module.phase.BindingContext;
import org.safris.xml.toolkit.binding.Bundle;
import org.safris.xml.toolkit.binding.Generator;
import org.safris.xml.toolkit.binding.PropertyResolver;
import org.w3.x2001.xmlschema.IXSBoolean;
import org.w3c.dom.Document;

/**
 * @version $Id
 * @goal generate
 */
public class GeneratorMojo extends AbstractMojo
{
	public static void main(String[] args) throws MojoFailureException
	{
		if(args.length != 1)
			usage();

		final File pomFile = new File(args[0]);
		if(!pomFile.exists())
			throw new MojoFailureException("File does not exist: " + pomFile.getAbsolutePath());

		MavenLauncher.main(new String[]{"-e", "-f", pomFile.getAbsolutePath(), "org.safris.xml.maven:binding:generate"});
	}

	private static void usage()
	{
		System.err.println("Usage: GeneratorMojo <pom.xml>");
		System.exit(1);
	}

	private static boolean upToDate(long lastModified, File destDir)
	{
		if(lastModified < destDir.lastModified())
			return true;

		return false;
	}

	private static void setLastModified(File destDir, long offset)
	{
		destDir.setLastModified(destDir.lastModified() + offset);
	}

	private static final FileFilter classesFilter = new FileFilter()
	{
		public boolean accept(File pathname)
		{
			final String name = pathname.getName();
			return name != null && !name.endsWith(".class") && !name.endsWith(".java");
		}
	};

	/**
	 * @parameter default-value="${project}"
	 */
	private Object project = null;

	/**
	 * @parameter default-value="${maven.test.skip}"
	 */
	private Boolean mavenTestSkip = null;

	/**
	 * @parameter default-value="${basedir}"
	 */
	private String basedir = null;

	/**
	 * @parameter
	 */
	private Manifest manifest;

	private PropertyResolver resolver = null;

	public void execute() throws MojoExecutionException, MojoFailureException
	{
		String href = null;
		final BindingContext bindingContext = new BindingContext();
		if(project != null && project instanceof MavenProject)
		{
			final Build build = ((MavenProject)project).getBuild();
			if(build != null && build.getPlugins() != null)
			{
				resolver = new MavenPropertyResolver(((MavenProject)project));
				for(Plugin plugin : (List<Plugin>)build.getPlugins())
				{
					if(!"binding".equals(plugin.getArtifactId()))
						continue;

					final Xpp3Dom configuration = (Xpp3Dom)plugin.getConfiguration();
					for(int i = 0; i < configuration.getChildCount(); i++)
					{
						final Xpp3Dom bindings = configuration.getChild(i);
						if("manifest".equals(bindings.getName()))
						{
							for(int j = 0; j < bindings.getChildCount(); j++)
							{
								final Xpp3Dom link = bindings.getChild(j);
								if("link".equals(link.getName()))
								{
									String attributeName = null;
									final String[] names = link.getAttributeNames();
									for(String name : names)
									{
										if(name.endsWith("href"))
										{
											attributeName = name;
											break;
										}
									}

									if(attributeName == null)
										throw new MojoFailureException("There is an error in your manifest xml. Please consult the manifest.xsd for proper usage.");

									href = link.getAttribute(attributeName);
									break;
								}
								else if("destdir".equals(link.getName()))
								{
									String explodeJarsName = null;
									String overwriteName = null;
									final String[] names = link.getAttributeNames();
									for(String name : names)
									{
										if(name.endsWith("explodeJars"))
											explodeJarsName = name;
										else if(name.endsWith("overwrite"))
											overwriteName = name;
									}

									if(explodeJarsName != null)
										bindingContext.setExplodeJars(IXSBoolean.parseBoolean(link.getAttribute(explodeJarsName)));

									if(overwriteName != null)
										bindingContext.setOverwrite(IXSBoolean.parseBoolean(link.getAttribute(explodeJarsName)));

									break;
								}
							}

							break;
						}
					}

					final Object defaultExecution = plugin.getExecutionsAsMap().get("default");
					if(defaultExecution == null || !(defaultExecution instanceof PluginExecution))
						break;

					final String phase = ((PluginExecution)defaultExecution).getPhase();
					if(phase != null && !phase.contains("test"))
						break;

					if(mavenTestSkip == null || !mavenTestSkip)
						break;

					return;
				}
			}
		}

		try
		{
			if(href != null)
			{
				File hrefFile = null;
				if(href.startsWith(File.separator))
					hrefFile = new File(href);
				else if(basedir != null)
					hrefFile = new File(basedir, href);
				else
					hrefFile = new File(href);

				if(!hrefFile.exists())
					throw new MojoFailureException("href=\"" + href + "\" does not exist.");
				else if(!hrefFile.isFile())
					throw new MojoFailureException("href=\"" + href + "\" is not a file.");

				Document document = null;
				try
				{
					final DocumentBuilder documentBuilder = DOMParsers.newDocumentBuilder();
					document = documentBuilder.parse(hrefFile);
				}
				catch(Exception e)
				{
					throw new MojoExecutionException(e.getMessage(), e);
				}

				final Generator generator = new Generator(bindingContext, new File(basedir), document.getDocumentElement(), hrefFile.lastModified(), resolver);
				if(bindingContext.getOverwrite())
				{
					if(generator.getbindingContext().getDestDir().exists())
						Files.deleteAll(generator.getbindingContext().getDestDir());
				}
				else if(upToDate(hrefFile.lastModified(), generator.getbindingContext().getDestDir()))
				{
					System.out.println("Generated sources up-to-date.");
					return;
				}

				final long start = System.currentTimeMillis();
				final Collection<Bundle> bundles = generator.generate();
				addCompileSourceRoot(generator.getbindingContext().getDestDir().getAbsolutePath(), bundles);
				final long end = System.currentTimeMillis();
				setLastModified(generator.getbindingContext().getDestDir(), start - end);
				return;
			}

			if(manifest == null || manifest.getDestdir() == null || manifest.getSchemas() == null)
				return;

			final String destDir = manifest.getDestdir();
			final Collection<SchemaReference> generatorBindings = new ArrayList<SchemaReference>(7);

			for(String schema : manifest.getSchemas())
			{
				if(URLs.isAbsolute(schema))
					generatorBindings.add(new SchemaReference(schema));
				else
					generatorBindings.add(new SchemaReference(((MavenProject)project).getFile().getParentFile().getAbsolutePath(), schema));
			}

			if(destDir == null || destDir.length() == 0)
				throw new MojoFailureException("<destdir> is null or empty!");

			if(generatorBindings.size() == 0)
				return;

			final File destDirFile = new File(destDir);
			final Generator generator = new Generator(new BindingContext(destDirFile, bindingContext.getExplodeJars(), bindingContext.getOverwrite()), generatorBindings);
			File pomXml = null;
			if(basedir != null && basedir.length() != 0)
				pomXml = new File(basedir, "pom.xml");
			else
				pomXml = new File("pom.xml");

			if(bindingContext.getOverwrite())
			{
				if(generator.getbindingContext().getDestDir().exists())
					Files.deleteAll(generator.getbindingContext().getDestDir());
			}
			else if(pomXml.exists() && !bindingContext.getOverwrite() && upToDate(pomXml.lastModified(), generator.getbindingContext().getDestDir()))
			{
				System.out.println("Generated sources up-to-date.");
				return;
			}

			final Collection<Bundle> bundles = generator.generate();
			final long start = System.currentTimeMillis();
			addCompileSourceRoot(generator.getbindingContext().getDestDir().getAbsolutePath(), bundles);
			final long end = System.currentTimeMillis();
			setLastModified(generator.getbindingContext().getDestDir(), start - end);
		}
		catch(IOException e)
		{
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private void addCompileSourceRoot(String path, Collection<Bundle> bundles) throws MojoExecutionException
	{
		if(bundles == null || path == null || project == null || !(project instanceof MavenProject))
			return;

		final MavenProject mavenProject = (MavenProject)project;
		try
		{
			for(Bundle bundle : bundles)
			{
				for(String element : (List<String>)mavenProject.getTestClasspathElements())
				{
					final File elementFile = new File(element);
					if(!elementFile.isFile())
						Zips.unzip(bundle.getFile(), classesFilter, elementFile);
				}

				for(String element : (List<String>)mavenProject.getCompileClasspathElements())
				{
					final File elementFile = new File(element);
					if(!elementFile.isFile())
						Zips.unzip(bundle.getFile(), classesFilter, elementFile);
				}
			}
		}
		catch(Exception e)
		{
			throw new MojoExecutionException(e.getMessage(), e);
		}

		// add to both compile and test-compile classpaths so that the generated classes
		// can be used for the main and test source.
		mavenProject.addTestCompileSourceRoot(path);
		mavenProject.addCompileSourceRoot(path);
	}
}
