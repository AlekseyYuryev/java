package org.safris.xml.generator.compiler.runtime;

public class ParseException extends BindingException
{
	public ParseException()
	{
		super();
	}

	public ParseException(String message)
	{
		super(message);
	}

	public ParseException(Throwable cause)
	{
		super(cause);
	}

	public ParseException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
