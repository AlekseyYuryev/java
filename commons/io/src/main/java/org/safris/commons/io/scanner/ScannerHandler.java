package org.safris.commons.io.scanner;

import java.io.IOException;

public abstract class ScannerHandler
{
	private final String input;
	
	public ScannerHandler(String match)
	{
		this.input = match;
	}
	
	public String getMatch()
	{
		return input;
	}
	
	public abstract void match(String match) throws IOException;
}
