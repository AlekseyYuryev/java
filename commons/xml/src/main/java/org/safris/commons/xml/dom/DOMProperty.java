/*  Copyright Safris Software 2008
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

package org.safris.commons.xml.dom;

import com.sun.org.apache.xerces.internal.impl.Constants;

public class DOMProperty {
  /**
   * DOM node.
   */
  public static final DOMProperty DOM_NODE = new DOMProperty(Constants.SAX_PROPERTY_PREFIX + Constants.DOM_NODE_PROPERTY);

  /**
   * JAXP schemaSource: when used internally may include DTD sources (DOM).
   */
  public static final DOMProperty SCHEMA_SOURCE = new DOMProperty(Constants.JAXP_PROPERTY_PREFIX + Constants.SCHEMA_SOURCE);

  /**
   * JAXP schemaSource language: when used internally may include DTD namespace (DOM).
   */
  public static final DOMProperty SCHEMA_LANGUAGE = new DOMProperty(Constants.JAXP_PROPERTY_PREFIX + Constants.SCHEMA_LANGUAGE);

  /**
   * Resource resolver.
   */
  public static final DOMFeature DOM_RESOURCE_RESOLVER = new DOMFeature(Constants.SAX_PROPERTY_PREFIX + Constants.DOM_RESOURCE_RESOLVER);

  /**
   * Error handler.
   */
  public static final DOMFeature DOM_ERROR_HANDLER = new DOMFeature(Constants.SAX_PROPERTY_PREFIX + Constants.DOM_ERROR_HANDLER);

  /**
   * Schema type.
   */
  public static final DOMFeature DOM_SCHEMA_TYPE = new DOMFeature(Constants.SAX_PROPERTY_PREFIX + Constants.DOM_SCHEMA_TYPE);

  /**
   * Schema Location.
   */
  public static final DOMFeature DOM_SCHEMA_LOCATION = new DOMFeature(Constants.SAX_PROPERTY_PREFIX + Constants.DOM_SCHEMA_LOCATION);

  /**
   * XSModel.
   */
  public static final DOMFeature DOM_PSVI = new DOMFeature(Constants.SAX_PROPERTY_PREFIX + Constants.DOM_PSVI);

  /**
   * Current element node.
   */
  public static final DOMProperty CURRENT_ELEMENT_NODE = new DOMProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.CURRENT_ELEMENT_NODE_PROPERTY);

  /**
   * Document class name.
   */
  public static final DOMProperty DOCUMENT_CLASS_NAME = new DOMProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.DOCUMENT_CLASS_NAME_PROPERTY);

  private final String property;

  protected DOMProperty(String property) {
    this.property = property;
  }

  protected String getProperty() {
    return property;
  }

  public int hashCode() {
    return property.hashCode();
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof DOMProperty))
      return false;

    final DOMProperty domProperty = (DOMProperty)obj;
    return property != null ? property.equals(domProperty.property) : domProperty.property == null;
  }
}
