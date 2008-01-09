package org.safris.xml.generator.compiler.runtime;

public class RuntimeBindingException extends RuntimeException
{
	public RuntimeBindingException()
	{
		super();
	}
	
	public RuntimeBindingException(String message)
	{
		super(message);
	}
	
	public RuntimeBindingException(Throwable cause)
	{
		super(cause);
	}
	
	public RuntimeBindingException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
