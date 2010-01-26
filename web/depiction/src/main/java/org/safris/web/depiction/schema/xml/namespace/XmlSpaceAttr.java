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

package org.safris.web.depiction.schema.xml.namespace;

/**
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
*/

public class XmlSpaceAttr extends org.safris.xml.bind.runtime.types.NCNameType<org.safris.xml.bind.runtime.lang.Attribute>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/XML/1998/namespace", "space");

	protected XmlSpaceAttr(java.lang.String value)
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

	public static final RESTRICTION DEFAULT = new RESTRICTION("default");
	public static final RESTRICTION PRESERVE = new RESTRICTION("preserve");

	public XmlSpaceAttr(RESTRICTION restriction)
	{
		super(restriction.value);
	}

	protected XmlSpaceAttr(org.safris.xml.bind.runtime.types.NCNameType binding)
	{
		super(binding);
	}

	protected XmlSpaceAttr()
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

	public org.safris.web.depiction.schema.xml.namespace.XmlSpaceAttr clone()
	{
		return new org.safris.web.depiction.schema.xml.namespace.XmlSpaceAttr(this);
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