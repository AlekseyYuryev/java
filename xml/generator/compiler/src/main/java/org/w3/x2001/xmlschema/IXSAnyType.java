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
}
