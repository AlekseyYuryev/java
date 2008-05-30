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

package org.safris.xml.generator.lexer.processor.reference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import org.safris.commons.logging.Logger;
import org.safris.commons.net.URLs;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.xml.NamespaceURI;
import org.safris.commons.xml.Prefix;
import org.safris.commons.xml.sax.SAXFeature;
import org.safris.commons.xml.sax.SAXParser;
import org.safris.commons.xml.sax.SAXParsers;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.lang.LexerLoggerName;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SchemaReference implements PipelineEntity<SchemaReference>
{
	private static final Logger logger = Logger.getLogger(LexerLoggerName.REFERENCE);
	private static final Map<NamespaceURI,Prefix> namespaceURIToPrefix = new HashMap<NamespaceURI,Prefix>();
	private static final Map<Prefix,NamespaceURI> prefixToNamespaceURI = new HashMap<Prefix,NamespaceURI>();

	// to de-reference the schemaReference to a targetNamespace
	private volatile boolean isConnected = false;
	private volatile boolean isResolved = false;
	private URL location;
	private NamespaceURI namespaceURI;
	private Prefix prefix;
	private long lastModified = Long.MIN_VALUE;
	private InputStream inputStream = null;

	public SchemaReference(String location)
	{
		this(null, location);
	}

	public SchemaReference(String basedir, String location)
	{
		if(location == null)
			throw new IllegalArgumentException("location cannot be null");

		try
		{
			if(basedir != null)
				this.location = URLs.makeUrlFromPath(basedir, location);
			else
				this.location = new URL(location);
		}
		catch(MalformedURLException e)
		{
			try
			{
				if(basedir != null)
					this.location = new File(basedir, location).toURL();
				else
					this.location = new File(location).toURL();
			}
			catch(MalformedURLException ex)
			{
				throw new IllegalArgumentException("Unknown URL format: " + location);
			}
		}

		logger.fine("new SchemaReference(\"" + this.location.toExternalForm() + "\")");
	}

	public SchemaReference(URL location)
	{
		this.location = location;
		logger.fine("new SchemaReference(\"" + this.location.toExternalForm() + "\")");
	}

	public SchemaReference(URL location, NamespaceURI namespaceURI, Prefix prefix)
	{
		this.location = location;
		this.namespaceURI = namespaceURI;
		this.prefix = prefix;
		logger.fine("new SchemaReference(\"" + this.location.toExternalForm() + "\", \"" + namespaceURI + "\", \"" + prefix + "\")");
	}

	public SchemaReference(URL location, NamespaceURI namespaceURI)
	{
		this.location = location;
		this.namespaceURI = namespaceURI;
		logger.fine("new SchemaReference(\"" + this.location.toExternalForm() + "\", \"" + namespaceURI + "\")");
	}

	public NamespaceURI getNamespaceURI()
	{
		resolveUnknowns();
		return namespaceURI;
	}

	public Prefix getPrefix()
	{
		resolveUnknowns();
		return prefix;
	}

	private void resolveUnknowns()
	{
		if(isResolved)
			return;

		if(namespaceURI != null)
		{
			if(prefix == null)
				prefix = namespaceURIToPrefix.get(namespaceURI);

			if(prefix != null)
			{
				isResolved = true;
				return;
			}
		}
		else if(prefix != null)
		{
			if(namespaceURI == null)
				namespaceURI = prefixToNamespaceURI.get(prefix);

			if(namespaceURI != null)
			{
				isResolved = true;
				return;
			}
		}

		synchronized(location)
		{
			if(isResolved)
				return;

			try
			{
				openConnection();
			}
			catch(IOException e)
			{
				throw new LexerError(e);
			}

			try
			{
				final SAXParser saxParser = SAXParsers.createParser();
				saxParser.setFeature(SAXFeature.NAMESPACE_PREFIXES, true);
				saxParser.setContentHandler(new SchemaNamespaceHandler(getURL()));
				saxParser.parse(new InputSource(inputStream));
			}
			catch(FileNotFoundException e)
			{
				throw new LexerError(e.getMessage());
			}
			catch(IOException e)
			{
				throw new LexerError(e);
			}
			catch(SAXException e)
			{
				if(e.getMessage() == null)
					throw new LexerError(location.toString(), e);

				final String code = location.hashCode() + "\"";
				if(e.getMessage().indexOf(code) != 0)
					throw new LexerError(location.toString(), e);

				final int delimiter = e.getMessage().lastIndexOf("\"");
				if(delimiter == -1)
					throw new LexerError(location.toString(), e);

				final String namespace = e.getMessage().substring(code.length(), delimiter);
				final String prefix = e.getMessage().substring(delimiter + 1);
				// This links the namespaceURI to the prefix
				if(namespaceURI == null)
					namespaceURI = NamespaceURI.getInstance(namespace);
				else if(!namespaceURI.toString().equals(namespace))
					throw new LexerError("This should never happen!!");

				this.prefix = Prefix.getInstance(prefix);
				logger.fine("linking \"" + namespaceURI + "\" to \"" + this.prefix + "\"");
				UniqueQName.linkPrefixNamespace(namespaceURI, this.prefix);
				isResolved = true;
			}
		}
	}

	private void openConnection() throws IOException
	{
		if(isConnected)
			return;

		synchronized(location.toString())
		{
			if(isConnected)
				return;

			final URLConnection connection = location.openConnection();
			int tryCount = 0;
			while(tryCount++ < 10)
			{
				try
				{
					this.inputStream = connection.getInputStream();
					logger.fine("opened connection to: " + location.toExternalForm());
				}
				catch(FileNotFoundException e)
				{
					logger.info("File not found: " + location.toExternalForm());
					System.exit(1);
				}
				catch(IOException e)
				{
					if("Connection refused".equals(e.getMessage()) && tryCount == 10)
						throw new LexerError("Connection refused: " + location);
					else
						throw e;
				}
			}

			this.lastModified = connection.getLastModified();
			isConnected = true;
		}

		return;
	}

	public long getLastModified() throws IOException
	{
		openConnection();
		return lastModified;
	}

	public URL getURL()
	{
		return location;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof SchemaReference))
			return false;

		final SchemaReference that = (SchemaReference)obj;
		return location.equals(that.location) && namespaceURI.equals(that.namespaceURI);
	}

	public int hashCode()
	{
		return location.hashCode() ^ (namespaceURI != null ? namespaceURI.hashCode() : 89432);
	}
}
