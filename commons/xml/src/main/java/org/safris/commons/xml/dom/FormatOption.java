package org.safris.commons.xml.dom;

import org.safris.commons.xml.dom.FormatOption;

	public class FormatOption
	{
		protected static FormatOption consolidate(FormatOption ... options)
			{
		if(options == null)
		return null;
			
		if(options.length == 0)
		return DEFAULT;
			
		if(options.length == 1)
		return options[0];
		
			final FormatOption consolidated = new FormatOption(DEFAULT_MASK);
		for(FormatOption option : options)
		consolidated.mask = consolidated.mask | option.mask;
	
	return consolidated;
	}
	
	private static final int DEFAULT_MASK = 0x00;
	private static final int INDENT_MASK = 0x01;
	private static final int IGNORE_NAMESPACES_MASK = 0x10;
	
	private static final FormatOption DEFAULT = new FormatOption(DEFAULT_MASK);
	public static final FormatOption INDENT = new FormatOption(INDENT_MASK);
	public static final FormatOption IGNORE_NAMESPACES = new FormatOption(IGNORE_NAMESPACES_MASK);
	
	private int mask = 0;
	
		public FormatOption(int mask)
	{
	this.mask = mask;
	}
	
		protected boolean isIndent()
	{
	return (mask & INDENT_MASK) == INDENT_MASK;
	}
	
		protected boolean isIgnoreNamespaces()
	{
	return (mask & IGNORE_NAMESPACES_MASK) == IGNORE_NAMESPACES_MASK;
	}
	
		public int hashCode()
	{
	return mask;
	}
	
		public boolean equals(Object obj)
			{
		if(this == obj)
		return true;
			
		if(!(obj instanceof FormatOption))
		return false;
	
return ((FormatOption)obj).mask == mask;
	}
}
