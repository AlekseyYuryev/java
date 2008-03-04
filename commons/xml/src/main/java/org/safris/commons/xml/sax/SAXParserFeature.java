package org.safris.commons.xml.sax;

import org.safris.commons.xml.sax.SAXParserFeature;

	public class SAXParserFeature
	{
	public static final SAXParserFeature VALIDATION = new SAXParserFeature("http://xml.org/sax/features/validation");
	public static final SAXParserFeature SCHEMA_VALIDATION = new SAXParserFeature("http://apache.org/xml/features/validation/schema");
	public static final SAXParserFeature ALLOW_JAVA_ENCODINGS = new SAXParserFeature("http://apache.org/xml/features/allow-java-encodings");
	public static final SAXParserFeature CONTINUE_AFTER_FATAL_ERROR = new SAXParserFeature("http://apache.org/xml/features/continue-after-fatal-error");
	public static final SAXParserFeature DISALLOW_DOCTYPE = new SAXParserFeature("http://apache.org/xml/features/disallow-doctype-decl");
	public static final SAXParserFeature GENERATE_SYNTHETIC_ANNOTATIONS = new SAXParserFeature("http://apache.org/xml/features/generate-synthetic-annotations");
	public static final SAXParserFeature VALIDATE_ANNOTATIONS = new SAXParserFeature("http://apache.org/xml/features/validate-annotations");
	
	/**
	 * For some reason, this feature messes up stuff dealing with xsi:type!!!
	 */
	public static final SAXParserFeature HONOUR_ALL_SCHEMALOCATIONS = new SAXParserFeature("http://apache.org/xml/features/honour-all-schemaReferences");
	public static final SAXParserFeature STRING_INTERNING = new SAXParserFeature("http://xml.org/sax/features/string-interning");
	public static final SAXParserFeature NAMESPACES_FEATURE_ID = new SAXParserFeature("http://xml.org/sax/features/namespaces");
	public static final SAXParserFeature NAMESPACE_PREFIXES_FEATURE_ID = new SAXParserFeature("http://xml.org/sax/features/namespace-prefixes");
	public static final SAXParserFeature SCHEMA_FULL_CHECKING_FEATURE_ID = new SAXParserFeature("http://apache.org/xml/features/validation/schema-full-checking");
	
	private final String feature;
	
		public SAXParserFeature(String feature)
	{
	this.feature = feature;
	}
	
		protected String getFeature()
	{
	return feature;
	}
	
		public int hashCode()
	{
	return feature.hashCode();
	}
	
		public boolean equals(Object obj)
			{
		if(this == obj)
		return true;
			
		if(!(obj instanceof SAXParserFeature))
		return false;
		
	final SAXParserFeature saxParserFeature = (SAXParserFeature)obj;
return feature != null ? feature.equals(saxParserFeature.feature) : saxParserFeature.feature == null;
	}
}
