package org.safris.xml.generator.compiler.runtime;

public class BindingConfig
{
	private boolean indent = false;
	
	public BindingConfig()
	{
	}
	
	public void setIndent(boolean indent)
	{
		this.indent = indent;
	}
	
	protected boolean getIndent()
	{
		return indent;
	}
}
