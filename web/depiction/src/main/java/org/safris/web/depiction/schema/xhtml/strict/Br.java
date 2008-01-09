package org.safris.web.depiction.schema.xhtml.strict;

/**
 * forced line break
 *
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
 */

public class Br extends org.safris.xml.bind.runtime.lang.Binding<org.safris.xml.bind.runtime.lang.ComplexType>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "br");

	static
	{
		getElementBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.Br.class.getName());
	}

	private IdAttr _idAttr = null;
	private org.safris.xml.bind.runtime.lang.CloneableList<ClassAttr,org.safris.xml.bind.runtime.lang.Attribute> _classAttr = null;
	private StyleAttr _styleAttr = null;
	private TitleAttr _titleAttr = null;

	public Br(org.safris.web.depiction.schema.xhtml.strict.Br binding)
	{
		super(binding);
		if(binding instanceof org.safris.web.depiction.schema.xhtml.strict.Br)
		{
			org.safris.web.depiction.schema.xhtml.strict.Br element = (org.safris.web.depiction.schema.xhtml.strict.Br)binding;
			this._idAttr = element._idAttr != null ? element._idAttr.clone() : null;
			this._classAttr = element._classAttr != null ? element._classAttr.clone() : null;
			this._styleAttr = element._styleAttr != null ? element._styleAttr.clone() : null;
			this._titleAttr = element._titleAttr != null ? element._titleAttr.clone() : null;
		}
	}

	public Br()
	{
		super();
	}

	public void setIdAttr(IdAttr _idAttr)
	{
		this._idAttr = _idAttr;
	}

	public IdAttr getIdAttr()
	{
		return _idAttr;
	}

	public void addClassAttr(ClassAttr _classAttr)
	{
		if(this._classAttr == null)
			this._classAttr = new org.safris.xml.bind.runtime.lang.CloneableList<ClassAttr,org.safris.xml.bind.runtime.lang.Attribute>();

		this._classAttr.add(_classAttr);
	}

	public org.safris.xml.bind.runtime.lang.CloneableList<ClassAttr,org.safris.xml.bind.runtime.lang.Attribute> getClassAttr()
	{
		return _classAttr;
	}

	public void setStyleAttr(StyleAttr _styleAttr)
	{
		this._styleAttr = _styleAttr;
	}

	public StyleAttr getStyleAttr()
	{
		return _styleAttr;
	}

	public void setTitleAttr(TitleAttr _titleAttr)
	{
		this._titleAttr = _titleAttr;
	}

	public TitleAttr getTitleAttr()
	{
		return _titleAttr;
	}

	protected javax.xml.namespace.QName _getName()
	{
		return NAME;
	}

	protected javax.xml.namespace.QName _getTypeName()
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
		if(_idAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_idAttr, "id", element));
		}

		if(_classAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_classAttr, "class", element));
		}

		if(_styleAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_styleAttr, "style", element));
		}

		if(_titleAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_titleAttr, "title", element));
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
			else if("id".equals(attribute.getLocalName()))
			{
				this._idAttr = (IdAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(IdAttr.class, element, attribute);
			}
			else if("class".equals(attribute.getLocalName()))
			{
				this._classAttr = org.safris.xml.bind.runtime.lang.Binding._parseAttrList(ClassAttr.class, element, attribute);
			}
			else if("style".equals(attribute.getLocalName()))
			{
				this._styleAttr = (StyleAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(StyleAttr.class, element, attribute);
			}
			else if("title".equals(attribute.getLocalName()))
			{
				this._titleAttr = (TitleAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(TitleAttr.class, element, attribute);
			}
		}

		super.parse(element);
	}

	public org.safris.web.depiction.schema.xhtml.strict.Br clone()
	{
		return new org.safris.web.depiction.schema.xhtml.strict.Br(this);
	}

	public boolean equals(java.lang.Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof org.safris.web.depiction.schema.xhtml.strict.Br))
			return false;

		org.safris.web.depiction.schema.xhtml.strict.Br binding = (org.safris.web.depiction.schema.xhtml.strict.Br)obj;
		if((this._idAttr == null && binding._idAttr != null) || (this._idAttr != null && !this._idAttr.equals(binding._idAttr)))
			return false;

		if((this._classAttr == null && binding._classAttr != null) || (this._classAttr != null && !this._classAttr.equals(binding._classAttr)))
			return false;

		if((this._styleAttr == null && binding._styleAttr != null) || (this._styleAttr != null && !this._styleAttr.equals(binding._styleAttr)))
			return false;

		if((this._titleAttr == null && binding._titleAttr != null) || (this._titleAttr != null && !this._titleAttr.equals(binding._titleAttr)))
			return false;

		return super.equals(obj);
	}

	public int hashCode()
	{
		java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
		stringBuffer.append(_idAttr != null ? _idAttr.hashCode() : 0).append("-");
		stringBuffer.append(_classAttr != null ? _classAttr.hashCode() : 0).append("-");
		stringBuffer.append(_styleAttr != null ? _styleAttr.hashCode() : 0).append("-");
		stringBuffer.append(_titleAttr != null ? _titleAttr.hashCode() : 0).append("-");
		return stringBuffer.toString().hashCode();
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class IdAttr extends org.safris.xml.bind.runtime.types.IdType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "id");

		public IdAttr(org.safris.xml.bind.runtime.types.IdType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public IdAttr(java.lang.String value)
		{
			super(value);
		}

		protected IdAttr()
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

		public IdAttr clone()
		{
			return new IdAttr(this);
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

	public static class ClassAttr extends org.safris.xml.bind.runtime.lang.CloneableList<org.safris.xml.bind.runtime.types.NMTokenType,org.safris.xml.bind.runtime.lang.SimpleType>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "class");

		public ClassAttr(org.safris.xml.bind.runtime.lang.CloneableList<org.safris.xml.bind.runtime.types.NMTokenType,org.safris.xml.bind.runtime.lang.SimpleType> binding)
		{
			super(binding);
		}

		public ClassAttr(java.util.List<java.lang.String> value)
		{
			super(value);
		}

		protected ClassAttr(java.lang.String value)
		{
			super(value);
		}

		protected ClassAttr()
		{
			super();
		}

		public java.util.List<java.lang.String> getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.util.List<java.lang.String> value)
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

		public ClassAttr clone()
		{
			return new ClassAttr(this);
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

	public static class StyleAttr extends org.safris.web.depiction.schema.xhtml.strict.IStyleSheet<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "style");

		public StyleAttr(org.safris.web.depiction.schema.xhtml.strict.IStyleSheet<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public StyleAttr(java.lang.String value)
		{
			super(value);
		}

		protected StyleAttr()
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

		public StyleAttr clone()
		{
			return new StyleAttr(this);
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

	public static class TitleAttr extends org.safris.web.depiction.schema.xhtml.strict.IText<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "title");

		public TitleAttr(org.safris.web.depiction.schema.xhtml.strict.IText<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public TitleAttr(java.lang.String value)
		{
			super(value);
		}

		protected TitleAttr()
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

		public TitleAttr clone()
		{
			return new TitleAttr(this);
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