package org.w3.x2001.xmlschema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.safris.xml.generator.compiler.runtime.Attribute;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.ComplexType;

public abstract class IXSAnyType<T extends ComplexType> extends IXSAnySimpleType<T>
{
	private final List<Binding<? extends BindingType>> anys = new ArrayList<Binding<? extends BindingType>>(7);
	private final List<Binding<? extends Attribute>> anyAttrs = new ArrayList<Binding<? extends Attribute>>(7);

	public IXSAnyType(IXSAnyType<T> binding)
	{
		super(binding);
	}

	public IXSAnyType(Object text)
	{
		super(text);
	}

	protected IXSAnyType(String text)
	{
		super();
	}

	protected IXSAnyType()
	{
		super();
	}

	public void addANYATTR(Binding<? extends Attribute> anyAttribute)
	{
		this.anyAttrs.add(anyAttribute);
	}

	public List<Binding<? extends Attribute>> getANYATTR()
	{
		return anyAttrs;
	}

	public void addANY(Binding<? extends BindingType> any)
	{
		this.anys.add(any);
	}

	public List<Binding<? extends BindingType>> getANY()
	{
		return anys;
	}

	public IXSAnyType clone()
	{
		return new IXSAnyType(this)
		{
			protected QName _getName()
			{
				return this._getName();
			}

			protected IXSAnyType inherits()
			{
				return this;
			}
		};
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof IXSAnyType))
			return false;

		final IXSAnyType that = (IXSAnyType)obj;
		if(anys != null)
		{
			if(that.anys != null && anys.size() == that.anys.size())
			{
				for(int i = 0; i < anys.size(); i++)
					if(!anys.get(i).equals(that.anys.get(i)))
						return false;
			}
			else
				return false;
		}
		else if(that.anys != null)
			return false;

		if(anyAttrs != null)
		{
			if(that.anyAttrs != null && anyAttrs.size() == that.anyAttrs.size())
			{
				for(int i = 0; i < anyAttrs.size(); i++)
					if(!anyAttrs.get(i).equals(that.anyAttrs.get(i)))
						return false;
			}
			else
				return false;
		}
		else if(that.anyAttrs != null)
			return false;

		return true;
	}
}
