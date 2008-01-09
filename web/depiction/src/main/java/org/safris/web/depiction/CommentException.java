package org.safris.web.depiction;

public class CommentException extends Exception
{
	public CommentException(String msg)
	{
		super(msg);
	}
	
	public CommentException(Exception cause)
	{
		super(cause);
	}
	
	public CommentException(String msg, Exception cause)
	{
		super(msg + "\n" + cause.getMessage());
		setStackTrace(cause.getStackTrace());
	}
}
