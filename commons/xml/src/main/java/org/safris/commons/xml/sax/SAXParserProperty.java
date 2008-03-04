package org.safris.commons.xml.sax;

public class SAXParserProperty
{
	public static final SAXParserProperty ERROR_HANDLER = new SAXParserProperty("http://apache.org/xml/properties/internal/error-handler");
	public static final SAXParserProperty JAXP_SCHEMA_SOURCE = new SAXParserProperty("http://java.sun.com/xml/jaxp/properties/schemaSource");
	public static final SAXParserProperty ENTITY_RESOLVER = new SAXParserProperty("http://apache.org/xml/properties/internal/entity-resolver");
	public static final SAXParserProperty ENTITY_MANAGER = new SAXParserProperty("http://apache.org/xml/properties/internal/entity-manager");
	public static final SAXParserProperty ERROR_REPORTER = new SAXParserProperty("http://apache.org/xml/properties/internal/error-reporter");
	public static final SAXParserProperty XMLGRAMMAR_POOL = new SAXParserProperty("http://apache.org/xml/properties/internal/grammar-pool");
	public static final SAXParserProperty SYMBOL_TABLE = new SAXParserProperty("http://apache.org/xml/properties/internal/symbol-table");
	public static final SAXParserProperty SECURITY_MANAGER = new SAXParserProperty("http://apache.org/xml/properties/security-manager");
	private final String property;

	protected SAXParserProperty(String property)
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

		if(!(obj instanceof SAXParserProperty))
			return false;

		final SAXParserProperty saxParserProperty = (SAXParserProperty)obj;
		return property != null ? property.equals(saxParserProperty.property) : saxParserProperty.property == null;
	}
}
