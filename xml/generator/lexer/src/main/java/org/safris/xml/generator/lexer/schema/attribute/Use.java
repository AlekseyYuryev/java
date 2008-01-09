package org.safris.xml.generator.lexer.schema.attribute;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.schema.attribute.Use;

public final class Use
{
	private static final Map<String,Use> enums = new HashMap<String,Use>();
	
	public static final Use OPTIONAL = new Use("optional");
	public static final Use PROHIBITED = new Use("prohibited");
	public static final Use REQUIRED = new Use("required");
	
	public static Use parseUse(String value)
	{
		return enums.get(value);
	}
	
	private final String value;
	
	private Use(String value)
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
		if(!(obj instanceof Use))
			return false;
		
		return getValue().equals(((Use)obj).getValue());
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
