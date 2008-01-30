package org.safris.commons.util.logging;

public abstract class LoggerName
{
	private final String name;

	protected LoggerName(String name)
	{
		this.name = name;
	}

	protected String getName()
	{
		return name;
	}
}
