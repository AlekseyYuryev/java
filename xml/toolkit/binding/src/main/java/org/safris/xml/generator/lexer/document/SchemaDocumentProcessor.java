/* Copyright (c) 2008 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

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
import java.util.logging.Logger;

import org.safris.commons.net.URLs;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.commons.xml.NamespaceURI;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.xml.generator.lexer.processor.reference.SchemaReference;
import org.safris.xml.toolkit.binding.AbstractGenerator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public final class SchemaDocumentProcessor implements PipelineEntity, PipelineProcessor<GeneratorContext,SchemaReference,SchemaDocument> {
  protected static final Logger logger = Logger.getLogger(SchemaDocumentProcessor.class.getName());

  private static final String[] includeStrings = new String[] {
    "include",
    "redefine"
  };

  @Override
  public Collection<SchemaDocument> process(final GeneratorContext pipelineContext, final Collection<SchemaReference> selectedSchemas, final PipelineDirectory<GeneratorContext,SchemaReference,SchemaDocument> directory) {
    if (selectedSchemas == null || selectedSchemas.size() == 0)
      return null;

    final Collection<SchemaDocument> schemas = new LinkedHashSet<SchemaDocument>();
    final Map<NamespaceURI,URL> importLoopCheck = new HashMap<NamespaceURI,URL>();
    final Map<NamespaceURI,Collection<URL>> includeLoopCheck = new HashMap<NamespaceURI,Collection<URL>>();

    for (final SchemaReference schemaReference : selectedSchemas) {
      if (schemaReference == null)
        continue;

      final Stack<SchemaDocument> schemasToGenerate = new Stack<SchemaDocument>();

      try {
        URL url = URLs.canonicalizeURL(schemaReference.getURL());
        // First we need to find all of the imports and includes
        Collection<SchemaDocument> outer = new Stack<SchemaDocument>();
        outer.add(AbstractGenerator.parse(schemaReference));
        importLoopCheck.put(schemaReference.getNamespaceURI(), url);
        while (outer.size() != 0) {
          schemasToGenerate.addAll(0, outer);
          final Stack<SchemaDocument> inner = new Stack<SchemaDocument>();
          for (final SchemaDocument schemaDocument : outer) {
            NodeList includeNodeList = null;
            for (final String includeString : includeStrings) {
              includeNodeList = schemaDocument.getDocument().getElementsByTagNameNS(UniqueQName.XS.getNamespaceURI().toString(), includeString);
              for (int i = 0; i < includeNodeList.getLength(); i++) {
                final Element includeElement = (Element)includeNodeList.item(i);
                final URL schemaLocationURL = SchemaDocumentProcessor.getSchemaLocation(schemaDocument.getSchemaReference().getURL(), includeElement);

                // Don't want to get into an infinite loop
                Collection<URL> duplicates = includeLoopCheck.get(schemaDocument.getSchemaReference().getNamespaceURI());
                if (schemaLocationURL.equals(schemaDocument.getSchemaReference().getURL()) || (duplicates != null && duplicates.contains(schemaLocationURL)))
                    continue;

                final SchemaReference includeSchemaReference = new SchemaReference(schemaLocationURL, schemaDocument.getSchemaReference().getNamespaceURI(), schemaDocument.getSchemaReference().getPrefix(), true);
                inner.insertElementAt(AbstractGenerator.parse(includeSchemaReference), 0);
                if (duplicates == null)
                    duplicates = new ArrayList<URL>();

                duplicates.add(schemaLocationURL);
                logger.info("Adding " + new File(schemaLocationURL.getFile()).getName() + " for {" + schemaDocument.getSchemaReference().getNamespaceURI() + "}");
                includeLoopCheck.put(schemaDocument.getSchemaReference().getNamespaceURI(), duplicates);
              }
            }

            final NodeList importNodeList = schemaDocument.getDocument().getElementsByTagNameNS(UniqueQName.XS.getNamespaceURI().toString(), "import");
            for (int i = 0; i < importNodeList.getLength(); i++) {
              final Element importElement = (Element)importNodeList.item(i);
              final URL schemaLocationURL = SchemaDocumentProcessor.getSchemaLocation(schemaDocument.getSchemaReference().getURL(), importElement);

              // Check if we have two schemaReferences for a single targetNamespace
              // This should not happen for import, but can happen for include!
              final NamespaceURI importNamespaceURI = NamespaceURI.getInstance(importElement.getAttribute("namespace"));
              final URL duplicate = importLoopCheck.get(importNamespaceURI);
              if (duplicate == null) {
                importLoopCheck.put(importNamespaceURI, schemaLocationURL);
                inner.insertElementAt(AbstractGenerator.parse(new SchemaReference(schemaLocationURL, importNamespaceURI, false)), 0);
                continue;
              }

              if (!duplicate.equals(schemaLocationURL)) {
                logger.severe("There are two schemaReferences that define the namespace {" + importNamespaceURI + "}:\n[x] " + schemaDocument.getSchemaReference().getURL() + "\n[1] " + duplicate + "\n[2] " + schemaLocationURL);
                System.exit(1);
              }
            }
          }

          outer = inner;
        }
      }
      catch (final MalformedURLException e) {
        logger.severe("Unknown URL format: " + schemaReference.getURL());
        System.exit(1);
      }

      schemas.addAll(schemasToGenerate);
    }

    for (final SchemaDocument schema : schemas) {
      final Collection<URL> includes = includeLoopCheck.get(schema.getSchemaReference().getNamespaceURI());
      if (includes == null || includes.size() == 0)
        continue;

      final Collection<URL> externalIncludes = new ArrayList<URL>(includes.size());
      for (final URL include : includes)
        if (!include.equals(schema.getSchemaReference().getURL()))
          externalIncludes.add(include);

      if (externalIncludes.size() != 0)
        schema.setIncludes(externalIncludes);
    }

    return schemas;
  }

  private static URL getSchemaLocation(final URL baseURL, final Element element) throws MalformedURLException {
    final String basedir = baseURL.getFile().substring(0, baseURL.getFile().lastIndexOf('/') + 1);
    final String schemaLocation = element.getAttribute("schemaLocation");
    if (URLs.isAbsolute(schemaLocation))
      return new URL(schemaLocation);

    return URLs.makeUrlFromPath(basedir, schemaLocation);
  }
}