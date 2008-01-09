package org.safris.xml.generator.compiler.runtime;

public class BindingError extends Error
{
	public BindingError()
	{
		super();
	}
	
	public BindingError(String message)
	{
		super(message);
	}
	
	public BindingError(Throwable cause)
	{
		super(cause);
	}
	
	public BindingError(String message, Throwable cause)
	{
		super(message, cause);
	}
}
