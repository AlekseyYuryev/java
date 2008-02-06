package org.safris.xml.generator.compiler.lang;

import org.safris.commons.logging.LoggerName;

public class CompilerLoggerName extends LoggerName
{
	public static final CompilerLoggerName PLAN = new CompilerLoggerName("PLAN");
	public static final CompilerLoggerName WRITE = new CompilerLoggerName("WRITE");

	public CompilerLoggerName(String name)
	{
		super(name);
	}
}
