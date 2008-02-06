package org.safris.xml.generator.lexer.lang;

import org.safris.commons.logging.LoggerName;

public class LexerLoggerName extends LoggerName
{
	public static final LexerLoggerName COMPOSITE = new LexerLoggerName("COMPOSITE");
	public static final LexerLoggerName DOCUMENT = new LexerLoggerName("DOCUMENT");
	public static final LexerLoggerName MODEL = new LexerLoggerName("MODEL");
	public static final LexerLoggerName NORMALIZE = new LexerLoggerName("NORMALIZE");
	public static final LexerLoggerName REFERENCE = new LexerLoggerName("REFERENCE");

	public LexerLoggerName(String name)
	{
		super(name);
	}
}
