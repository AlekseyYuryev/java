package org.safris.maven.plugin.xml.validator;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.safris.commons.io.Files;
import org.safris.commons.lang.Paths;
import org.safris.commons.xml.sax.SAXFeature;
import org.safris.commons.xml.sax.SAXParser;
import org.safris.commons.xml.sax.SAXParsers;
import org.safris.commons.xml.sax.SAXProperty;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @goal validate
 * @requiresDependencyResolution test
 * @execute phase="compile"
 * @phase compile
 */
public class ValidatorMojo extends AbstractMojo
{
	private static final FileFilter xmlFileFilter = new FileFilter()
	{
		public boolean accept(File pathname)
		{
			return pathname.isFile() && (pathname.getName().endsWith(".xml") || pathname.getName().endsWith(".xsd"));
		}
	};

	/**
	 * @parameter default-value="" expression="${mdep.httpProxy}"
	 */
	private String httpProxy;

	public String getHttpProxy()
	{
		return httpProxy;
	}

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

	protected static void validate(File file) throws IOException, SAXException
	{
		final SAXParser saxParser = SAXParsers.createParser();
		// Set the features.
		saxParser.setFeature(SAXFeature.CONTINUE_AFTER_FATAL_ERROR, true);
		saxParser.setFeature(SAXFeature.DYNAMIC_VALIDATION, true);
		saxParser.setFeature(SAXFeature.NAMESPACE_PREFIXES, true);
		saxParser.setFeature(SAXFeature.NAMESPACES, true);
		saxParser.setFeature(SAXFeature.SCHEMA_FULL_CHECKING, true);
		saxParser.setFeature(SAXFeature.SCHEMA_VALIDATION, true);
		saxParser.setFeature(SAXFeature.WARN_ON_DUPLICATE_ATTDEF, true);
		saxParser.setFeature(SAXFeature.WARN_ON_DUPLICATE_ENTITYDEF, true);
		saxParser.setFeature(SAXFeature.VALIDATION, true);

		// Set the properties.
		saxParser.setProptery(SAXProperty.SCHEMA_LOCATION, "http://www.w3.org/2001/XMLSchema http://www.w3.org/2001/XMLSchema.xsd");
		saxParser.setProptery(SAXProperty.ENTITY_RESOLVER, new ValidatorEntityResolver(file.getAbsoluteFile().getParentFile()));

		// Set the ErrorHandler.
		saxParser.setErrorHandler(ValidatorErrorHandler.getInstance());

		// Parse.
		saxParser.parse(new InputSource(new FileInputStream(file)));
	}

	protected void setHttpProxy() throws MojoExecutionException
	{
		String httpProxy = getHttpProxy();
		if(!httpProxy.startsWith("http://"))
			throw new MojoExecutionException("Invalid proxy: " + httpProxy + " no http://");

		int portIndex = httpProxy.indexOf(":");
		if(portIndex == -1)
			throw new MojoExecutionException("Invalid proxy: " + httpProxy + " no port specified.");

		System.setProperty("http.proxyHost", httpProxy.substring(7, portIndex));
		System.setProperty("http.proxyPort", httpProxy.substring(portIndex + 1));
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

		final Collection<File> files = new ArrayList<File>();
		for(Resource resource : resources)
		{
			final Collection<File> xmlFiles = Files.listAll(new File(resource.getDirectory()), xmlFileFilter);
			if(xmlFiles != null)
				files.addAll(xmlFiles);
		}

		if(files.size() == 0)
			return;

		// Set the httpProxy if it was specified.
		setHttpProxy();

		try
		{
			for(File file : files)
			{
				try
				{
					validate(file);
				}
				catch(SAXException e)
				{
					getLog().error(Paths.relativePath(getBasedir(), file.getPath()));
					throw new MojoFailureException(e.getMessage());
				}
			}
		}
		catch(IOException e)
		{
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
}
