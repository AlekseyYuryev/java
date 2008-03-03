package org.safris.xml.generator.compiler.util;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.compiler.runtime.BindingError;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.generator.compiler.runtime.ValidationException;
import org.safris.xml.generator.compiler.runtime.XMLSchemaResolver;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class DefaultValidator extends Validator
{
    private static final String VALIDATION = "http://xml.org/sax/features/validation";
    private static final String XMLSCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
    private static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
    private static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
    private static final String DISALLOW_DOCTYPE = "http://apache.org/xml/features/disallow-doctype-decl";
    private static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
    private static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
    private static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaReferences";
    private static final String STRING_INTERNING = "http://xml.org/sax/features/string-interning";
    private static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
    private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    private static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
    private static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
    private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    private static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
    private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";

	private static final String NAMESPACES_FEATURE_ID = "http://xml.org/sax/features/namespaces";
	private static final String NAMESPACE_PREFIXES_FEATURE_ID = "http://xml.org/sax/features/namespace-prefixes";
	private static final String SCHEMA_FULL_CHECKING_FEATURE_ID = "http://apache.org/xml/features/validation/schema-full-checking";
	private final Map<String,URL> schemaReferences = new HashMap<String,URL>();

	private static XMLReader createReader() throws SAXException
	{
		// Create a specific SAXParser so that it works with our XMLSchemaResolver
		final XMLReader xmlReader = XMLReaderFactory.createXMLReader(SAXParser.class.getName());

		xmlReader.setFeature(NAMESPACES_FEATURE_ID, true);
		xmlReader.setFeature(VALIDATION, true);
		xmlReader.setFeature(NAMESPACE_PREFIXES_FEATURE_ID, true);
		xmlReader.setFeature(XMLSCHEMA_VALIDATION, true);
		xmlReader.setFeature(CONTINUE_AFTER_FATAL_ERROR, true);

		// NOTE: Do NOT enable "http://apache.org/xml/features/honour-all-schemaReferences"
		// NOTE: For some reason, it messes up stuff dealing with xsi:type!!!
		//xmlReader.setFeature(HONOUR_ALL_SCHEMALOCATIONS, true);

		final ErrorHandler errorHandler = new DefaultErrorHandler();
		if(errorHandler != null)
			xmlReader.setErrorHandler(errorHandler);

		return xmlReader;
	}

	public void registerSchemaLocation(String namespaceURI, String schemaReference)
	{
		URL url;
		try
		{
			url = new URL(schemaReference);
		}
		catch(MalformedURLException e)
		{
			final File file = new File(schemaReference);
			try
			{
				url = file.toURL();
			}
			catch(MalformedURLException ex)
			{
				throw new BindingError("Unknown URL format: " + schemaReference);
			}
		}

		schemaReferences.put(namespaceURI, url);
	}

	protected URL lookupSchemaLocation(String namespaceURI)
	{
		return schemaReferences.get(namespaceURI);
	}

	protected void parse(Element element) throws IOException, ValidationException
	{
		final XMLReader xmlReader;
		try
		{
			xmlReader = createReader();
			xmlReader.setProperty(ENTITY_RESOLVER, new XMLSchemaResolver());
		}
		catch(Exception e)
		{
			throw new ValidationException(e);
		}

		final String output = Bindings.domToString(element);
		try
		{
			xmlReader.parse(new InputSource(new StringReader(output)));
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
