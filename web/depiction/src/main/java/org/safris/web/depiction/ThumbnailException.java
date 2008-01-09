package org.safris.web.depiction;

public class ThumbnailException extends Exception
{
	public ThumbnailException(String msg)
	{
		super(msg);
	}
	
	public ThumbnailException(Exception cause)
	{
		super(cause);
	}
	
	public ThumbnailException(String msg, Exception cause)
	{
		super(msg + "\n" + cause.getMessage());
		setStackTrace(cause.getStackTrace());
	}
}
