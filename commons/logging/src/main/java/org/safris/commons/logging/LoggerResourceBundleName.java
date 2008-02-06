package org.safris.commons.logging;

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
