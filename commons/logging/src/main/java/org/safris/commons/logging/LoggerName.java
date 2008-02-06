package org.safris.commons.logging;

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
