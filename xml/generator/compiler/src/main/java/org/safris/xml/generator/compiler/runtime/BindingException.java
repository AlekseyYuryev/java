package org.safris.xml.generator.compiler.runtime;

public class BindingException extends Exception
{
	public static final String ILLEGAL_NULL_ARGUMENT = "value cannot be null";
	
	public BindingException()
	{
		super();
	}
	
	public BindingException(String message)
	{
		super(message);
	}
	
	public BindingException(Throwable cause)
	{
		super(cause);
	}
	
	public BindingException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
