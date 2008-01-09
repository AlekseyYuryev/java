package org.safris.web.depiction;

public class ImageException extends Exception
{
	public ImageException(String msg)
	{
		super(msg);
	}
	
	public ImageException(Exception cause)
	{
		super(cause);
	}
	
	public ImageException(String msg, Exception cause)
	{
		super(msg + "\n" + cause.getMessage());
		setStackTrace(cause.getStackTrace());
	}
}
