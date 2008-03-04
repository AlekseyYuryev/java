package org.safris.xml.generator.compiler.lang;

import java.lang.reflect.Method;
import org.safris.xml.generator.processor.BindingQName;

public class NativeBinding
{
	private final BindingQName name;
	private final GenericClass baseClass;
	private final GenericClass nativeClass;
	private final Method factoryMethod;

	public NativeBinding(BindingQName name, GenericClass baseClass, GenericClass nativeClass, Method factoryMethod)
	{
		if(name == null)
			throw new NullPointerException("name == null");

		if(baseClass == null)
			throw new NullPointerException("baseClass == null");

		this.name = name;
		this.baseClass = baseClass;
		this.nativeClass = nativeClass;
		this.factoryMethod = factoryMethod;
	}

	public NativeBinding(BindingQName name, GenericClass baseClass, GenericClass nativeClass)
	{
		this(name, baseClass, nativeClass, null);
	}

	public NativeBinding(BindingQName name, GenericClass baseClass)
	{
		this(name, baseClass, null, null);
	}

	public BindingQName getName()
	{
		return name;
	}

	public GenericClass getBaseClass()
	{
		return baseClass;
	}

	public GenericClass getNativeClass()
	{
		return nativeClass;
	}

	public Method getFactoryMethod()
	{
		return factoryMethod;
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(!(obj instanceof NativeBinding))
			return false;

		final NativeBinding nativeBinding = (NativeBinding)obj;
		return name.equals(nativeBinding.name) && baseClass.equals(nativeBinding.baseClass) && nativeClass.equals(nativeBinding.nativeClass);
	}

	public int hashCode()
	{
		return toString().hashCode();
	}

	public String toString()
	{
		return name.toString() + "\n" + baseClass.toString() + "\n" + nativeClass.toString();
	}

	public static class GenericClass
	{
		private final Class cls;
		private final Class type;

		public GenericClass(Class cls, Class type)
		{
			if(cls == null)
				throw new NullPointerException("cls == null");

			this.cls = cls;
			this.type = type;
		}

		public GenericClass(Class cls)
		{
			this(cls, null);
		}

		public Class getCls()
		{
			return cls;
		}

		public Class getType()
		{
			return type;
		}

		public boolean equals(Object obj)
		{
			if(obj == this)
				return true;

			if(!(obj instanceof GenericClass))
				return false;

			final GenericClass genericClass = (GenericClass)obj;
			return cls.equals(genericClass.cls) && (type == null && genericClass.type == null || type != null && type.equals(genericClass.type));
		}

		public int hashCode()
		{
			return toString().hashCode();
		}

		public String toString()
		{
			if(type != null)
				return cls.getName() + "<" + type.getName() + ">";

			return cls.getName();
		}
	}
}
