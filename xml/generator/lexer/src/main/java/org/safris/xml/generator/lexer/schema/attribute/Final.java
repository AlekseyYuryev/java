package org.safris.xml.generator.lexer.schema.attribute;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.schema.attribute.Final;

public final class Final
{
	private static final Map<String,Final> enums = new HashMap<String,Final>();
	
	public static final Final ALL = new Final("#all");
	public static final Final EXTENSION = new Final("extension");
	public static final Final RESTRICTION = new Final("restriction");
	public static final Final SUBSTITUTION = new Final("substitution");
	
	public static Final parseFinal(String value)
	{
		return enums.get(value);
	}
	
	private final String value;
	
	private Final(String value)
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
		if(!(obj instanceof Final))
			return false;
		
		return getValue().equals(((Final)obj).getValue());
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
