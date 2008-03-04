package org.safris.xml.generator.compiler.runtime;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.safris.commons.net.URLs;
import org.safris.commons.xml.dom.DOMs;
import org.safris.commons.xml.sax.SAXParser;
import org.safris.commons.xml.sax.SAXParserFeature;
import org.safris.commons.xml.sax.SAXParserProperty;
import org.safris.commons.xml.sax.SAXParsers;
import org.safris.commons.xml.validator.ValidationException;
import org.safris.commons.xml.validator.Validator;
import org.safris.commons.xml.validator.ValidatorError;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class BindingValidator extends Validator
{
	private final Map<String,URL> schemaReferences = new HashMap<String,URL>();

	// FIXME: Does this have to be public?
	public void registerSchemaLocation(String namespaceURI, String schemaLocation)
	{
		try
		{
			schemaReferences.put(namespaceURI, URLs.makeUrlFromPath(schemaLocation));
		}
		catch(MalformedURLException e)
		{
			throw new ValidatorError("Unknown URL format: " + schemaLocation);
		}
	}

	protected URL lookupSchemaLocation(String namespaceURI)
	{
		return schemaReferences.get(namespaceURI);
	}

	protected URL getSchemaLocation(String namespaceURI)
	{
		return BindingEntityResolver.lookupSchemaLocation(namespaceURI);
	}

	protected void parse(Element element) throws IOException, ValidationException
	{
		final SAXParser saxParser;
		try
		{
			saxParser = SAXParsers.createParser();

			saxParser.addFeature(SAXParserFeature.CONTINUE_AFTER_FATAL_ERROR);
			saxParser.addFeature(SAXParserFeature.VALIDATION);
			saxParser.addFeature(SAXParserFeature.NAMESPACE_PREFIXES_FEATURE_ID);
			saxParser.addFeature(SAXParserFeature.NAMESPACES_FEATURE_ID);
			saxParser.addFeature(SAXParserFeature.SCHEMA_VALIDATION);

			saxParser.addProptery(SAXParserProperty.ENTITY_RESOLVER, new BindingEntityResolver());
			saxParser.setErrorHandler(BindingErrorHandler.getInstance());
		}
		catch(Exception e)
		{
			throw new ValidationException(e);
		}

		final String output = DOMs.domToString(element);
		try
		{
			saxParser.parse(new InputSource(new StringReader(output)));
		}
		catch(IOException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new ValidationException("\n" + e.getMessage() + "\n" + output, e);
		}
	}
}
