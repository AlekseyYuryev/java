/*  Copyright 2010 Safris Technologies Inc.
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

package org.safris.web.depiction.schema.xhtml.strict;

/**
 * script statements, which may include CDATA sections
 *
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
 */

public class Script extends org.safris.xml.bind.runtime.lang.Binding<org.safris.xml.bind.runtime.lang.ComplexType>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "script");

	static
	{
		getElementBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.Script.class.getName());
	}

	private org.safris.web.depiction.schema.xhtml.strict.Script.IdAttr _idAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Script.CharsetAttr _charsetAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Script.TypeAttr _typeAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Script.SrcAttr _srcAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Script.DeferAttr _deferAttr = null;
	private org.safris.web.depiction.schema.xml.namespace.XmlSpaceAttr _xmlSpaceAttr = null;

	public Script(org.safris.web.depiction.schema.xhtml.strict.Script binding)
	{
		super(binding);
		if(binding instanceof org.safris.web.depiction.schema.xhtml.strict.Script)
		{
			org.safris.web.depiction.schema.xhtml.strict.Script element = (org.safris.web.depiction.schema.xhtml.strict.Script)binding;
			this._idAttr = element._idAttr != null ? element._idAttr.clone() : null;
			this._charsetAttr = element._charsetAttr != null ? element._charsetAttr.clone() : null;
			this._typeAttr = element._typeAttr != null ? element._typeAttr.clone() : null;
			this._srcAttr = element._srcAttr != null ? element._srcAttr.clone() : null;
			this._deferAttr = element._deferAttr != null ? element._deferAttr.clone() : null;
			this._xmlSpaceAttr = element._xmlSpaceAttr != null ? element._xmlSpaceAttr.clone() : null;
		}
	}

	public Script()
	{
		super();
	}

	public void setIdAttr(org.safris.web.depiction.schema.xhtml.strict.Script.IdAttr _idAttr)
	{
		this._idAttr = _idAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Script.IdAttr getIdAttr()
	{
		return _idAttr;
	}

	public void setCharsetAttr(org.safris.web.depiction.schema.xhtml.strict.Script.CharsetAttr _charsetAttr)
	{
		this._charsetAttr = _charsetAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Script.CharsetAttr getCharsetAttr()
	{
		return _charsetAttr;
	}

	public void setTypeAttr(org.safris.web.depiction.schema.xhtml.strict.Script.TypeAttr _typeAttr)
	{
		this._typeAttr = _typeAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Script.TypeAttr getTypeAttr()
	{
		return _typeAttr;
	}

	public void setSrcAttr(org.safris.web.depiction.schema.xhtml.strict.Script.SrcAttr _srcAttr)
	{
		this._srcAttr = _srcAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Script.SrcAttr getSrcAttr()
	{
		return _srcAttr;
	}

	public void setDeferAttr(org.safris.web.depiction.schema.xhtml.strict.Script.DeferAttr _deferAttr)
	{
		this._deferAttr = _deferAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Script.DeferAttr getDeferAttr()
	{
		return _deferAttr;
	}

	public void setXmlSpaceAttr(org.safris.web.depiction.schema.xml.namespace.XmlSpaceAttr _xmlSpaceAttr)
	{
		this._xmlSpaceAttr = _xmlSpaceAttr;
	}

	public org.safris.web.depiction.schema.xml.namespace.XmlSpaceAttr getXmlSpaceAttr()
	{
		return _xmlSpaceAttr;
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

		if(_charsetAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_charsetAttr, "charset", element));
		}

		if(_typeAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_typeAttr, "type", element));
		}

		if(_srcAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_srcAttr, "src", element));
		}

		if(_deferAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_deferAttr, "defer", element));
		}

		if(_xmlSpaceAttr != null)
		{
			java.lang.String prefix = _getPrefix(element, org.safris.xml.bind.runtime.lang.Binding._getName(_xmlSpaceAttr).getNamespaceURI());
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_xmlSpaceAttr, prefix + ":" + org.safris.xml.bind.runtime.lang.Binding._getName(_xmlSpaceAttr).getLocalPart(), element));
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
				this._idAttr = (org.safris.web.depiction.schema.xhtml.strict.Script.IdAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Script.IdAttr.class, element, attribute);
			}
			else if("charset".equals(attribute.getLocalName()))
			{
				this._charsetAttr = (org.safris.web.depiction.schema.xhtml.strict.Script.CharsetAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Script.CharsetAttr.class, element, attribute);
			}
			else if("type".equals(attribute.getLocalName()))
			{
				this._typeAttr = (org.safris.web.depiction.schema.xhtml.strict.Script.TypeAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Script.TypeAttr.class, element, attribute);
			}
			else if("src".equals(attribute.getLocalName()))
			{
				this._srcAttr = (org.safris.web.depiction.schema.xhtml.strict.Script.SrcAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Script.SrcAttr.class, element, attribute);
			}
			else if("defer".equals(attribute.getLocalName()))
			{
				this._deferAttr = (org.safris.web.depiction.schema.xhtml.strict.Script.DeferAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Script.DeferAttr.class, element, attribute);
			}
			else if("http://www.w3.org/XML/1998/namespace".equals(attribute.getNamespaceURI()) && "space".equals(attribute.getLocalName()))
			{
				this._xmlSpaceAttr = (org.safris.web.depiction.schema.xml.namespace.XmlSpaceAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xml.namespace.XmlSpaceAttr.class, element, attribute);
			}
		}

		super.parse(element);
	}

	public org.safris.web.depiction.schema.xhtml.strict.Script clone()
	{
		return new org.safris.web.depiction.schema.xhtml.strict.Script(this);
	}

	public boolean equals(java.lang.Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof org.safris.web.depiction.schema.xhtml.strict.Script))
			return false;

		org.safris.web.depiction.schema.xhtml.strict.Script binding = (org.safris.web.depiction.schema.xhtml.strict.Script)obj;
		if((this._idAttr == null && binding._idAttr != null) || (this._idAttr != null && !this._idAttr.equals(binding._idAttr)))
			return false;

		if((this._charsetAttr == null && binding._charsetAttr != null) || (this._charsetAttr != null && !this._charsetAttr.equals(binding._charsetAttr)))
			return false;

		if((this._typeAttr == null && binding._typeAttr != null) || (this._typeAttr != null && !this._typeAttr.equals(binding._typeAttr)))
			return false;

		if((this._srcAttr == null && binding._srcAttr != null) || (this._srcAttr != null && !this._srcAttr.equals(binding._srcAttr)))
			return false;

		if((this._deferAttr == null && binding._deferAttr != null) || (this._deferAttr != null && !this._deferAttr.equals(binding._deferAttr)))
			return false;

		if((this._xmlSpaceAttr == null && binding._xmlSpaceAttr != null) || (this._xmlSpaceAttr != null && !this._xmlSpaceAttr.equals(binding._xmlSpaceAttr)))
			return false;

		return super.equals(obj);
	}

	public int hashCode()
	{
		java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
		stringBuffer.append(_idAttr != null ? _idAttr.hashCode() : 0).append("-");
		stringBuffer.append(_charsetAttr != null ? _charsetAttr.hashCode() : 0).append("-");
		stringBuffer.append(_typeAttr != null ? _typeAttr.hashCode() : 0).append("-");
		stringBuffer.append(_srcAttr != null ? _srcAttr.hashCode() : 0).append("-");
		stringBuffer.append(_deferAttr != null ? _deferAttr.hashCode() : 0).append("-");
		stringBuffer.append(_xmlSpaceAttr != null ? _xmlSpaceAttr.hashCode() : 0).append("-");
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

		public org.safris.web.depiction.schema.xhtml.strict.Script.IdAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Script.IdAttr(this);
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

	public static class CharsetAttr extends org.safris.web.depiction.schema.xhtml.strict.ICharset<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "charset");

		public CharsetAttr(org.safris.web.depiction.schema.xhtml.strict.ICharset<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public CharsetAttr(java.lang.String value)
		{
			super(value);
		}

		protected CharsetAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Script.CharsetAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Script.CharsetAttr(this);
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

	public static class TypeAttr extends org.safris.web.depiction.schema.xhtml.strict.IContentType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "type");

		public TypeAttr(org.safris.web.depiction.schema.xhtml.strict.IContentType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public TypeAttr(java.lang.String value)
		{
			super(value);
		}

		protected TypeAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Script.TypeAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Script.TypeAttr(this);
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

	public static class SrcAttr extends org.safris.web.depiction.schema.xhtml.strict.IURI<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "src");

		public SrcAttr(org.safris.web.depiction.schema.xhtml.strict.IURI<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public SrcAttr(java.lang.String value)
		{
			super(value);
		}

		protected SrcAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Script.SrcAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Script.SrcAttr(this);
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

	public static class DeferAttr extends org.safris.xml.bind.runtime.types.TokenType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "defer");

		protected DeferAttr(org.safris.xml.bind.runtime.types.TokenType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		protected DeferAttr(java.lang.String value)
		{
			super(value);
		}

		protected static class RESTRICTION
		{
			protected static RESTRICTION parseRESTRICTION(java.lang.String value)
			{
				return singletons.get(value);
			}

			protected final static java.util.Map<java.lang.String,RESTRICTION> singletons = new java.util.HashMap<java.lang.String,RESTRICTION>();
			protected final java.lang.String value;

			protected RESTRICTION(java.lang.String value)
			{
				this.value = value;
				singletons.put(this.value, this);
			}

			public java.lang.String getValue()
			{
				return value;
			}
		}

		public static final RESTRICTION DEFER = new RESTRICTION("defer");

		public DeferAttr(RESTRICTION restriction)
		{
			super(restriction.value);
		}

		protected DeferAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Script.DeferAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Script.DeferAttr(this);
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