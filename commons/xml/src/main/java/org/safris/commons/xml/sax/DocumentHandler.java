package org.safris.commons.xml.sax;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.xml.XMLConstants;

import org.safris.commons.lang.Paths;
import org.safris.commons.net.CachedURL;
import org.safris.commons.net.URLs;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DocumentHandler extends XMLHandler {
  private final Set<String> namespaceURIs = new HashSet<String>();
  private boolean referencesOnlyLocal = true;

  public DocumentHandler(final CachedURL url) {
    super(url);
  }

  public Set<String> getNamespaceURIs() {
    return namespaceURIs;
  }

  public Map<String,CachedURL> getSchemaLocations() {
    return super.schemaLocations;
  }

  private Boolean isXSD = null;

  public boolean isXSD() {
    if (isXSD == null)
      throw new IllegalStateException();

    return isXSD;
  }

  public boolean referencesOnlyLocal() {
    return referencesOnlyLocal;
  }

  @Override
  public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
    if (isXSD == null)
      isXSD = XMLConstants.W3C_XML_SCHEMA_NS_URI.equals(uri) && "schema".equals(localName);

    for (int i = 0; i < attributes.getLength(); i++) {
      final String namespaceURI = attributes.getURI(i);
      if (XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI.equals(namespaceURI) && "schemaLocation".equals(attributes.getLocalName(i))) {
        final String value = attributes.getValue(i);
        final StringTokenizer tokenizer = new StringTokenizer(value);
        while (tokenizer.hasMoreTokens()) {
          final String schemaNamespaceURI = tokenizer.nextToken();
          if (tokenizer.hasMoreTokens()) {
            final String location = tokenizer.nextToken();
            try {
              final String path = getPath(URLs.toExternalForm(url), location);
              referencesOnlyLocal = Paths.isLocal(path) && referencesOnlyLocal;
              final CachedURL locationURL = new CachedURL(path);
              if (!schemaLocations.containsKey(schemaNamespaceURI))
                schemaLocations.put(schemaNamespaceURI, locationURL);
            }
            catch (final MalformedURLException e) {
              throw new SAXException();
            }
          }
        }
      }
      else if (!XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI.equals(namespaceURI) && namespaceURI.length() != 0) {
        namespaceURIs.add(namespaceURI);
      }
    }

    if (!XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI.equals(uri) && uri.length() != 0)
      namespaceURIs.add(uri);
  }
}