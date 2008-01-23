package org.safris.xml.generator.compiler.runtime;

import java.util.HashMap;
import java.util.Map;

public abstract class NotationType
{
	private static final Map<String,NotationType> notations = new HashMap<String,NotationType>(7);

	protected static void _registerNotation(NotationType notation)
	{
		notations.put(notation.getName(), notation);
	}

	public static NotationType parseNotation(String name)
	{
		return notations.get(name);
	}

	protected NotationType()
	{
		notations.put(getName(), this);
	}

	protected abstract String getName();
	protected abstract String getPublic();
	protected abstract String getSystem();
}
