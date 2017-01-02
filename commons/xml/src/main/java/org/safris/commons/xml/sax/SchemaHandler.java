/* Copyright (c) 2016 Seva Safris
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

import java.net.MalformedURLException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.XMLConstants;

import org.safris.commons.lang.Paths;
import org.safris.commons.net.CachedURL;
import org.safris.commons.net.URLs;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SchemaHandler extends XMLHandler {
  private Set<CachedURL> includes = new LinkedHashSet<CachedURL>();
  private boolean referencesOnlyLocal = true;

  public SchemaHandler(final CachedURL schemaLocation) {
    super(schemaLocation);
  }

  public Map<String,CachedURL> getImports() {
    return super.schemaLocations;
  }

  public Set<CachedURL> getIncludes() {
    return includes;
  }

  public boolean referencesOnlyLocal() {
    return referencesOnlyLocal;
  }

  @Override
  public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
    if (XMLConstants.W3C_XML_SCHEMA_NS_URI.equals(uri)) {
      if ("import".equals(localName)) {
        String namespace = null;
        String schemaLocation = null;
        for (int i = 0; i < attributes.getLength(); i++) {
          final String attributeName = attributes.getLocalName(i);
          if ("namespace".equals(attributeName)) {
            namespace = attributes.getValue(i);
            if (schemaLocation != null)
              break;
          }
          else if ("schemaLocation".equals(attributeName)) {
            schemaLocation = attributes.getValue(i);
            if (namespace != null)
              break;
          }
        }

        try {
          final String path = getPath(URLs.toExternalForm(url), schemaLocation);
          referencesOnlyLocal = Paths.isLocal(path) && referencesOnlyLocal;
          final CachedURL locationURL = new CachedURL(path);
          if (!schemaLocations.containsKey(namespace))
            schemaLocations.put(namespace, locationURL);
        }
        catch (final MalformedURLException e) {
          throw new SAXException(e);
        }
      }
      else if ("include".equals(localName)) {
        for (int i = 0; i < attributes.getLength(); i++) {
          if ("schemaLocation".equals(attributes.getLocalName(i))) {
            final String schemaLocation = attributes.getValue(i);
            try {
              final String path = getPath(URLs.toExternalForm(url), schemaLocation);
              referencesOnlyLocal = Paths.isLocal(path) && referencesOnlyLocal;
              final CachedURL locationURL = new CachedURL(path);
              includes.add(locationURL);
            }
            catch (final MalformedURLException e) {
              throw new SAXException(e);
            }
          }
        }
      }
      else if (!"schema".equals(localName) && !"annotation".equals(localName) && !"redefine".equals(localName)) {
        throw new SAXInterruptException();
      }
    }
  }
}