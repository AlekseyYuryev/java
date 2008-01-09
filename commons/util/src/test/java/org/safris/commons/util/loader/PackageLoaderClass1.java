package org.safris.commons.util.loader;

import org.safris.commons.util.PackageLoaderTest;

public class PackageLoaderClass1
{
	static
	{
		PackageLoaderTest.registerLoad();
	}
}
