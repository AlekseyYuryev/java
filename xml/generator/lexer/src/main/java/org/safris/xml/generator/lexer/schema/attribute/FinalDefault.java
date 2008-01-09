package org.safris.xml.generator.lexer.schema.attribute;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.schema.attribute.FinalDefault;

public final class FinalDefault
{
	private static Map<String,FinalDefault> enums = new HashMap<String,FinalDefault>();
	
	public static FinalDefault ALL = new FinalDefault("#all");
	public static FinalDefault EXTENSION = new FinalDefault("extension");
	public static FinalDefault RESTRICTION = new FinalDefault("restriction");
	public static FinalDefault SUBSTITUTION = new FinalDefault("substitution");
	
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
