package org.safris.commons.xml.sax;

import com.sun.org.apache.xerces.internal.impl.Constants;

public class SAXProperty
{
	public static final SAXProperty ERROR_HANDLER = new SAXProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_HANDLER_PROPERTY);
	public static final SAXProperty ENTITY_RESOLVER = new SAXProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.ENTITY_RESOLVER_PROPERTY);
	public static final SAXProperty ENTITY_MANAGER = new SAXProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.ENTITY_MANAGER_PROPERTY);
	public static final SAXProperty ERROR_REPORTER = new SAXProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_REPORTER_PROPERTY);
	public static final SAXProperty XMLGRAMMAR_POOL = new SAXProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.XMLGRAMMAR_POOL_PROPERTY);
	public static final SAXProperty SYMBOL_TABLE = new SAXProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.SYMBOL_TABLE_PROPERTY);
	public static final SAXProperty SECURITY_MANAGER = new SAXProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.SECURITY_MANAGER_PROPERTY);
	public static final SAXProperty SCHEMA_LOCATION = new SAXProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.SCHEMA_LOCATION);
	public static final SAXProperty DTD_SCANNER = new SAXProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.DTD_SCANNER_PROPERTY);
	public static final SAXProperty DTD_PROCESSOR = new SAXProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.DTD_PROCESSOR_PROPERTY);
	public static final SAXProperty DTD_VALIDATOR = new SAXProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.DTD_VALIDATOR_PROPERTY);
	private final String property;

	protected SAXProperty(String property)
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

		if(!(obj instanceof SAXProperty))
			return false;

		final SAXProperty saxProperty = (SAXProperty)obj;
		return property != null ? property.equals(saxProperty.property) : saxProperty.property == null;
	}
}
