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

public class Metadata extends org.safris.web.depiction.schema.xhtml.tags.ITaglib<org.safris.xml.bind.runtime.lang.ComplexType>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "metadata");

	static
	{
		getElementBindings().put(NAME, org.safris.web.depiction.schema.tags.Metadata.class.getName());
	}

	private org.safris.web.depiction.schema.tags.Metadata.FileAttr _fileAttr = null;
	private org.safris.web.depiction.schema.tags.Metadata.VarAttr _varAttr = null;

	public Metadata(org.safris.web.depiction.schema.xhtml.tags.ITaglib<org.safris.xml.bind.runtime.lang.ComplexType> binding)
	{
		super(binding);
		if(binding instanceof org.safris.web.depiction.schema.tags.Metadata)
		{
			org.safris.web.depiction.schema.tags.Metadata element = (org.safris.web.depiction.schema.tags.Metadata)binding;
			this._fileAttr = element._fileAttr != null ? element._fileAttr.clone() : null;
			this._varAttr = element._varAttr != null ? element._varAttr.clone() : null;
		}
	}

	public Metadata()
	{
		super();
	}

	public void setFileAttr(org.safris.web.depiction.schema.tags.Metadata.FileAttr _fileAttr)
	{
		this._fileAttr = _fileAttr;
	}

	public org.safris.web.depiction.schema.tags.Metadata.FileAttr getFileAttr()
	{
		return _fileAttr;
	}

	public void setVarAttr(org.safris.web.depiction.schema.tags.Metadata.VarAttr _varAttr)
	{
		this._varAttr = _varAttr;
	}

	public org.safris.web.depiction.schema.tags.Metadata.VarAttr getVarAttr()
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
		if(_fileAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_fileAttr, "file", element));
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
			else if("file".equals(attribute.getLocalName()))
			{
				this._fileAttr = (org.safris.web.depiction.schema.tags.Metadata.FileAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Metadata.FileAttr.class, element, attribute);
			}
			else if("var".equals(attribute.getLocalName()))
			{
				this._varAttr = (org.safris.web.depiction.schema.tags.Metadata.VarAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Metadata.VarAttr.class, element, attribute);
			}
		}

		super.parse(element);
	}

	public org.safris.web.depiction.schema.tags.Metadata clone()
	{
		return new org.safris.web.depiction.schema.tags.Metadata(this);
	}

	public boolean equals(java.lang.Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof org.safris.web.depiction.schema.tags.Metadata))
			return false;

		org.safris.web.depiction.schema.tags.Metadata binding = (org.safris.web.depiction.schema.tags.Metadata)obj;
		if((this._fileAttr == null && binding._fileAttr != null) || (this._fileAttr != null && !this._fileAttr.equals(binding._fileAttr)))
			return false;

		if((this._varAttr == null && binding._varAttr != null) || (this._varAttr != null && !this._varAttr.equals(binding._varAttr)))
			return false;

		return super.equals(obj);
	}

	public int hashCode()
	{
		java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
		stringBuffer.append(_fileAttr != null ? _fileAttr.hashCode() : 0).append("-");
		stringBuffer.append(_varAttr != null ? _varAttr.hashCode() : 0).append("-");
		return stringBuffer.toString().hashCode();
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class FileAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "file");

		public FileAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public FileAttr(java.lang.String value)
		{
			super(value);
		}

		protected FileAttr()
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

		public org.safris.web.depiction.schema.tags.Metadata.FileAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.Metadata.FileAttr(this);
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

		public org.safris.web.depiction.schema.tags.Metadata.VarAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.Metadata.VarAttr(this);
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