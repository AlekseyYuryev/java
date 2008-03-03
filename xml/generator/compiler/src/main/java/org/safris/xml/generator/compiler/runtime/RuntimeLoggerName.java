package org.safris.xml.generator.compiler.runtime;

import org.safris.commons.logging.LoggerName;

public class RuntimeLoggerName extends LoggerName
{
	public static final RuntimeLoggerName VALIDATOR = new RuntimeLoggerName("VALIDATOR");

	public RuntimeLoggerName(String name)
	{
		super(name);
	}
}
