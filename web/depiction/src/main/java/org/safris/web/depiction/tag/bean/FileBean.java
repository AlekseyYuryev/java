package org.safris.web.depiction.tag.bean;

import java.io.File;
import org.safris.web.depiction.tag.bean.Bean;

public class FileBean implements Bean
{
	private final File file;
	
	public FileBean(File file)
	{
		this.file = file;
	}
	
	public File getObject()
	{
		return file;
	}
	
	public String getPath()
	{
		return file.getPath();
	}
	
	public long getLastModified()
	{
		return file.lastModified();
	}
	
	public String toString()
	{
		return file.toString();
	}
}
