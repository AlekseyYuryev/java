package org.w3.x2001.xmlschema;

import javax.xml.namespace.QName;
import org.safris.commons.xml.validator.ValidationException;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public abstract class $xs_anySimpleType<T extends BindingType> extends Binding<T>
{
	private Object text = null;

	private $xs_anySimpleType($xs_anySimpleType<T> binding)
	{
		super(binding);
		this.text = binding.text;
	}

	public $xs_anySimpleType(Object text)
	{
		super();
		this.text = text;
	}

	protected $xs_anySimpleType(String text)
	{
		super();
		this.text = text;
	}

	protected $xs_anySimpleType()
	{
		super();
	}

	protected void setText(Object text)
	{
		this.text = text;
	}

	protected Object getText()
	{
		return text;
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		this.text = value;
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	private transient Element parent = null;

	protected Element marshal(Element parent, QName name, QName typeName) throws MarshalException
	{
		this.parent = parent;
		final Element element = super.marshal(parent, name, typeName);
		if(text != null)
		{
			final Document document = parent.getOwnerDocument();
			element.appendChild(document.createTextNode(String.valueOf(_$$encode(parent))));
		}

		return element;
	}

	protected Attr marshalAttr(String name, Element parent) throws MarshalException
	{
		this.parent = parent;
		final Attr attr = parent.getOwnerDocument().createAttribute(name);
		attr.setNodeValue(String.valueOf(_$$encode(parent)));
		return attr;
	}

	protected void parseText(Text text) throws ParseException, ValidationException
	{
		// Ignore all attributes that have a xsi prefix because these are
		// controlled implicitly by the framework
		if(XSI_NIL.getPrefix().equals(text.getPrefix()))
			return;

		final StringBuffer value = new StringBuffer("");
		if(text.getNodeValue() != null)
			value.append(text.getNodeValue().trim());

		if(value.length() == 0)
			return;

		_$$decode((Element)text.getParentNode(), value.toString());
	}

	protected QName _$$getName()
	{
		return _$$getName(inherits());
	}

	protected QName _$$getTypeName()
	{
		return null;
	}

	public $xs_anySimpleType clone()
	{
		return new $xs_anySimpleType(this)
		{
			protected $xs_anySimpleType inherits()
			{
				return this;
			}
		};
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof $xs_anySimpleType))
			return false;

		final $xs_anySimpleType that = ($xs_anySimpleType)obj;
		try
		{
			final String thisEncoded = _$$encode(parent);
			final String thatEncoded = that._$$encode(parent);
			return thisEncoded != null ? thisEncoded.equals(thatEncoded) : thatEncoded == null;
		}
		catch(MarshalException e)
		{
			return false;
		}
	}

	public int hashCode()
	{
		final String value;
		try
		{
			value = _$$encode(parent);
		}
		catch(MarshalException e)
		{
			return super.hashCode();
		}

		if(value == null)
			return super.hashCode();

		return value.hashCode();
	}

	public String toString()
	{
		try
		{
			return String.valueOf(_$$encode(parent));
		}
		catch(MarshalException e)
		{
			return super.toString();
		}
	}
}
