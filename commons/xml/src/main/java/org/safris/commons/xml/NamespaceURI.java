/*  Copyright Safris Software 2006
 *
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.commons.xml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.w3c.dom.Node;

public final class NamespaceURI {
  // subjectively chosen
  public static final String W3C_XML_SCHEMA_PREFIX = "xs";
  public static final String W3C_XML_SCHEMA_INSTANCE_PREFIX = "xsi";

  public static final QName XS = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "", W3C_XML_SCHEMA_PREFIX);
  public static final QName XSI = new QName(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "", W3C_XML_SCHEMA_INSTANCE_PREFIX);

  // staticly defined
  public static final QName XML = new QName(XMLConstants.XML_NS_URI, "", XMLConstants.XML_NS_PREFIX);
  public static final QName XMLNS = new QName(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "", XMLConstants.XMLNS_ATTRIBUTE);

  private static final Map<String,NamespaceURI> instances = new HashMap<String,NamespaceURI>();

  public static NamespaceURI getInstance(final String namespaceURI) {
    if (namespaceURI == null)
      throw new NullPointerException("namespaceURI == null");

    NamespaceURI value = instances.get(namespaceURI);
    if (value == null)
      instances.put(namespaceURI, value = new NamespaceURI(namespaceURI));

    return value;
  }

  public static String lookupNamespaceURI(final Node parent, final String prefix) {
    if (XML.getPrefix().equals(prefix))
      return XML.getNamespaceURI();

    if (XMLNS.getPrefix().equals(prefix))
      return XMLNS.getNamespaceURI();

    return parent.lookupNamespaceURI(prefix);
  }

  private final String namespaceURI;
  private final PackageName packageName;

  private NamespaceURI(final String namespaceURI) {
    if (namespaceURI == null)
      throw new NullPointerException("namespaceURI == null");

    this.namespaceURI = namespaceURI.intern();
    this.packageName = new PackageName(NamespaceBinding.getPackageFromNamespace(namespaceURI));
  }

  public PackageName getPackageName() {
    return packageName;
  }

  public boolean equals(final Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof NamespaceURI))
      return false;

    return namespaceURI.equals(((NamespaceURI)obj).namespaceURI);
  }

  public int hashCode() {
    return namespaceURI.hashCode();
  }

  public String toString() {
    return namespaceURI;
  }
}