package org.safris.xml.generator.lexer.schema.attribute;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.schema.attribute.Form;

public class Form
{
	private static final Map<String,Form> enums = new HashMap<String,Form>();
	
	public static final Form QUALIFIED = new Form("qualified");
	public static final Form UNQUALIFIED = new Form("unqualified");
	
	public static Form parseForm(String value)
	{
		return enums.get(value);
	}
	
	private final String value;
	
	private Form(String value)
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
		if(!(obj instanceof Form))
			return false;
		
		return getValue().equals(((Form)obj).getValue());
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
