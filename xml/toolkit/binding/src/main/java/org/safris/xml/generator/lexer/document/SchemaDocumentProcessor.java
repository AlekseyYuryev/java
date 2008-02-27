package org.safris.xml.generator.lexer.document;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Stack;
import org.safris.commons.logging.Logger;
import org.safris.commons.net.URLs;
import org.safris.commons.xml.NamespaceURI;
import org.safris.xml.generator.lexer.lang.LexerLoggerName;
import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.xml.generator.lexer.processor.reference.SchemaReference;
import org.safris.xml.generator.processor.BindingQName;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;
import org.safris.xml.toolkit.binding.AbstractGenerator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SchemaDocumentProcessor implements ElementModule<SchemaDocument>, ModuleProcessor<SchemaReference,SchemaDocument>
{
	protected static final Logger logger = Logger.getLogger(LexerLoggerName.DOCUMENT);

	private static final String[] includeStrings = new String[]
	{
		"include",
		"redefine"
	};

	public Collection<SchemaDocument> process(Collection<SchemaReference> selectedSchemas, GeneratorContext generatorContext, ProcessorDirectory<SchemaReference, SchemaDocument> directory)
	{
		if(selectedSchemas == null || selectedSchemas.size() == 0)
			return null;

		final Collection<SchemaDocument> schemas = new LinkedHashSet<SchemaDocument>();
		final Map<NamespaceURI,URL> importLoopCheck = new HashMap<NamespaceURI,URL>();
		final Map<NamespaceURI,Collection<URL>> includeLoopCheck = new HashMap<NamespaceURI,Collection<URL>>();

		for(SchemaReference schemaReference : selectedSchemas)
		{
			if(schemaReference == null)
				continue;

			final Stack<SchemaDocument> schemasToGenerate = new Stack<SchemaDocument>();

			try
			{
				URL url = URLs.canonicalizeURL(schemaReference.getURL());
				// First we need to find all of the imports and includes
				Collection<SchemaDocument> outer = new Stack<SchemaDocument>();
				outer.add(AbstractGenerator.parse(schemaReference));
				importLoopCheck.put(schemaReference.getNamespaceURI(), url);
				while(outer.size() != 0)
				{
					schemasToGenerate.addAll(0, outer);
					Stack<SchemaDocument> inner = new Stack<SchemaDocument>();
					for(SchemaDocument entry : outer)
					{
						NodeList includeNodeList = null;
						for(String includeString : includeStrings)
						{
							includeNodeList = entry.getDocument().getElementsByTagNameNS(BindingQName.XS.getNamespaceURI().toString(), includeString);
							for(int i = 0; i < includeNodeList.getLength(); i++)
							{
								final Element includeElement = (Element)includeNodeList.item(i);
								final URL schemaLocationURL = SchemaDocumentProcessor.getSchemaLocation(url, includeElement);

								// Dont want to get into an infinite loop
								Collection<URL> duplicates = includeLoopCheck.get(entry.getSchemaReference().getNamespaceURI());
								if(schemaLocationURL.equals(entry.getSchemaReference().getURL()) || (duplicates != null && duplicates.contains(schemaLocationURL)))
									continue;

								final SchemaReference includeSchemaReference = new SchemaReference(schemaLocationURL, entry.getSchemaReference().getNamespaceURI(), entry.getSchemaReference().getPrefix());
								inner.insertElementAt(AbstractGenerator.parse(includeSchemaReference), 0);
								if(duplicates == null)
									duplicates = new ArrayList<URL>();

								duplicates.add(schemaLocationURL);
								System.out.println("Adding " + new File(schemaLocationURL.getFile()).getName() + " for {" + entry.getSchemaReference().getNamespaceURI() + "}");
								includeLoopCheck.put(entry.getSchemaReference().getNamespaceURI(), duplicates);
							}
						}

						final NodeList importNodeList = entry.getDocument().getElementsByTagNameNS(BindingQName.XS.getNamespaceURI().toString(), "import");
						for(int i = 0; i < importNodeList.getLength(); i++)
						{
							final Element includeElement = (Element)importNodeList.item(i);
							final URL schemaLocationURL = SchemaDocumentProcessor.getSchemaLocation(url, includeElement);

							// Check if we have two schemaReferences for a single targetNamespace
							// This should not happen for import, but can happen for include!
							final NamespaceURI importNamespaceURI = NamespaceURI.getInstance(includeElement.getAttribute("namespace"));
							final URL duplicate = importLoopCheck.get(importNamespaceURI);
							if(duplicate == null)
							{
								importLoopCheck.put(importNamespaceURI, schemaLocationURL);
								inner.insertElementAt(AbstractGenerator.parse(new SchemaReference(schemaLocationURL, importNamespaceURI)), 0);
								continue;
							}

							if(!duplicate.equals(schemaLocationURL))
							{
								logger.severe("There are two schemaReferences that define the namespace {" + importNamespaceURI + "}:\n" + duplicate + "\n" + schemaLocationURL);
								System.exit(1);
							}
						}
					}

					outer = inner;
				}
			}
			catch(MalformedURLException e)
			{
				logger.severe("Unknown URL format: " + schemaReference.getURL());
				System.exit(1);
			}

			schemas.addAll(schemasToGenerate);
		}

		for(SchemaDocument schema : schemas)
		{
			final Collection<URL> includes = includeLoopCheck.get(schema.getSchemaReference().getNamespaceURI());
			if(includes == null || includes.size() == 0)
				continue;

			final Collection<URL> externalIncludes = new ArrayList<URL>(includes.size());
			for(URL include : includes)
				if(!include.equals(schema.getSchemaReference().getURL()))
					externalIncludes.add(include);

			if(externalIncludes.size() != 0)
				schema.setIncludes(externalIncludes);
		}

		return schemas;
	}

	private static URL getSchemaLocation(URL baseURL, Element element) throws MalformedURLException
	{
		final String basedir = baseURL.getFile().substring(0, baseURL.getFile().lastIndexOf('/') + 1);
		return URLs.makeUrlFromPath(basedir, element.getAttribute("schemaLocation"));
	}
}
