package org.safris.xml.generator.compiler.runtime.lang;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.compiler.runtime.lang.Language;

public class Language implements CharSequence
{
	public static Language parseLanguage(String string)
	{
		return singletons.get(string);
	}
	
	private static final Map<String,Language> singletons = new HashMap<String,Language>();
	private final String value;
	
	public Language(String value)
	{
		this.value = value;
		singletons.put(value, this);
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
