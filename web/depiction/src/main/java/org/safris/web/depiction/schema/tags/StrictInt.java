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

public class StrictInt extends org.safris.web.depiction.schema.xhtml.tags.ITaglib<org.safris.xml.bind.runtime.lang.ComplexType>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "strictInt");

	static
	{
		getElementBindings().put(NAME, org.safris.web.depiction.schema.tags.StrictInt.class.getName());
	}

	private org.safris.web.depiction.schema.tags.StrictInt.ValueAttr _valueAttr = null;
	private org.safris.web.depiction.schema.tags.StrictInt.MinAttr _minAttr = null;
	private org.safris.web.depiction.schema.tags.StrictInt.MaxAttr _maxAttr = null;
	private org.safris.web.depiction.schema.tags.StrictInt.VarAttr _varAttr = null;

	public StrictInt(org.safris.web.depiction.schema.xhtml.tags.ITaglib<org.safris.xml.bind.runtime.lang.ComplexType> binding)
	{
		super(binding);
		if(binding instanceof org.safris.web.depiction.schema.tags.StrictInt)
		{
			org.safris.web.depiction.schema.tags.StrictInt element = (org.safris.web.depiction.schema.tags.StrictInt)binding;
			this._valueAttr = element._valueAttr != null ? element._valueAttr.clone() : null;
			this._minAttr = element._minAttr != null ? element._minAttr.clone() : null;
			this._maxAttr = element._maxAttr != null ? element._maxAttr.clone() : null;
			this._varAttr = element._varAttr != null ? element._varAttr.clone() : null;
		}
	}

	public StrictInt()
	{
		super();
	}

	public void setValueAttr(org.safris.web.depiction.schema.tags.StrictInt.ValueAttr _valueAttr)
	{
		this._valueAttr = _valueAttr;
	}

	public org.safris.web.depiction.schema.tags.StrictInt.ValueAttr getValueAttr()
	{
		return _valueAttr;
	}

	public void setMinAttr(org.safris.web.depiction.schema.tags.StrictInt.MinAttr _minAttr)
	{
		this._minAttr = _minAttr;
	}

	public org.safris.web.depiction.schema.tags.StrictInt.MinAttr getMinAttr()
	{
		return _minAttr;
	}

	public void setMaxAttr(org.safris.web.depiction.schema.tags.StrictInt.MaxAttr _maxAttr)
	{
		this._maxAttr = _maxAttr;
	}

	public org.safris.web.depiction.schema.tags.StrictInt.MaxAttr getMaxAttr()
	{
		return _maxAttr;
	}

	public void setVarAttr(org.safris.web.depiction.schema.tags.StrictInt.VarAttr _varAttr)
	{
		this._varAttr = _varAttr;
	}

	public org.safris.web.depiction.schema.tags.StrictInt.VarAttr getVarAttr()
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
		if(_valueAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_valueAttr, "value", element));
		}

		if(_minAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_minAttr, "min", element));
		}

		if(_maxAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_maxAttr, "max", element));
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
			else if("value".equals(attribute.getLocalName()))
			{
				this._valueAttr = (org.safris.web.depiction.schema.tags.StrictInt.ValueAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.StrictInt.ValueAttr.class, element, attribute);
			}
			else if("min".equals(attribute.getLocalName()))
			{
				this._minAttr = (org.safris.web.depiction.schema.tags.StrictInt.MinAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.StrictInt.MinAttr.class, element, attribute);
			}
			else if("max".equals(attribute.getLocalName()))
			{
				this._maxAttr = (org.safris.web.depiction.schema.tags.StrictInt.MaxAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.StrictInt.MaxAttr.class, element, attribute);
			}
			else if("var".equals(attribute.getLocalName()))
			{
				this._varAttr = (org.safris.web.depiction.schema.tags.StrictInt.VarAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.StrictInt.VarAttr.class, element, attribute);
			}
		}

		super.parse(element);
	}

	public org.safris.web.depiction.schema.tags.StrictInt clone()
	{
		return new org.safris.web.depiction.schema.tags.StrictInt(this);
	}

	public boolean equals(java.lang.Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof org.safris.web.depiction.schema.tags.StrictInt))
			return false;

		org.safris.web.depiction.schema.tags.StrictInt binding = (org.safris.web.depiction.schema.tags.StrictInt)obj;
		if((this._valueAttr == null && binding._valueAttr != null) || (this._valueAttr != null && !this._valueAttr.equals(binding._valueAttr)))
			return false;

		if((this._minAttr == null && binding._minAttr != null) || (this._minAttr != null && !this._minAttr.equals(binding._minAttr)))
			return false;

		if((this._maxAttr == null && binding._maxAttr != null) || (this._maxAttr != null && !this._maxAttr.equals(binding._maxAttr)))
			return false;

		if((this._varAttr == null && binding._varAttr != null) || (this._varAttr != null && !this._varAttr.equals(binding._varAttr)))
			return false;

		return super.equals(obj);
	}

	public int hashCode()
	{
		java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
		stringBuffer.append(_valueAttr != null ? _valueAttr.hashCode() : 0).append("-");
		stringBuffer.append(_minAttr != null ? _minAttr.hashCode() : 0).append("-");
		stringBuffer.append(_maxAttr != null ? _maxAttr.hashCode() : 0).append("-");
		stringBuffer.append(_varAttr != null ? _varAttr.hashCode() : 0).append("-");
		return stringBuffer.toString().hashCode();
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class ValueAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "value");

		public ValueAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public ValueAttr(java.lang.String value)
		{
			super(value);
		}

		protected ValueAttr()
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

		public org.safris.web.depiction.schema.tags.StrictInt.ValueAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.StrictInt.ValueAttr(this);
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

	public static class MinAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "min");

		public MinAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public MinAttr(java.lang.String value)
		{
			super(value);
		}

		protected MinAttr()
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

		public org.safris.web.depiction.schema.tags.StrictInt.MinAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.StrictInt.MinAttr(this);
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

	public static class MaxAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "max");

		public MaxAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public MaxAttr(java.lang.String value)
		{
			super(value);
		}

		protected MaxAttr()
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

		public org.safris.web.depiction.schema.tags.StrictInt.MaxAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.StrictInt.MaxAttr(this);
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

		public org.safris.web.depiction.schema.tags.StrictInt.VarAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.StrictInt.VarAttr(this);
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