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
 * The border attribute sets the thickness of the frame around the table. The
 * default units are screen pixels.
 * 
 *  The frame attribute specifies which parts of the frame around the table should
 * be rendered. The values are not the same as CALS to avoid a name clash with
 * the valign attribute.
 *
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
 */

public abstract class ITFrame<T extends org.safris.xml.bind.runtime.lang.BindingType> extends org.safris.xml.bind.runtime.types.TokenType<T>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "TFrame");

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

	public static final RESTRICTION VOID = new RESTRICTION("void");
	public static final RESTRICTION ABOVE = new RESTRICTION("above");
	public static final RESTRICTION BELOW = new RESTRICTION("below");
	public static final RESTRICTION HSIDES = new RESTRICTION("hsides");
	public static final RESTRICTION LHS = new RESTRICTION("lhs");
	public static final RESTRICTION RHS = new RESTRICTION("rhs");
	public static final RESTRICTION VSIDES = new RESTRICTION("vsides");
	public static final RESTRICTION BOX = new RESTRICTION("box");
	public static final RESTRICTION BORDER = new RESTRICTION("border");

	public ITFrame(RESTRICTION restriction)
	{
		super(restriction.value);
	}

	protected static ITFrame newInstance(final javax.xml.namespace.QName name)
	{
		return new ITFrame()
		{
			protected javax.xml.namespace.QName _getName()
			{
				return name;
			}
		};
	}

	static
	{
		getTypeBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.ITFrame.class.getName());
	}

	protected ITFrame(org.safris.xml.bind.runtime.types.TokenType binding)
	{
		super(binding);
	}

	protected ITFrame(java.lang.String value)
	{
		super(value);
	}

	protected ITFrame()
	{
		super();
	}

	public void setRESTRICTION(RESTRICTION restriction)
	{
		super.setTEXT(restriction.getValue());
	}

	public RESTRICTION getRESTRICTION()
	{
		return RESTRICTION.parseRESTRICTION(super.getTEXT());
	}

	protected void setTEXT(java.lang.String text)
	{
		super.setTEXT(text);
	}

	protected java.lang.String getTEXT()
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

	public org.safris.web.depiction.schema.xhtml.strict.ITFrame clone()
	{
		return new org.safris.web.depiction.schema.xhtml.strict.ITFrame(this)
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