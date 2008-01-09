package org.safris.web.depiction.schema.xhtml.strict;

/**
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
*/

public abstract class IShape<T extends org.safris.xml.bind.runtime.lang.BindingType> extends org.safris.xml.bind.runtime.types.TokenType<T>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Shape");

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

	public static final RESTRICTION RECT = new RESTRICTION("rect");
	public static final RESTRICTION CIRCLE = new RESTRICTION("circle");
	public static final RESTRICTION POLY = new RESTRICTION("poly");
	public static final RESTRICTION DEFAULT = new RESTRICTION("default");

	public IShape(RESTRICTION restriction)
	{
		super(restriction.value);
	}

	protected static IShape newInstance(final javax.xml.namespace.QName name)
	{
		return new IShape()
		{
			protected javax.xml.namespace.QName _getName()
			{
				return name;
			}
		};
	}

	static
	{
		getTypeBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.IShape.class.getName());
	}

	protected IShape(org.safris.xml.bind.runtime.types.TokenType binding)
	{
		super(binding);
	}

	protected IShape(java.lang.String value)
	{
		super(value);
	}

	protected IShape()
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

	public org.safris.web.depiction.schema.xhtml.strict.IShape clone()
	{
		return new org.safris.web.depiction.schema.xhtml.strict.IShape(this)
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