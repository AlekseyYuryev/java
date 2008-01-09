package org.safris.commons.format;

import org.safris.commons.format.FormatModule;

public abstract class FormatModule
{
	private static int depth = 0;
	private static FormatModule lastModule = null;
	
	public static void restetDepth()
	{
		depth = 0;
	}
	
	protected void increaseDepth()
	{
		depth++;
	}
	
	protected void decreaseDepth()
	{
		depth--;
	}
	
	protected int getDepth()
	{
		return depth;
	}
	
	protected static FormatModule getLastModule()
	{
		return lastModule;
	}
	
	protected static void setLastModule(FormatModule module)
	{
		FormatModule.lastModule = module;
	}
	
	abstract String format(String formated, String token);
}
