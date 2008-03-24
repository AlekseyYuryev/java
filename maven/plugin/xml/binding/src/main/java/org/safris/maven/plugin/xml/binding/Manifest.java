package org.safris.maven.plugin.xml.binding;

import java.util.List;

public final class Manifest
{
	private String destdir = null;
	private String link = null;
	private List<String> schemas = null;

	public String getDestdir()
	{
		return destdir;
	}

	public String getLink()
	{
		return link;
	}

	public List<String> getSchemas()
	{
		return schemas;
	}
}
