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
 * The rules attribute defines which rules to draw between cells:
 * 
 *  If rules is absent then assume: "none" if border is absent or border="0"
 * otherwise "all"
 *
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
 */

public abstract class ITRules<T extends org.safris.xml.bind.runtime.lang.BindingType> extends org.safris.xml.bind.runtime.types.TokenType<T>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "TRules");

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

	public static final RESTRICTION NONE = new RESTRICTION("none");
	public static final RESTRICTION GROUPS = new RESTRICTION("groups");
	public static final RESTRICTION ROWS = new RESTRICTION("rows");
	public static final RESTRICTION COLS = new RESTRICTION("cols");
	public static final RESTRICTION ALL = new RESTRICTION("all");

	public ITRules(RESTRICTION restriction)
	{
		super(restriction.value);
	}

	protected static ITRules newInstance(final javax.xml.namespace.QName name)
	{
		return new ITRules()
		{
			protected javax.xml.namespace.QName _getName()
			{
				return name;
			}
		};
	}

	static
	{
		getTypeBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.ITRules.class.getName());
	}

	protected ITRules(org.safris.xml.bind.runtime.types.TokenType binding)
	{
		super(binding);
	}

	protected ITRules(java.lang.String value)
	{
		super(value);
	}

	protected ITRules()
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

	public org.safris.web.depiction.schema.xhtml.strict.ITRules clone()
	{
		return new org.safris.web.depiction.schema.xhtml.strict.ITRules(this)
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