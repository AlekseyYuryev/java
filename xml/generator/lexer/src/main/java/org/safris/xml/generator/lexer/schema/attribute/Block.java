package org.safris.xml.generator.lexer.schema.attribute;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.schema.attribute.Block;

public final class Block
{
	private static final Map<String,Block> enums = new HashMap<String,Block>();
	
	public static final Block ALL = new Block("#all");
	public static final Block EXTENSION = new Block("extension");
	public static final Block RESTRICTION = new Block("restriction");
	public static final Block SUBSTITUTION = new Block("substitution");
	
	public static Block parseBlock(String value)
	{
		return enums.get(value);
	}
	
	private final String value;
	
	private Block(String value)
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
		if(!(obj instanceof Block))
			return false;
		
		return getValue().equals(((Block)obj).getValue());
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
