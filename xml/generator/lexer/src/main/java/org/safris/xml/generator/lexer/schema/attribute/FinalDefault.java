package org.safris.xml.generator.lexer.schema.attribute;

import java.util.HashMap;
import java.util.Map;

public final class FinalDefault
{
	private static final Map<String,FinalDefault> enums = new HashMap<String,FinalDefault>();

	public static final FinalDefault ALL = new FinalDefault("#all");
	public static final FinalDefault EXTENSION = new FinalDefault("extension");
	public static final FinalDefault RESTRICTION = new FinalDefault("restriction");
	public static final FinalDefault SUBSTITUTION = new FinalDefault("substitution");

	public static FinalDefault parseFinalDefault(String value)
	{
		return enums.get(value);
	}

	private String value;

	private FinalDefault(String value)
	{
		this.value = value;
		enums.put(value, this);
	}

	public String getValue()
	{
		return value;
	}

	public boolean equals(Object obj)
	{
		if(!(obj instanceof FinalDefault))
			return false;

		return getValue().equals(((FinalDefault)obj).getValue());
	}

	public int hashCode()
	{
		return (getClass().getName() + toString()).hashCode();
	}

	public String toString()
	{
		return getValue();
	}
}
