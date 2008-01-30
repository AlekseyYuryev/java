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
import org.safris.commons.util.URLs;
import org.safris.commons.util.logging.ExitSevereError;
import org.safris.commons.util.xml.NamespaceURI;
import org.safris.xml.generator.lexer.phase.document.SchemaDocument;
import org.safris.xml.generator.lexer.phase.reference.SchemaReference;
import org.safris.xml.generator.module.phase.BindingContext;
import org.safris.xml.generator.module.phase.BindingQName;
import org.safris.xml.generator.module.phase.ElementModule;
import org.safris.xml.generator.module.phase.HandlerDirectory;
import org.safris.xml.generator.module.phase.Phase;
import org.safris.xml.toolkit.binding.AbstractGenerator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SchemaDocumentPhase extends Phase<SchemaReference,SchemaDocument> implements ElementModule<SchemaDocument>
{
	private static final String[] includeStrings = new String[]
	{
		"include",
		"redefine"
	};

	public Collection<SchemaDocument> manipulate(Collection<SchemaReference> selectedSchemas, BindingContext bindingContext, HandlerDirectory<SchemaReference, SchemaDocument> directory)
	{
		final Collection<SchemaDocument> schemas = new LinkedHashSet<SchemaDocument>();
		final Map<NamespaceURI,URL> importLoopCheck = new HashMap<NamespaceURI,URL>();
		final Map<NamespaceURI,Collection<URL>> includeLoopCheck = new HashMap<NamespaceURI,Collection<URL>>();

		for(SchemaReference schemaReference : selectedSchemas)
		{
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
								final URL schemaLocationURL = SchemaDocumentPhase.getSchemaLocation(url, includeElement);

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
							final URL schemaLocationURL = SchemaDocumentPhase.getSchemaLocation(url, includeElement);

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
								throw new ExitSevereError("There are two schemaReferences that define the namespace {" + importNamespaceURI + "}:\n" + duplicate + "\n" + schemaLocationURL);
						}
					}

					outer = inner;
				}
			}
			catch(MalformedURLException e)
			{
				throw new ExitSevereError("Unknown URL format: " + schemaReference.getURL());
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

		/*		System.out.println("DEBUG: Order of work on schemas:");
		 for(SchemaDocument schema : schemas)
		 {
		 System.out.println("--------------------------------");
		 System.out.println("URL: " + schema.getSchemaReference().getURL().getFile());
		 System.out.println("Namespace: " + schema.getSchemaReference().getNamespaceURI());
		 if(schema.getIncludes() != null)
		 for(URL url : schema.getIncludes())
		 System.out.println("\t" + url.getFile());
		 }*/

		return schemas;
	}

	private static URL getSchemaLocation(URL baseURL, Element element) throws MalformedURLException
	{
		final String basedir = baseURL.getFile().substring(0, baseURL.getFile().lastIndexOf('/') + 1);
		return URLs.makeUrlFromPath(basedir, element.getAttribute("schemaLocation"));
	}
}
