package org.safris.xml.generator.compiler.runtime;

import org.safris.xml.generator.compiler.runtime.BindingException;

public class MarshalException extends BindingException
{
	public MarshalException()
	{
		super();
	}
	
	public MarshalException(String message)
	{
		super(message);
	}
	
	public MarshalException(Throwable cause)
	{
		super(cause);
	}
	
	public MarshalException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
