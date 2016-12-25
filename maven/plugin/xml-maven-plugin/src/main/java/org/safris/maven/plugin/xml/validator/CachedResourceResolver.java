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

package org.safris.maven.plugin.xml.validator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.lang.Paths;
import org.safris.commons.lang.Resources;
import org.safris.commons.net.URLConnections;
import org.safris.commons.net.URLs;
import org.safris.commons.xml.validator.OfflineValidationException;
import org.safris.commons.xml.validator.ValidatorError;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

public final class CachedResourceResolver implements LSResourceResolver {
  private final File basedir;
  private final boolean offline;

  public CachedResourceResolver(final File basedir, final boolean offline) {
    this.basedir = basedir;
    this.offline = offline;
  }

  private final Map<String,LSInput> resourceMap = new HashMap<String,LSInput>();

  @Override
  public LSInput resolveResource(final String type, final String namespaceURI, final String publicId, final String systemId, final String baseURI) {
    try {
//      if (namespaceURI != null)
//        System.out.println(namespaceURI + "\t" + (namespaceURI.length() < 40 ? "\t" : "") + publicId + "\t" + systemId + "\t" + baseURI);

      if ("http://www.w3.org/2001/XMLSchema".equals(namespaceURI)) {
        final LSInput resource = new CachedResourceInput(systemId, publicId, baseURI);
        resource.setByteStream(Resources.getResource("XMLSchema.xsd").getURL().openStream());
        return resource;
      }

      if (systemId == null)
        return resourceMap.get(namespaceURI);

      if ("http://www.w3.org/2001/XMLSchema.dtd".equals(systemId) || "XMLSchema.dtd".equals(systemId)) {
        final LSInput resource = new CachedResourceInput("http://www.w3.org/2001/XMLSchema.dtd", publicId, baseURI);
        resource.setByteStream(Resources.getResource("XMLSchema.dtd").getURL().openStream());
        return resource;
      }

      if ("datatypes.dtd".equals(systemId)) {
        final LSInput resource = new CachedResourceInput(systemId, publicId, baseURI);
        resource.setByteStream(Resources.getResource("datatypes.dtd").getURL().openStream());
        return resource;
      }

      if ("http://www.w3.org/2001/xml.xsd".equals(systemId)) {
        final LSInput resource = new CachedResourceInput(systemId, publicId, baseURI);
        resource.setByteStream(Resources.getResource("xml.xsd").getURL().openStream());
        return resource;
      }

      final URL url;
      if (!URLs.isAbsolute(systemId)) {
        final String parentBaseId;
        if (baseURI != null)
          parentBaseId = Paths.getParent(baseURI);
        else
          parentBaseId = basedir.getAbsolutePath();

        url = URLs.makeUrlFromPath(parentBaseId, systemId);
      }
      else {
        url = URLs.makeUrlFromPath(systemId);
      }

      if (offline && !URLs.isLocal(url))
        throw new OfflineValidationException(url.toExternalForm());

      LSInput resource = resourceMap.get(namespaceURI + systemId);
      if (resource == null) {
        resourceMap.put(namespaceURI + systemId, resource = new CachedResourceInput(systemId, publicId, baseURI));
        if (!resourceMap.containsKey(namespaceURI))
          resourceMap.put(namespaceURI, resource);
      }
      else if (resource.getByteStream() == null) {
        resource.setSystemId(systemId);
        resource.setPublicId(publicId);
        resource.setBaseURI(baseURI);
        resource.setByteStream(URLConnections.checkOpenRedirectStream(url));
      }

      return resource;
    }
    catch (final IOException e) {
      throw new ValidatorError(e);
    }
  }
}