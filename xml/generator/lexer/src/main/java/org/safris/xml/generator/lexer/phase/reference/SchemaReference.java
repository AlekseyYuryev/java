package org.safris.xml.generator.lexer.phase.reference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import org.safris.commons.util.URLs;
import org.safris.commons.util.logging.ExitSevereError;
import org.safris.commons.util.xml.NamespaceURI;
import org.safris.commons.util.xml.Prefix;
import org.safris.xml.generator.lexer.phase.reference.SchemaNamespaceHandler;
import org.safris.xml.generator.processor.phase.BindingQName;
import org.safris.xml.generator.processor.phase.ElementModule;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SchemaReference implements ElementModule<SchemaReference>
{
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
	}

	public SchemaReference(URL location)
	{
		this.location = location;
	}

	public SchemaReference(URL location, NamespaceURI namespaceURI, Prefix prefix)
	{
		this.location = location;
		this.namespaceURI = namespaceURI;
		this.prefix = prefix;
	}

	public SchemaReference(URL location, NamespaceURI namespaceURI)
	{
		this.location = location;
		this.namespaceURI = namespaceURI;
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
				throw new ExitSevereError(e);
			}

			try
			{
				final XMLReader xmlReader = XMLReaderFactory.createXMLReader();
				xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
				xmlReader.setContentHandler(new SchemaNamespaceHandler(getURL()));
				xmlReader.parse(new InputSource(inputStream));
			}
			catch(FileNotFoundException e)
			{
				throw new ExitSevereError(e.getMessage());
			}
			catch(IOException e)
			{
				throw new ExitSevereError(e);
			}
			catch(SAXException e)
			{
				final String code = location.hashCode() + "\"";
				if(e.getMessage() == null)
					throw new ExitSevereError(location.toString(), e);

				if(e.getMessage().indexOf(code) != 0)
					throw new ExitSevereError(location.toString(), e);

				final int delimiter = e.getMessage().lastIndexOf("\"");
				if(delimiter == -1)
					throw new ExitSevereError(location.toString(), e);

				final String namespace = e.getMessage().substring(code.length(), delimiter);
				final String prefix = e.getMessage().substring(delimiter + 1);
				// This links the namespaceURI to the prefix
				if(namespaceURI == null)
					namespaceURI = NamespaceURI.getInstance(namespace);
				else if(!namespaceURI.toString().equals(namespace))
					throw new ExitSevereError("This should never happen!!");

				this.prefix = Prefix.getInstance(prefix);
				BindingQName.linkPrefixNamespace(namespaceURI, this.prefix);
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
				}
				catch(FileNotFoundException e)
				{
					throw new ExitSevereError(e.getMessage());
				}
				catch(IOException e)
				{
					if("Connection refused".equals(e.getMessage()) && tryCount == 10)
						throw new ExitSevereError("Connection refused: " + location);
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

		final SchemaReference schemaReference = (SchemaReference)obj;
		return location.equals(schemaReference.location) && namespaceURI.equals(schemaReference.namespaceURI);
	}

	public int hashCode()
	{
		return location.hashCode() ^ (namespaceURI != null ? namespaceURI.hashCode() : 89432);
	}
}
