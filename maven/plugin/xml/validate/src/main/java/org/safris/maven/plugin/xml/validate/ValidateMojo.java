package org.safris.maven.plugin.xml.validate;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.parsers.SAXParser;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.safris.commons.io.Files;
import org.safris.commons.lang.Paths;
import org.safris.commons.xml.SAXParsers;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @goal validate
 * @requiresDependencyResolution test
 * @execute phase="compile"
 * @phase compile
 */
public class ValidateMojo extends AbstractMojo
{
	private static final FileFilter xmlFileFilter = new FileFilter()
	{
		public boolean accept(File pathname)
		{
			return pathname.isFile() && pathname.getName().endsWith(".xml");
		}
	};

	/**
	 * @parameter default-value="${basedir}"
	 */
	private String basedir = null;

	public String getBasedir()
	{
		return basedir;
	}

	/**
	 * @parameter expression="${project.resources}"
	 */
	private List<Resource> resources;

	public List<Resource> getResources()
	{
		return resources;
	}

	/**
	 * @parameter expression="${project.testResources}"
	 */
	private List<Resource> testResources;

	public List<Resource> getTestResources()
	{
		return testResources;
	}

	public void execute() throws MojoExecutionException, MojoFailureException
	{
		final Collection<Resource> resources = new ArrayList<Resource>();
		if(getResources() != null)
			resources.addAll(getResources());

		if(getTestResources() != null)
			resources.addAll(getTestResources());

		if(resources.size() == 0)
			return;

		final Collection<File> xmlFiles = new ArrayList<File>();
		for(Resource resource : resources)
		{
			final Collection<File> files = Files.listAll(new File(resource.getDirectory()), xmlFileFilter);
			if(files != null)
				xmlFiles.addAll(files);
		}

		if(xmlFiles.size() == 0)
			return;

		try
		{
			for(File xmlFile : xmlFiles)
			{
				SAXParser xmlReader;
				try
				{
					xmlReader = SAXParsers.newSAXParser();
				}
				catch(Exception e)
				{
					throw new MojoExecutionException(e.getMessage(), e);
				}

				try
				{
					xmlReader.parse(new InputSource(new FileInputStream(xmlFile)), new DefaultHandler());
				}
				catch(SAXException e)
				{
					getLog().error(Paths.relativePath(getBasedir(), xmlFile.getPath()));
					throw e;
				}
			}
		}
		catch(Exception e)
		{
			throw new MojoFailureException(e.getMessage());
		}
	}
}
