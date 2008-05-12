package org.safris.xml.generator.compiler.runtime;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;

public abstract class NotationType extends Binding<NotationType> implements BindingType
{
	private static final Map<String,NotationType> notations = new HashMap<String,NotationType>(7);

	protected static void _$$registerNotation(NotationType notation)
	{
		notations.put(notation.getName(), notation);
	}

	public static NotationType parseNotation(String name)
	{
		return notations.get(name);
	}

	private final QName _$$name;

	protected NotationType()
	{
		this._$$name = new QName(getName());
		notations.put(getName(), this);
	}

	protected Binding inherits()
	{
		return this;
	}

	protected abstract String getName();
	protected abstract String getPublic();
	protected abstract String getSystem();
}
