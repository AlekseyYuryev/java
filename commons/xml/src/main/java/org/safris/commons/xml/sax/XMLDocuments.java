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

package org.safris.commons.xml.sax;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;

import org.safris.commons.net.CachedURL;
import org.safris.commons.net.Sockets;
import org.safris.commons.net.URLs;
import org.xml.sax.SAXException;

public final class XMLDocuments {
  private static SAXParserFactory factory;

  static {
    try {
      factory = SAXParserFactory.newInstance("org.apache.xerces.jaxp.SAXParserFactoryImpl", null);
    }
    catch (final FactoryConfigurationError e) {
      factory = SAXParserFactory.newInstance();
    }
  }

  private static SAXParser newParser() throws SAXException {
    factory.setNamespaceAware(true);
    factory.setValidating(true);
    try {
      try {
        factory.setSchema(SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1").newSchema());
      }
      catch (final IllegalArgumentException e) {
      }

      factory.setFeature("http://xml.org/sax/features/validation", true);
      factory.setFeature("http://apache.org/xml/features/validation/schema", true);
      factory.setFeature("http://apache.org/xml/features/validation/dynamic", false);
      factory.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
      factory.setFeature("http://apache.org/xml/features/honour-all-schemaLocations", true);
      factory.setFeature("http://apache.org/xml/features/continue-after-fatal-error", true);

      return factory.newSAXParser();
    }
    catch (final ParserConfigurationException e) {
      throw new SAXException(e);
    }
  }

  public static XMLDocument analyze(final File file, final boolean offline) throws IOException, SAXException {
    final DocumentHandler documentHandler = new DocumentHandler(new CachedURL(file.toURI().toURL()));
    if (offline)
      Sockets.disableNetwork();

    final SAXParser parser = newParser();
    parser.parse(file, documentHandler);
    parser.reset();
    final Map<String,SchemaLocation> references = new LinkedHashMap<String,SchemaLocation>();
    final boolean referencesOnlyLocal = imports(parser, offline, references, documentHandler.getNamespaceURIs(), documentHandler.getSchemaLocations());
    return new XMLDocument(references, documentHandler.isXSD(), documentHandler.referencesOnlyLocal() && referencesOnlyLocal);
  }

  private static boolean imports(final SAXParser parser, final boolean offline, final Map<String,SchemaLocation> references, final Set<String> namespaceURIs, final Map<String,CachedURL> schemaLocations) throws IOException, SAXException {
    boolean referencesOnlyLocal = true;
    for (final Map.Entry<String,CachedURL> schemaLocation : schemaLocations.entrySet()) {
      if (!references.containsKey(schemaLocation.getKey())) {
        if (!offline || (referencesOnlyLocal = schemaLocation.getValue().isLocal() && referencesOnlyLocal)) {
          final SchemaHandler schemaHandler = new SchemaHandler(schemaLocation.getValue());
          try {
            parser.reset();
            parser.parse(schemaLocation.getValue().openStream(), schemaHandler);
          }
          catch (final SAXInterruptException e) {
            schemaLocation.getValue().reset();
          }

          references.put(schemaLocation.getKey(), new SchemaLocation(schemaLocation.getKey(), schemaLocation.getValue()));
          for (final String sl : schemaHandler.getImports().keySet())
            if (!references.containsKey(sl))
              namespaceURIs.add(sl);

          namespaceURIs.remove(schemaLocation.getKey());
          if (namespaceURIs.isEmpty())
            break;

          referencesOnlyLocal = imports(parser, offline, references, namespaceURIs, schemaHandler.getImports()) && referencesOnlyLocal;
          referencesOnlyLocal = includes(parser, offline, references, schemaLocation.getKey(), schemaHandler.getIncludes()) && referencesOnlyLocal;
        }
      }
    }

    return referencesOnlyLocal;
  }

  private static boolean includes(final SAXParser parser, final boolean offline, final Map<String,SchemaLocation> references, final String namespaceURI, final Set<CachedURL> includes) throws IOException, SAXException {
    boolean referencesOnlyLocal = false;
    for (final CachedURL include : includes) {
      if (!offline || (referencesOnlyLocal = include.isLocal() && referencesOnlyLocal)) {
        final SchemaHandler schemaHandler = new SchemaHandler(include);
        try {
          parser.reset();
          parser.parse(include.openStream(), schemaHandler);
        }
        catch (final SAXInterruptException e) {
          include.reset();
        }

        references.get(namespaceURI).getLocation().put(URLs.toExternalForm(include), include);
        referencesOnlyLocal = includes(parser, offline, references, namespaceURI, schemaHandler.getIncludes()) && referencesOnlyLocal;
      }
    }

    return referencesOnlyLocal;
  }
}