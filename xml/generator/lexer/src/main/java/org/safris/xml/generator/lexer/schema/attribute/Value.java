package org.safris.xml.generator.lexer.schema.attribute;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.schema.attribute.Value;

public final class Value
	{
	private static final Map<String,Value> enums = new HashMap<String,Value>();
	
	public static final Value COLLAPSE = new Value("collapse");
	public static final Value PRESERVE = new Value("preserve");
	public static final Value REPLACE = new Value("replace");
	
	public static Value parseValue(String value)
		{
	return enums.get(value);
	}
	
	private final String value;
	
	private Value(String value)
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
			if(!(obj instanceof Value))
		return false;
		
	return getValue().equals(((Value)obj).getValue());
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
