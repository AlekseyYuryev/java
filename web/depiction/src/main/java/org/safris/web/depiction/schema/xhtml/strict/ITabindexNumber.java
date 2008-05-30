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
 * tabindex attribute specifies the position of the current element in the tabbing
 * order for the current document. This value must be a number between 0 and
 * 32767. User agents should ignore leading zeros.
 *
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
 */

public abstract class ITabindexNumber<T extends org.safris.xml.bind.runtime.lang.BindingType> extends org.safris.web.depiction.schema.xhtml.strict.INumber<T>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "tabindexNumber");

	protected static ITabindexNumber newInstance(final javax.xml.namespace.QName name)
	{
		return new ITabindexNumber()
		{
			protected javax.xml.namespace.QName _getName()
			{
				return name;
			}
		};
	}

	static
	{
		getTypeBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.ITabindexNumber.class.getName());
	}

	protected ITabindexNumber(org.safris.web.depiction.schema.xhtml.strict.INumber binding)
	{
		super(binding);
	}

	protected ITabindexNumber(java.lang.Integer value)
	{
		super(value);
	}

	protected ITabindexNumber(java.lang.String value)
	{
		super(value);
	}

	protected ITabindexNumber()
	{
		super();
	}

	public void setTEXT(java.lang.Integer text)
	{
		super.setTEXT(text);
	}

	public java.lang.Integer getTEXT()
	{
		return super.getTEXT();
	}

	protected javax.xml.namespace.QName _getTypeName()
	{
		return NAME;
	}

	protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
	{
		return super.marshalAttr(name, parent);
	}

	protected org.w3c.dom.Element marshal() throws org.safris.xml.bind.runtime.lang.MarshalException, org.safris.xml.bind.runtime.lang.ValidationException
	{
		org.w3c.dom.Element root = createElementNS(_getName().getNamespaceURI(), _getName().getLocalPart());
		return marshal(root, _getName(), _getTypeName());
	}

	protected org.w3c.dom.Element marshal(org.w3c.dom.Element parent, javax.xml.namespace.QName name, javax.xml.namespace.QName typeName) throws org.safris.xml.bind.runtime.lang.MarshalException
	{
		return super.marshal(parent, name, typeName);
	}

	public org.safris.web.depiction.schema.xhtml.strict.ITabindexNumber clone()
	{
		return new org.safris.web.depiction.schema.xhtml.strict.ITabindexNumber(this)
		{
			protected javax.xml.namespace.QName _getName()
			{
				return _getName();
			}
		};
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