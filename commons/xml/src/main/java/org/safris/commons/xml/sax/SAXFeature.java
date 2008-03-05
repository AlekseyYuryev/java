package org.safris.commons.xml.sax;

import com.sun.org.apache.xerces.internal.impl.Constants;

public class SAXFeature
{
	public static final SAXFeature VALIDATION = new SAXFeature(Constants.SAX_FEATURE_PREFIX + Constants.VALIDATION_FEATURE);
	public static final SAXFeature SCHEMA_VALIDATION = new SAXFeature(Constants.XERCES_FEATURE_PREFIX + Constants.SCHEMA_VALIDATION_FEATURE);
	public static final SAXFeature ALLOW_JAVA_ENCODINGS = new SAXFeature(Constants.XERCES_FEATURE_PREFIX + Constants.ALLOW_JAVA_ENCODINGS_FEATURE);
	public static final SAXFeature CONTINUE_AFTER_FATAL_ERROR = new SAXFeature(Constants.XERCES_FEATURE_PREFIX + Constants.CONTINUE_AFTER_FATAL_ERROR_FEATURE);
	public static final SAXFeature DISALLOW_DOCTYPE = new SAXFeature(Constants.XERCES_FEATURE_PREFIX + Constants.DISALLOW_DOCTYPE_DECL_FEATURE);
	public static final SAXFeature GENERATE_SYNTHETIC_ANNOTATIONS = new SAXFeature(Constants.XERCES_FEATURE_PREFIX + Constants.GENERATE_SYNTHETIC_ANNOTATIONS_FEATURE);
	public static final SAXFeature VALIDATE_ANNOTATIONS = new SAXFeature(Constants.XERCES_FEATURE_PREFIX + Constants.VALIDATE_ANNOTATIONS_FEATURE);
	public static final SAXFeature DYNAMIC_VALIDATION = new SAXFeature(Constants.XERCES_FEATURE_PREFIX + Constants.DYNAMIC_VALIDATION_FEATURE);
	public static final SAXFeature DISALLOW_DOCTYPE_DECL = new SAXFeature(Constants.XERCES_FEATURE_PREFIX + Constants.DISALLOW_DOCTYPE_DECL_FEATURE);
	public static final SAXFeature VALIDATE_CONTENT_MODELS = new SAXFeature(Constants.XERCES_FEATURE_PREFIX + Constants.VALIDATE_CONTENT_MODELS_FEATURE);
	public static final SAXFeature WARN_ON_DUPLICATE_ATTDEF = new SAXFeature(Constants.XERCES_FEATURE_PREFIX + Constants.WARN_ON_DUPLICATE_ATTDEF_FEATURE);
	public static final SAXFeature WARN_ON_DUPLICATE_ENTITYDEF = new SAXFeature(Constants.XERCES_FEATURE_PREFIX + Constants.WARN_ON_DUPLICATE_ENTITYDEF_FEATURE);
	public static final SAXFeature VALIDATE_DATATYPES = new SAXFeature(Constants.XERCES_FEATURE_PREFIX + Constants.VALIDATE_DATATYPES_FEATURE);
	/**
	 * For some reason, this feature messes up stuff dealing with xsi:type!!!
	 */
	public static final SAXFeature HONOUR_ALL_SCHEMALOCATIONS = new SAXFeature(Constants.XERCES_FEATURE_PREFIX + Constants.HONOUR_ALL_SCHEMALOCATIONS_FEATURE);
	public static final SAXFeature STRING_INTERNING = new SAXFeature(Constants.SAX_FEATURE_PREFIX + Constants.STRING_INTERNING_FEATURE);
	// FIXME: DOM!
	public static final SAXFeature NAMESPACES_FEATURE_ID = new SAXFeature(Constants.SAX_FEATURE_PREFIX + Constants.DOM_NAMESPACES);
	public static final SAXFeature NAMESPACE_PREFIXES_FEATURE_ID = new SAXFeature(Constants.SAX_FEATURE_PREFIX + Constants.NAMESPACE_PREFIXES_FEATURE);
	public static final SAXFeature SCHEMA_FULL_CHECKING_FEATURE_ID = new SAXFeature(Constants.XERCES_FEATURE_PREFIX + Constants.SCHEMA_FULL_CHECKING);

	private final String feature;

	public SAXFeature(String feature)
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

		if(!(obj instanceof SAXFeature))
			return false;

		final SAXFeature saxFeature = (SAXFeature)obj;
		return feature != null ? feature.equals(saxFeature.feature) : saxFeature.feature == null;
	}
}
