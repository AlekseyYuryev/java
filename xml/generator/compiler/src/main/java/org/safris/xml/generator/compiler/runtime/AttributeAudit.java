package org.safris.xml.generator.compiler.runtime;

import java.util.Collection;
import javax.xml.namespace.QName;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.w3c.dom.Element;

public class AttributeAudit<T>
{
	private final T _default;
	private final QName name;
	private final boolean qualified;
	private final boolean required;
	private T value = null;
	
	public AttributeAudit(T _default, QName name, boolean qualified, boolean required)
	{
		this._default = _default;
		this.name = name;
		this.qualified = qualified;
		this.required = required;
	}
	
	public T getDefault()
	{
		return _default;
	}
	
	public QName getName()
	{
		return name;
	}
	
	public boolean isQualified()
	{
		return qualified;
	}
	
	public boolean isRequired()
	{
		return required;
	}
	
	public void setValue(T value)
	{
		this.value = value;
	}
	
	public T getValue()
	{
		return value;
	}
	
	public void marshal(Element parent) throws MarshalException
	{
		Object value = getValue();
		if(value == null)
		{
			if(getDefault() == null)
				return;
			
			if(!isRequired())
				return;
			
			value = getDefault();
		}
		
		if(value instanceof Collection)
		{
			String name = null;
			if(getName() != null)
			{
				if(isQualified())
					name = Binding._getPrefix(parent, getName()) + ":" + getName().getLocalPart();
				else
					name = getName().getLocalPart();
			}
			
			for(Object object : (Collection)value)
			{
				Binding binding = (Binding)object;
				if(name == null)
				{
					QName actualName = Binding._getName(binding);
					if(isQualified())
						name = Binding._getPrefix(parent, getName()) + ":" + getName().getLocalPart();
					else
						name = actualName.getLocalPart();
				}
				
				parent.setAttributeNodeNS(binding.marshalAttr(name, parent));
			}
		}
		else
		{
			QName name = getName();
			if(name == null)
				name = ((Binding)value)._getName();
			
			String marshalName = null;
			if(isQualified())
				marshalName = Binding._getPrefix(parent, name) + ":" + name.getLocalPart();
			else
				marshalName = name.getLocalPart();
			
			parent.setAttributeNodeNS(((Binding)value).marshalAttr(marshalName, parent));
		}
	}
	
	public boolean equals(Object obj)
	{
		if(obj == null)
		{
			if(value == null)
				return true;
			
			return false;
		}
		
		return obj.equals(value);
	}
	
	public int hashCode()
	{
		if(value == null)
			return 0;
		
		return value.hashCode();
	}
	
	public String toString()
	{
		if(value == null)
			return super.toString();
		
		return value.toString();
	}
}
