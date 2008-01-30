package org.safris.commons.util.logging;

public abstract class LoggerResourceBundleName
{
	private final String name;

	protected LoggerResourceBundleName(String name)
	{
		this.name = name;
	}

	protected String getName()
	{
		return name;
	}
}
