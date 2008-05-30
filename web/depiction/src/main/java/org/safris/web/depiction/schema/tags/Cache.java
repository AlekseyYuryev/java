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

package org.safris.web.depiction.schema.tags;

/**
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
*/

public class Cache extends org.safris.web.depiction.schema.xhtml.tags.ITaglib<org.safris.xml.bind.runtime.lang.ComplexType>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "cache");

	static
	{
		getElementBindings().put(NAME, org.safris.web.depiction.schema.tags.Cache.class.getName());
	}

	private org.safris.web.depiction.schema.tags.Cache.ScopeAttr _scopeAttr = null;
	private org.safris.web.depiction.schema.tags.Cache.NameAttr _nameAttr = null;
	private org.safris.web.depiction.schema.tags.Cache.KeyAttr _keyAttr = null;

	public Cache(org.safris.web.depiction.schema.xhtml.tags.ITaglib<org.safris.xml.bind.runtime.lang.ComplexType> binding)
	{
		super(binding);
		if(binding instanceof org.safris.web.depiction.schema.tags.Cache)
		{
			org.safris.web.depiction.schema.tags.Cache element = (org.safris.web.depiction.schema.tags.Cache)binding;
			this._scopeAttr = element._scopeAttr != null ? element._scopeAttr.clone() : null;
			this._nameAttr = element._nameAttr != null ? element._nameAttr.clone() : null;
			this._keyAttr = element._keyAttr != null ? element._keyAttr.clone() : null;
		}
	}

	public Cache()
	{
		super();
	}

	public void setScopeAttr(org.safris.web.depiction.schema.tags.Cache.ScopeAttr _scopeAttr)
	{
		this._scopeAttr = _scopeAttr;
	}

	public org.safris.web.depiction.schema.tags.Cache.ScopeAttr getScopeAttr()
	{
		return _scopeAttr;
	}

	public void setNameAttr(org.safris.web.depiction.schema.tags.Cache.NameAttr _nameAttr)
	{
		this._nameAttr = _nameAttr;
	}

	public org.safris.web.depiction.schema.tags.Cache.NameAttr getNameAttr()
	{
		return _nameAttr;
	}

	public void setKeyAttr(org.safris.web.depiction.schema.tags.Cache.KeyAttr _keyAttr)
	{
		this._keyAttr = _keyAttr;
	}

	public org.safris.web.depiction.schema.tags.Cache.KeyAttr getKeyAttr()
	{
		return _keyAttr;
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
		if(_scopeAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_scopeAttr, "scope", element));
		}

		if(_nameAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_nameAttr, "name", element));
		}

		if(_keyAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_keyAttr, "key", element));
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
			else if("scope".equals(attribute.getLocalName()))
			{
				this._scopeAttr = (org.safris.web.depiction.schema.tags.Cache.ScopeAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Cache.ScopeAttr.class, element, attribute);
			}
			else if("name".equals(attribute.getLocalName()))
			{
				this._nameAttr = (org.safris.web.depiction.schema.tags.Cache.NameAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Cache.NameAttr.class, element, attribute);
			}
			else if("key".equals(attribute.getLocalName()))
			{
				this._keyAttr = (org.safris.web.depiction.schema.tags.Cache.KeyAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Cache.KeyAttr.class, element, attribute);
			}
		}

		super.parse(element);
	}

	public org.safris.web.depiction.schema.tags.Cache clone()
	{
		return new org.safris.web.depiction.schema.tags.Cache(this);
	}

	public boolean equals(java.lang.Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof org.safris.web.depiction.schema.tags.Cache))
			return false;

		org.safris.web.depiction.schema.tags.Cache binding = (org.safris.web.depiction.schema.tags.Cache)obj;
		if((this._scopeAttr == null && binding._scopeAttr != null) || (this._scopeAttr != null && !this._scopeAttr.equals(binding._scopeAttr)))
			return false;

		if((this._nameAttr == null && binding._nameAttr != null) || (this._nameAttr != null && !this._nameAttr.equals(binding._nameAttr)))
			return false;

		if((this._keyAttr == null && binding._keyAttr != null) || (this._keyAttr != null && !this._keyAttr.equals(binding._keyAttr)))
			return false;

		return super.equals(obj);
	}

	public int hashCode()
	{
		java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
		stringBuffer.append(_scopeAttr != null ? _scopeAttr.hashCode() : 0).append("-");
		stringBuffer.append(_nameAttr != null ? _nameAttr.hashCode() : 0).append("-");
		stringBuffer.append(_keyAttr != null ? _keyAttr.hashCode() : 0).append("-");
		return stringBuffer.toString().hashCode();
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class ScopeAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "scope");

		public ScopeAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public ScopeAttr(java.lang.String value)
		{
			super(value);
		}

		protected ScopeAttr()
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

		public org.safris.web.depiction.schema.tags.Cache.ScopeAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.Cache.ScopeAttr(this);
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

		public org.safris.web.depiction.schema.tags.Cache.NameAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.Cache.NameAttr(this);
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

	public static class KeyAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "key");

		public KeyAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public KeyAttr(java.lang.String value)
		{
			super(value);
		}

		protected KeyAttr()
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

		public org.safris.web.depiction.schema.tags.Cache.KeyAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.Cache.KeyAttr(this);
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