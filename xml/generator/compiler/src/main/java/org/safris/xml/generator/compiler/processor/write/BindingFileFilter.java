package org.safris.xml.generator.compiler.processor.write;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;

public class BindingFileFilter implements FileFilter
{
	private final boolean acceptHidden;

	public BindingFileFilter(boolean acceptHidden)
	{
		this.acceptHidden = acceptHidden;
	}

	public boolean accept(File pathname)
	{
		if(!acceptHidden && pathname.isHidden())
			return false;

		if(pathname.isDirectory())
			return true;

		try
		{
			final InputStream in = pathname.toURL().openStream();
			final byte[] bytes = new byte[15];
			in.read(bytes);
			in.close();
			return new String(bytes).contains("SAFRIS.org");
		}
		catch(IOException e)
		{
			return false;
		}
	}
}
