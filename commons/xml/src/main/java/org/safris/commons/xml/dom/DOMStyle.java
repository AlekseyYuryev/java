package org.safris.commons.xml.dom;

public class DOMStyle
{
	protected static DOMStyle consolidate(DOMStyle ... options)
	{
		if(options == null)
			return null;

		if(options.length == 0)
			return DEFAULT;

		if(options.length == 1)
			return options[0];

		final DOMStyle consolidated = new DOMStyle(DEFAULT_MASK);
		for(DOMStyle option : options)
			consolidated.mask = consolidated.mask | option.mask;

		return consolidated;
	}

	private static final int DEFAULT_MASK = 0x00;
	private static final int INDENT_MASK = 0x01;
	private static final int IGNORE_NAMESPACES_MASK = 0x10;

	private static final DOMStyle DEFAULT = new DOMStyle(DEFAULT_MASK);
	public static final DOMStyle INDENT = new DOMStyle(INDENT_MASK);
	public static final DOMStyle IGNORE_NAMESPACES = new DOMStyle(IGNORE_NAMESPACES_MASK);

	private int mask = 0;

	public DOMStyle(int mask)
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

		if(!(obj instanceof DOMStyle))
			return false;

		return ((DOMStyle)obj).mask == mask;
	}
}
