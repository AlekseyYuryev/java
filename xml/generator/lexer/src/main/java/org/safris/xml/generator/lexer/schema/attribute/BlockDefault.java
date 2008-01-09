package org.safris.xml.generator.lexer.schema.attribute;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.schema.attribute.BlockDefault;

public final class BlockDefault
{
	private static final Map<String,BlockDefault> enums = new HashMap<String,BlockDefault>();
	
	public static final BlockDefault ALL = new BlockDefault("#all");
	public static final BlockDefault EXTENSION = new BlockDefault("extension");
	public static final BlockDefault RESTRICTION = new BlockDefault("restriction");
	public static final BlockDefault SUBSTITUTION = new BlockDefault("substitution");
	
	public static BlockDefault parseBlockDefault(String value)
	{
		return enums.get(value);
	}
	
	private final String value;
	
	private BlockDefault(String value)
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
		if(!(obj instanceof BlockDefault))
			return false;
		
		return getValue().equals(((BlockDefault)obj).getValue());
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
