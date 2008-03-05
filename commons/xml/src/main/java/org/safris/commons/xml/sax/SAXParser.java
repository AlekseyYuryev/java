package org.safris.commons.xml.sax;

import java.io.IOException;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

public class SAXParser
{
	private final XMLReader xmlReader;

	protected SAXParser(XMLReader xmlReader)
	{
		this.xmlReader = xmlReader;
	}

	public void setFeature(SAXFeature feature, boolean value)
	{
		try
		{
			xmlReader.setFeature(feature.getFeature(), value);
		}
		catch(SAXNotRecognizedException e)
		{
			// FIXME: Remove this!
			throw new Error(e);
		}
		catch(SAXNotSupportedException e)
		{
			// FIXME: Remove this!
			throw new Error(e);
		}
	}

	public boolean getFeature(SAXFeature feature)
	{
		try
		{
			return xmlReader.getFeature(feature.getFeature());
		}
		catch(SAXNotRecognizedException e)
		{
			// FIXME: Remove this!
			throw new Error(e);
		}
		catch(SAXNotSupportedException e)
		{
			// FIXME: Remove this!
			throw new Error(e);
		}
	}

	public void setProptery(SAXProperty property, Object value)
	{
		try
		{
			xmlReader.setProperty(property.getProperty(), value);
		}
		catch(SAXNotRecognizedException e)
		{
			// FIXME: Remove this!
			throw new Error(e);
		}
		catch(SAXNotSupportedException e)
		{
			// FIXME: Remove this!
			throw new Error(e);
		}
	}

	public Object getProperty(SAXProperty property)
	{
		try
		{
			return xmlReader.getProperty(property.getProperty());
		}
		catch(SAXNotRecognizedException e)
		{
			// FIXME: Remove this!
			throw new Error(e);
		}
		catch(SAXNotSupportedException e)
		{
			// FIXME: Remove this!
			throw new Error(e);
		}
	}

	public void setEntityResolver(EntityResolver resolver)
	{
		xmlReader.setEntityResolver(resolver);
	}

	public EntityResolver getEntityResolver()
	{
		return xmlReader.getEntityResolver();
	}

	public void setDTDHandler(DTDHandler handler)
	{
		xmlReader.setDTDHandler(handler);
	}

	public DTDHandler getDTDHandler()
	{
		return xmlReader.getDTDHandler();
	}

	public void setContentHandler(ContentHandler handler)
	{
		xmlReader.setContentHandler(handler);
	}

	public ContentHandler getContentHandler()
	{
		return xmlReader.getContentHandler();
	}

	public void setErrorHandler(ErrorHandler handler)
	{
		xmlReader.setErrorHandler(handler);
	}

	public ErrorHandler getErrorHandler()
	{
		return xmlReader.getErrorHandler();
	}

	public void parse(InputSource input) throws IOException, SAXException
	{
		xmlReader.parse(input);
	}

	public void parse(String systemId) throws IOException, SAXException
	{
		xmlReader.parse(systemId);
	}
}
