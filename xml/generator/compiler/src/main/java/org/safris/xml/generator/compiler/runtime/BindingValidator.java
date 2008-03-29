package org.safris.xml.generator.compiler.runtime;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.safris.commons.xml.dom.DOMs;
import org.safris.commons.xml.sax.SAXFeature;
import org.safris.commons.xml.sax.SAXParser;
import org.safris.commons.xml.sax.SAXParsers;
import org.safris.commons.xml.sax.SAXProperty;
import org.safris.commons.xml.validator.ValidationException;
import org.safris.commons.xml.validator.Validator;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class BindingValidator extends Validator
{
	private final Map<String,URL> schemaReferences = new HashMap<String,URL>();

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

			saxParser.setFeature(SAXFeature.CONTINUE_AFTER_FATAL_ERROR, true);
			saxParser.setFeature(SAXFeature.DYNAMIC_VALIDATION, true);
			saxParser.setFeature(SAXFeature.NAMESPACE_PREFIXES, true);
			saxParser.setFeature(SAXFeature.NAMESPACES, true);
			saxParser.setFeature(SAXFeature.SCHEMA_VALIDATION, true);
			saxParser.setFeature(SAXFeature.VALIDATION, true);

			saxParser.setProptery(SAXProperty.SCHEMA_LOCATION, "http://www.w3.org/2001/XMLSchema http://www.w3.org/2001/XMLSchema.xsd");
			saxParser.setProptery(SAXProperty.ENTITY_RESOLVER, new BindingEntityResolver());

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
