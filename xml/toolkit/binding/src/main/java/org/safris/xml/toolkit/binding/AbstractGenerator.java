package org.safris.xml.toolkit.binding;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import org.safris.commons.util.URLs;
import org.safris.commons.util.logging.ExitSevereError;
import org.safris.commons.util.xml.DOMParsers;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.xml.generator.lexer.processor.reference.SchemaReference;
import org.w3c.dom.Document;

public abstract class AbstractGenerator
{
	private static final Map<String,SchemaDocument> parsedDocuments = new HashMap<String,SchemaDocument>();

	public static SchemaDocument parse(SchemaReference schemaReference)
	{
		URL url = null;
		SchemaDocument parsedDocument = null;
		Document document = null;
		try
		{
			url = URLs.canonicalizeURL(schemaReference.getURL());
			parsedDocuments.get(schemaReference.getNamespaceURI() + url.toString());
			if(parsedDocument != null)
				return parsedDocument;

			final DocumentBuilder documentBuilder = DOMParsers.newDocumentBuilder();
			document = documentBuilder.parse(url.toString());
		}
		catch(FileNotFoundException e)
		{
			throw new ExitSevereError(e.getMessage());
		}
		catch(Exception e)
		{
			throw new CompilerError(e);
		}

		parsedDocument = new SchemaDocument(schemaReference, document);
		parsedDocuments.put(schemaReference.getNamespaceURI() + url.toString(), parsedDocument);
		return parsedDocument;
	}
}
