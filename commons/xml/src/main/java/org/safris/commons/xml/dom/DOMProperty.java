/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.commons.xml.dom;

import com.sun.org.apache.xerces.internal.impl.Constants;

public class DOMProperty
{
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

	protected DOMProperty(String property)
	{
		this.property = property;
	}

	protected String getProperty()
	{
		return property;
	}

	public int hashCode()
	{
		return property.hashCode();
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof DOMProperty))
			return false;

		final DOMProperty domProperty = (DOMProperty)obj;
		return property != null ? property.equals(domProperty.property) : domProperty.property == null;
	}
}
