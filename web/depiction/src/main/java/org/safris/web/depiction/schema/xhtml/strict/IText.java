package org.safris.web.depiction.schema.xhtml.strict;

/**
 * used for titles etc.
 *
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
 */

public abstract class IText<T extends org.safris.xml.bind.runtime.lang.BindingType> extends org.safris.xml.bind.runtime.types.StringType<T>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Text");

	protected static IText newInstance(final javax.xml.namespace.QName name)
	{
		return new IText()
		{
			protected javax.xml.namespace.QName _getName()
			{
				return name;
			}
		};
	}

	static
	{
		getTypeBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.IText.class.getName());
	}

	protected IText(org.safris.xml.bind.runtime.types.StringType binding)
	{
		super(binding);
	}

	protected IText(java.lang.String value)
	{
		super(value);
	}

	protected IText()
	{
		super();
	}

	public void setTEXT(java.lang.String text)
	{
		super.setTEXT(text);
	}

	public java.lang.String getTEXT()
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

	public org.safris.web.depiction.schema.xhtml.strict.IText clone()
	{
		return new org.safris.web.depiction.schema.xhtml.strict.IText(this)
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