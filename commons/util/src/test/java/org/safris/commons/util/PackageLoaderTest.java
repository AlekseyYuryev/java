package org.safris.commons.util;

import java.util.Collection;
import java.util.HashSet;
import junit.framework.TestCase;
import org.safris.commons.util.loader.PackageLoaderClass1;
import org.safris.commons.util.loader.PackageLoaderClass2;
import org.safris.commons.util.loader.PackageLoaderClass3;
import sun.reflect.Reflection;

public class PackageLoaderTest extends TestCase
{
	private static final Collection<Class> loadedClasses = new HashSet<Class>();

	public static void main(String[] args) throws Exception
	{
		new PackageLoaderTest().testPackageLoader();
	}

	public void testPackageLoader() throws Exception
	{
		PackageLoader.getSystemPackageLoader().loadPackage(getClass().getPackage().getName() + ".loader");
		assertTrue(loadedClasses.contains(PackageLoaderClass1.class));
		assertTrue(loadedClasses.contains(PackageLoaderClass2.class));
		assertTrue(loadedClasses.contains(PackageLoaderClass3.class));
	}

	public static void registerLoad()
	{
		// Gets the caller class
		Class clazz = Reflection.getCallerClass(2);
		if(loadedClasses.contains(clazz))
			fail("Loading " + clazz.getSimpleName() + " twice!");

		loadedClasses.add(clazz);
	}
}
