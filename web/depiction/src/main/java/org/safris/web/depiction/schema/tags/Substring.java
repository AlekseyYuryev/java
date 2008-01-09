package org.safris.web.depiction.schema.tags;

/**
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
*/

public class Substring extends org.safris.web.depiction.schema.xhtml.tags.ITaglib<org.safris.xml.bind.runtime.lang.ComplexType>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "substring");

	static
	{
		getElementBindings().put(NAME, org.safris.web.depiction.schema.tags.Substring.class.getName());
	}

	private org.safris.web.depiction.schema.tags.Substring.PathAttr _pathAttr = null;
	private org.safris.web.depiction.schema.tags.Substring.NameAttr _nameAttr = null;
	private org.safris.web.depiction.schema.tags.Substring.VarAttr _varAttr = null;

	public Substring(org.safris.web.depiction.schema.xhtml.tags.ITaglib<org.safris.xml.bind.runtime.lang.ComplexType> binding)
	{
		super(binding);
		if(binding instanceof org.safris.web.depiction.schema.tags.Substring)
		{
			org.safris.web.depiction.schema.tags.Substring element = (org.safris.web.depiction.schema.tags.Substring)binding;
			this._pathAttr = element._pathAttr != null ? element._pathAttr.clone() : null;
			this._nameAttr = element._nameAttr != null ? element._nameAttr.clone() : null;
			this._varAttr = element._varAttr != null ? element._varAttr.clone() : null;
		}
	}

	public Substring()
	{
		super();
	}

	public void setPathAttr(org.safris.web.depiction.schema.tags.Substring.PathAttr _pathAttr)
	{
		this._pathAttr = _pathAttr;
	}

	public org.safris.web.depiction.schema.tags.Substring.PathAttr getPathAttr()
	{
		return _pathAttr;
	}

	public void setNameAttr(org.safris.web.depiction.schema.tags.Substring.NameAttr _nameAttr)
	{
		this._nameAttr = _nameAttr;
	}

	public org.safris.web.depiction.schema.tags.Substring.NameAttr getNameAttr()
	{
		return _nameAttr;
	}

	public void setVarAttr(org.safris.web.depiction.schema.tags.Substring.VarAttr _varAttr)
	{
		this._varAttr = _varAttr;
	}

	public org.safris.web.depiction.schema.tags.Substring.VarAttr getVarAttr()
	{
		return _varAttr;
	}

	protected javax.xml.namespace.QName _getName()
	{
		return NAME;
	}

	public org.w3c.dom.Element marshal() throws org.safris.xml.bind.runtime.lang.MarshalException, org.safris.xml.bind.runtime.lang.ValidationException
	{
		org.w3c.dom.Element root = createElementNS(_getName().getNamespaceURI(), _getName().getLocalPart());
		org.w3c.dom.Element element = marshal(root, _getName(), _getTypeName());
		if(org.safris.xml.bind.runtime.util.Validator.getSystemValidator() != null)
			org.safris.xml.bind.runtime.util.Validator.getSystemValidator().validateMarshal(element);

		return element;
	}

	protected org.w3c.dom.Element marshal(org.w3c.dom.Element parent, javax.xml.namespace.QName name, javax.xml.namespace.QName typeName) throws org.safris.xml.bind.runtime.lang.MarshalException
	{
		org.w3c.dom.Element element = super.marshal(parent, name, typeName);
		if(_pathAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_pathAttr, "path", element));
		}

		if(_nameAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_nameAttr, "name", element));
		}

		if(_varAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_varAttr, "var", element));
		}

		return element;
	}

	protected void parse(org.w3c.dom.Element element) throws org.safris.xml.bind.runtime.lang.UnmarshalException, org.safris.xml.bind.runtime.lang.ValidationException
	{
		org.w3c.dom.NamedNodeMap namedNodeMap = element.getAttributes();
		org.w3c.dom.Node attribute = null;
		for(int i = 0; i < namedNodeMap.getLength(); i++)
		{
			attribute = namedNodeMap.item(i);
			if(attribute == null || XMLNS.getLocalPart().equals(attribute.getPrefix()))
			{
				continue;
			}
			else if("path".equals(attribute.getLocalName()))
			{
				this._pathAttr = (org.safris.web.depiction.schema.tags.Substring.PathAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Substring.PathAttr.class, element, attribute);
			}
			else if("name".equals(attribute.getLocalName()))
			{
				this._nameAttr = (org.safris.web.depiction.schema.tags.Substring.NameAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Substring.NameAttr.class, element, attribute);
			}
			else if("var".equals(attribute.getLocalName()))
			{
				this._varAttr = (org.safris.web.depiction.schema.tags.Substring.VarAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Substring.VarAttr.class, element, attribute);
			}
		}

		super.parse(element);
	}

	public org.safris.web.depiction.schema.tags.Substring clone()
	{
		return new org.safris.web.depiction.schema.tags.Substring(this);
	}

	public boolean equals(java.lang.Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof org.safris.web.depiction.schema.tags.Substring))
			return false;

		org.safris.web.depiction.schema.tags.Substring binding = (org.safris.web.depiction.schema.tags.Substring)obj;
		if((this._pathAttr == null && binding._pathAttr != null) || (this._pathAttr != null && !this._pathAttr.equals(binding._pathAttr)))
			return false;

		if((this._nameAttr == null && binding._nameAttr != null) || (this._nameAttr != null && !this._nameAttr.equals(binding._nameAttr)))
			return false;

		if((this._varAttr == null && binding._varAttr != null) || (this._varAttr != null && !this._varAttr.equals(binding._varAttr)))
			return false;

		return super.equals(obj);
	}

	public int hashCode()
	{
		java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
		stringBuffer.append(_pathAttr != null ? _pathAttr.hashCode() : 0).append("-");
		stringBuffer.append(_nameAttr != null ? _nameAttr.hashCode() : 0).append("-");
		stringBuffer.append(_varAttr != null ? _varAttr.hashCode() : 0).append("-");
		return stringBuffer.toString().hashCode();
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class PathAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "path");

		public PathAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public PathAttr(java.lang.String value)
		{
			super(value);
		}

		protected PathAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public org.safris.web.depiction.schema.tags.Substring.PathAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.Substring.PathAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class NameAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "name");

		public NameAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public NameAttr(java.lang.String value)
		{
			super(value);
		}

		protected NameAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public org.safris.web.depiction.schema.tags.Substring.NameAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.Substring.NameAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class VarAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "var");

		public VarAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public VarAttr(java.lang.String value)
		{
			super(value);
		}

		protected VarAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public org.safris.web.depiction.schema.tags.Substring.VarAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.Substring.VarAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}
}