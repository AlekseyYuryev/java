package org.safris.xml.generator.lexer.schema.attribute;

import org.safris.xml.generator.lexer.schema.attribute.Occurs;

public final class Occurs
{
	public static final Occurs UNBOUNDED = new Occurs(Integer.MAX_VALUE);
	
	public static Occurs parseOccurs(String value)
	{
		if("unbounded".equals(value))
			return UNBOUNDED;
		
		return new Occurs(Integer.parseInt(value));
	}
	
	private int value = 1;
	
	private Occurs(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public boolean equals(Object o)
	{
		if(!(o instanceof Occurs))
			return false;
		
		return getValue() == ((Occurs)o).getValue();
	}
	
	public int hashCode()
	{
		return (getClass().getName() + toString()).hashCode();
	}
	
	public String toString()
	{
		return String.valueOf(value);
	}
}
