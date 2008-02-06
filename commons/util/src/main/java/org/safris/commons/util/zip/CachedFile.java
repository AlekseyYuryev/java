package org.safris.commons.util.zip;

import java.io.File;
import java.net.URI;

public class CachedFile extends File
{
	private final byte[] bytes;

    public CachedFile(String pathname, byte[] bytes)
	{
		super(pathname);
		this.bytes = bytes;
	}

    public CachedFile(String parent, String child, byte[] bytes)
	{
		super(parent, child);
		this.bytes = bytes;
	}

	public CachedFile(File parent, String child, byte[] bytes)
	{
		super(parent, child);
		this.bytes = bytes;
	}

	public CachedFile(URI uri, byte[] bytes)
	{
		super(uri);
		this.bytes = bytes;
	}

	public byte[] getBytes()
	{
		return bytes;
	}
}
