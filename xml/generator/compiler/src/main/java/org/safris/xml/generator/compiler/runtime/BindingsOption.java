package org.safris.xml.generator.compiler.runtime;

public class BindingsOption
{
	protected static BindingsOption consolidate(BindingsOption ... options)
	{
		if(options == null)
			return null;

		if(options.length == 0)
			return DEFAULT;

		if(options.length == 1)
			return options[0];

		final BindingsOption consolidated = new BindingsOption(DEFAULT_MASK);
		for(BindingsOption option : options)
			consolidated.mask = consolidated.mask | option.mask;

		return consolidated;
	}

	private static final int DEFAULT_MASK = 0x00;
	private static final int INDENT_MASK = 0x01;
	private static final int IGNORE_NAMESPACES_MASK = 0x10;

	private static final BindingsOption DEFAULT = new BindingsOption(DEFAULT_MASK);
	public static final BindingsOption INDENT = new BindingsOption(INDENT_MASK);
	public static final BindingsOption IGNORE_NAMESPACES = new BindingsOption(IGNORE_NAMESPACES_MASK);

	private int mask = 0;

	public BindingsOption(int mask)
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

		if(!(obj instanceof BindingsOption))
			return false;

		return ((BindingsOption)obj).mask == mask;
	}
}
