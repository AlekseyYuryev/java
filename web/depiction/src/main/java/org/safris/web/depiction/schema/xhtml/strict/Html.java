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

package org.safris.web.depiction.schema.xhtml.strict;

/**
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
*/

public class Html extends org.safris.xml.bind.runtime.lang.Binding<org.safris.xml.bind.runtime.lang.ComplexType>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "html");

	static
	{
		getElementBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.Html.class.getName());
	}

	private org.safris.web.depiction.schema.xhtml.strict.Html.IdAttr _idAttr = null;
	private LangAttr _langAttr = null;
	private org.safris.web.depiction.schema.xml.namespace.XmlLangAttr _xmlLangAttr = null;
	private DirAttr _dirAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Head _head = null;
	private org.safris.web.depiction.schema.xhtml.strict.IBlock _body = null;

	public Html(org.safris.web.depiction.schema.xhtml.strict.Html binding)
	{
		super(binding);
		if(binding instanceof org.safris.web.depiction.schema.xhtml.strict.Html)
		{
			org.safris.web.depiction.schema.xhtml.strict.Html element = (org.safris.web.depiction.schema.xhtml.strict.Html)binding;
			this._idAttr = element._idAttr != null ? element._idAttr.clone() : null;
			this._langAttr = element._langAttr != null ? element._langAttr.clone() : null;
			this._xmlLangAttr = element._xmlLangAttr != null ? element._xmlLangAttr.clone() : null;
			this._dirAttr = element._dirAttr != null ? element._dirAttr.clone() : null;
			this._head = element._head != null ? element._head.clone() : null;
			this._body = element._body != null ? element._body.clone() : null;
		}
	}

	public Html()
	{
		super();
	}

	public void setIdAttr(org.safris.web.depiction.schema.xhtml.strict.Html.IdAttr _idAttr)
	{
		this._idAttr = _idAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Html.IdAttr getIdAttr()
	{
		return _idAttr;
	}

	public void setLangAttr(LangAttr _langAttr)
	{
		this._langAttr = _langAttr;
	}

	public LangAttr getLangAttr()
	{
		return _langAttr;
	}

	public void setXmlLangAttr(org.safris.web.depiction.schema.xml.namespace.XmlLangAttr _xmlLangAttr)
	{
		this._xmlLangAttr = _xmlLangAttr;
	}

	public org.safris.web.depiction.schema.xml.namespace.XmlLangAttr getXmlLangAttr()
	{
		return _xmlLangAttr;
	}

	public void setDirAttr(DirAttr _dirAttr)
	{
		this._dirAttr = _dirAttr;
	}

	public DirAttr getDirAttr()
	{
		return _dirAttr;
	}

	public void setHead(org.safris.web.depiction.schema.xhtml.strict.Head _head)
	{
		this._head = _head;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Head getHead()
	{
		return _head;
	}

	public void setBody(org.safris.web.depiction.schema.xhtml.strict.IBlock _body)
	{
		this._body = _body;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IBlock getBody()
	{
		return _body;
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

		if(_langAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_langAttr, "lang", element));
		}

		if(_xmlLangAttr != null)
		{
			java.lang.String prefix = _getPrefix(element, org.safris.xml.bind.runtime.lang.Binding._getName(_xmlLangAttr).getNamespaceURI());
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_xmlLangAttr, prefix + ":" + org.safris.xml.bind.runtime.lang.Binding._getName(_xmlLangAttr).getLocalPart(), element));
		}

		if(_dirAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_dirAttr, "dir", element));
		}

		if(_head != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _head, _getName(_head), _getTypeName(_head)));
		}

		if(_body != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _body, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "body"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Block")));
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
				this._idAttr = (org.safris.web.depiction.schema.xhtml.strict.Html.IdAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Html.IdAttr.class, element, attribute);
			}
			else if("lang".equals(attribute.getLocalName()))
			{
				this._langAttr = (LangAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(LangAttr.class, element, attribute);
			}
			else if("http://www.w3.org/XML/1998/namespace".equals(attribute.getNamespaceURI()) && "lang".equals(attribute.getLocalName()))
			{
				this._xmlLangAttr = (org.safris.web.depiction.schema.xml.namespace.XmlLangAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xml.namespace.XmlLangAttr.class, element, attribute);
			}
			else if("dir".equals(attribute.getLocalName()))
			{
				this._dirAttr = (DirAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(DirAttr.class, element, attribute);
			}
		}

		org.w3c.dom.NodeList nodeList = element.getChildNodes();
		org.w3c.dom.Node childNode = null;
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			childNode = nodeList.item(i);
			if(childNode.getLocalName() == null || childNode.getNodeType() == org.w3c.dom.Node.TEXT_NODE)
			{
				continue;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "head".equals(childNode.getLocalName()))
			{
				this._head = (org.safris.web.depiction.schema.xhtml.strict.Head)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Head.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "head"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "body".equals(childNode.getLocalName()))
			{
				this._body = (org.safris.web.depiction.schema.xhtml.strict.IBlock)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Body.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "body"));
				element.removeChild(childNode);
				i--;
			}
		}

		super.parse(element);
	}

	public org.safris.web.depiction.schema.xhtml.strict.Html clone()
	{
		return new org.safris.web.depiction.schema.xhtml.strict.Html(this);
	}

	public boolean equals(java.lang.Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof org.safris.web.depiction.schema.xhtml.strict.Html))
			return false;

		org.safris.web.depiction.schema.xhtml.strict.Html binding = (org.safris.web.depiction.schema.xhtml.strict.Html)obj;
		if((this._idAttr == null && binding._idAttr != null) || (this._idAttr != null && !this._idAttr.equals(binding._idAttr)))
			return false;

		if((this._langAttr == null && binding._langAttr != null) || (this._langAttr != null && !this._langAttr.equals(binding._langAttr)))
			return false;

		if((this._xmlLangAttr == null && binding._xmlLangAttr != null) || (this._xmlLangAttr != null && !this._xmlLangAttr.equals(binding._xmlLangAttr)))
			return false;

		if((this._dirAttr == null && binding._dirAttr != null) || (this._dirAttr != null && !this._dirAttr.equals(binding._dirAttr)))
			return false;

		if((this._head == null && binding._head != null) || (this._head != null && !this._head.equals(binding._head)))
			return false;

		if((this._body == null && binding._body != null) || (this._body != null && !this._body.equals(binding._body)))
			return false;

		return super.equals(obj);
	}

	public int hashCode()
	{
		java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
		stringBuffer.append(_idAttr != null ? _idAttr.hashCode() : 0).append("-");
		stringBuffer.append(_langAttr != null ? _langAttr.hashCode() : 0).append("-");
		stringBuffer.append(_xmlLangAttr != null ? _xmlLangAttr.hashCode() : 0).append("-");
		stringBuffer.append(_dirAttr != null ? _dirAttr.hashCode() : 0).append("-");
		stringBuffer.append(_head != null ? _head.hashCode() : 0).append("-");
		stringBuffer.append(_body != null ? _body.hashCode() : 0).append("-");
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

		public org.safris.web.depiction.schema.xhtml.strict.Html.IdAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Html.IdAttr(this);
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

	public static class LangAttr extends org.safris.web.depiction.schema.xhtml.strict.ILanguageCode<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "lang");

		public LangAttr(org.safris.web.depiction.schema.xhtml.strict.ILanguageCode<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public LangAttr(org.safris.xml.bind.runtime.types.lang.Language value)
		{
			super(value);
		}

		protected LangAttr(java.lang.String value)
		{
			super(value);
		}

		protected LangAttr()
		{
			super();
		}

		public org.safris.xml.bind.runtime.types.lang.Language getValue()
		{
			return super.getTEXT();
		}

		public void setValue(org.safris.xml.bind.runtime.types.lang.Language value)
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

		public LangAttr clone()
		{
			return new LangAttr(this);
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

	public static class DirAttr extends org.safris.xml.bind.runtime.types.TokenType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "dir");

		protected DirAttr(org.safris.xml.bind.runtime.types.TokenType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		protected DirAttr(java.lang.String value)
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

		public static final RESTRICTION LTR = new RESTRICTION("ltr");
		public static final RESTRICTION RTL = new RESTRICTION("rtl");

		public DirAttr(RESTRICTION restriction)
		{
			super(restriction.value);
		}

		protected DirAttr()
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

		public DirAttr clone()
		{
			return new DirAttr(this);
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