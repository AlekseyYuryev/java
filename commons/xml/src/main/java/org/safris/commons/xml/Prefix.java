package org.safris.commons.xml;

import java.util.HashMap;
import java.util.Map;

public final class Prefix
{
	private static final Map<String,Prefix> instances = new HashMap<String,Prefix>();

	public static Prefix getInstance(String prefix)
	{
		Prefix value = instances.get(prefix);
		if(value == null)
			instances.put(prefix, value = new Prefix(prefix));

		return value;
	}

	public static final Prefix EMPTY = new Prefix("");

	private final String prefix;
	private final String title;
	private final String lower;

	private Prefix(String prefix)
	{
		this.prefix = prefix;
		if(prefix.equals(prefix.toUpperCase()))
		{
			title = prefix;
			lower = prefix.toLowerCase();
		}
		else if(64 < prefix.charAt(0) && prefix.charAt(0) < 91)
		{
			title = prefix;
			lower = title.substring(0, 1).toLowerCase() + title.substring(1);
		}
		else
		{
			title = prefix.substring(0, 1).toUpperCase() + prefix.substring(1);
			lower = prefix;
		}
	}

	public boolean equals(Object obj)
	{
		if(!(obj instanceof Prefix))
			return false;

		return prefix.equals(((Prefix)obj).prefix);
	}

	public int hashCode()
	{
		return prefix.hashCode();
	}

	public String toString()
	{
		return prefix;
	}

//	public String toStringTitleCase()
//	{
//		return title;
//	}
//
//	public String toStringLowerCase()
//	{
//		return lower;
//	}
}
