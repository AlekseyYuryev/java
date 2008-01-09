package org.safris.xml.generator.compiler.runtime;

import org.safris.xml.generator.compiler.runtime.RuntimeBindingException;

public class IllegalBindingException extends RuntimeBindingException
{
	public IllegalBindingException()
	{
		super();
	}
	
	public IllegalBindingException(String message)
	{
		super(message);
	}
	
	public IllegalBindingException(Throwable cause)
	{
		super(cause);
	}
	
	public IllegalBindingException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
