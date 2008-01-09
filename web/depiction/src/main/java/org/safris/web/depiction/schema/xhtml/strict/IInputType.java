package org.safris.web.depiction.schema.xhtml.strict;

/**
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
*/

public abstract class IInputType<T extends org.safris.xml.bind.runtime.lang.BindingType> extends org.safris.xml.bind.runtime.types.TokenType<T>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "InputType");

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

	public static final RESTRICTION TEXT = new RESTRICTION("text");
	public static final RESTRICTION PASSWORD = new RESTRICTION("password");
	public static final RESTRICTION CHECKBOX = new RESTRICTION("checkbox");
	public static final RESTRICTION RADIO = new RESTRICTION("radio");
	public static final RESTRICTION SUBMIT = new RESTRICTION("submit");
	public static final RESTRICTION RESET = new RESTRICTION("reset");
	public static final RESTRICTION FILE = new RESTRICTION("file");
	public static final RESTRICTION HIDDEN = new RESTRICTION("hidden");
	public static final RESTRICTION IMAGE = new RESTRICTION("image");
	public static final RESTRICTION BUTTON = new RESTRICTION("button");

	public IInputType(RESTRICTION restriction)
	{
		super(restriction.value);
	}

	protected static IInputType newInstance(final javax.xml.namespace.QName name)
	{
		return new IInputType()
		{
			protected javax.xml.namespace.QName _getName()
			{
				return name;
			}
		};
	}

	static
	{
		getTypeBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.IInputType.class.getName());
	}

	protected IInputType(org.safris.xml.bind.runtime.types.TokenType binding)
	{
		super(binding);
	}

	protected IInputType(java.lang.String value)
	{
		super(value);
	}

	protected IInputType()
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

	public org.safris.web.depiction.schema.xhtml.strict.IInputType clone()
	{
		return new org.safris.web.depiction.schema.xhtml.strict.IInputType(this)
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