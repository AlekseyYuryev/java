/* Copyright (c) 2014 Seva Safris
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

package org.safris.maven.plugin.xml.validator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.lang.Resources;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * Half-baked implementation. This is missing the caching routine for the generic case.
 */
public class CachedResourceResolver implements LSResourceResolver {
  private static final Map<String,LSInput> resources = new HashMap<String,LSInput>(4);

  @Override
  public LSInput resolveResource(final String type, final String namespaceURI, final String publicId, String systemId, final String baseURI) {
    try {
      //System.err.println(type + ", " + namespaceURI + ", " + publicId + ", " + systemId + ", " + baseURI);
      if ("http://www.w3.org/2001/XMLSchema.dtd".equals(systemId) || "XMLSchema.dtd".equals(systemId)) {
        systemId = "http://www.w3.org/2001/XMLSchema.dtd";
        LSInput resource = resources.get(systemId);
        if (resource != null)
          return resource;

        synchronized (resources) {
          resource = resources.get(systemId);
          if (resource != null)
            return resource;

          resources.put(systemId, resource = new CachedResourceInput());
          resource.setByteStream(Resources.getResource("XMLSchema.dtd").getURL().openStream());
          return resource;
        }
      }

      if ("datatypes.dtd".equals(systemId)) {
        LSInput resource = resources.get(systemId);
        if (resource != null)
          return resource;

        synchronized (resources) {
          resource = resources.get(systemId);
          if (resource != null)
            return resource;

          resources.put(systemId, resource = new CachedResourceInput());
          resource.setByteStream(Resources.getResource("datatypes.dtd").getURL().openStream());
          return resource;
        }
      }

      if ("http://www.w3.org/2001/xml.xsd".equals(systemId)) {
        LSInput resource = resources.get(systemId);
        if (resource != null)
          return resource;

        synchronized (resources) {
          resource = resources.get(systemId);
          if (resource != null)
            return resource;

          resources.put(systemId, resource = new CachedResourceInput());
          resource.setByteStream(Resources.getResource("xml.xsd").getURL().openStream());
          return resource;
        }
      }

      return null;
    }
    catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }
}