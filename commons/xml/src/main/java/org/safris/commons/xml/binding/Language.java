package org.safris.commons.xml.binding;

import java.util.HashMap;
import java.util.Map;

public class Language implements CharSequence
{
	public static Language parseLanguage(String string)
	{
		return instances.get(string);
	}

	private static final Map<String,Language> instances = new HashMap<String,Language>();
	private final String value;

	public Language(String value)
	{
		this.value = value;
		instances.put(value, this);
	}

	public int length()
	{
		// TODO: Implement this method
		return 0;
	}

	public char charAt(int index)
	{
		// TODO: Implement this method
		return 0;
	}

	public CharSequence subSequence(int start, int end)
	{
		// TODO: Implement this method
		return null;
	}
}
