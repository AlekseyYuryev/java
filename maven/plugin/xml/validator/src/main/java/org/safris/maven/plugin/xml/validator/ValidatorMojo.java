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
import org.apache.maven.plugin.logging.Log;
import org.safris.commons.io.Files;
import org.safris.commons.xml.sax.SAXFeature;
import org.safris.commons.xml.sax.SAXParser;
import org.safris.commons.xml.sax.SAXParsers;
import org.safris.commons.xml.sax.SAXProperty;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @goal validate
 * @requiresDependencyResolution test
 * @phase compile
 */
public class ValidatorMojo extends AbstractMojo
{
	private static final String delimeter = "://";

	private static final FileFilter xmlFileFilter = new FileFilter()
	{
		public boolean accept(File pathname)
		{
			return pathname.isFile() && (pathname.getName().endsWith(".xml") || pathname.getName().endsWith(".xsd"));
		}
	};

	/**
	 * @parameter default-value="" expression="${httpProxy}"
	 */
	private String httpProxy;

	public String getHttpProxy()
	{
		return httpProxy;
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

	protected static void validate(File file, Log log) throws IOException, SAXException
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
		saxParser.setErrorHandler(ValidatorErrorHandler.getInstance(log));

		// Parse.
		saxParser.parse(new InputSource(new FileInputStream(file)));
	}

	protected void setHttpProxy() throws MojoExecutionException
	{
		final String httpProxy = getHttpProxy();
		if(httpProxy == null)
			return;

		final String scheme;
		if(httpProxy.startsWith("https"))
			scheme = "https";
		else if(httpProxy.startsWith("http"))
			scheme = "http";
		else
			throw new MojoExecutionException("Invalid proxy: " + httpProxy + " no http or http scheme.");

		final String port;
		final int portIndex = httpProxy.indexOf(":", scheme.length());
		if(portIndex != -1)
			port = httpProxy.substring(portIndex + 1);
		else
			port = "80";

		System.setProperty(scheme + ".proxyHost", httpProxy.substring(scheme.length() + delimeter.length(), portIndex));
		System.setProperty(scheme + ".proxyPort", port);
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
					validate(file, getLog());
				}
				catch(SAXException e)
				{
					throw new MojoFailureException("Failed to validate xml.", "\nFile: " + Files.relativePath(new File("").getAbsoluteFile(), file.getAbsoluteFile()), "Reason: " + e.getMessage() + "\n");
				}
			}
		}
		catch(IOException e)
		{
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
}
