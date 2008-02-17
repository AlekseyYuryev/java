package org.safris.commons.lang;

import junit.framework.TestCase;
import org.junit.Test;
import sun.reflect.Reflection;

public class PackageLoaderTest extends TestCase
{
	public static void main(String[] args) throws Exception
	{
		new PackageLoaderTest().testPackageLoader();
	}

	private static boolean isClassLoaded(String name)
	{
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		if(ClassLoaders.isClassLoaded(classLoader, name))
			return true;

		classLoader = Thread.currentThread().getContextClassLoader();
		if(ClassLoaders.isClassLoaded(classLoader, name))
			return true;

		final Class callerClass = Reflection.getCallerClass(2);
		classLoader = callerClass.getClassLoader();
		if(ClassLoaders.isClassLoaded(classLoader, name))
			return true;

		return false;
	}

	@Test
	public void testPackageLoader() throws Exception
	{
		assertFalse(isClassLoaded("org.junit.ComparisonFailure"));
		assertFalse(isClassLoaded("org.junit.experimental.results.ResultMatchers"));
		assertFalse(isClassLoaded("org.junit.experimental.theories.internal.TheoryMethod"));
		assertFalse(isClassLoaded("org.junit.experimental.theories.suppliers.TestedOnSupplier"));
		assertFalse(isClassLoaded("org.junit.internal.ArrayComparisonFailure"));
		assertFalse(isClassLoaded("org.junit.matchers.Each"));
		assertFalse(isClassLoaded("org.junit.runner.manipulation.NoTestsRemainException"));
		assertFalse(isClassLoaded("org.junit.runners.Enclosed"));
		assertFalse(isClassLoaded("org.junit.runners.Parameterized"));
		assertFalse(isClassLoaded("org.junit.runners.Suite"));
		assertFalse(isClassLoaded("org.junit.PackageLoaderClass1"));
		assertFalse(isClassLoaded("org.junit.PackageLoaderClass2"));
		assertFalse(isClassLoaded("org.junit.PackageLoaderClass3"));
		PackageLoader.getSystemPackageLoader().loadPackage("org.junit");
		assertTrue(isClassLoaded("org.junit.ComparisonFailure"));
		assertTrue(isClassLoaded("org.junit.experimental.results.ResultMatchers"));
		assertTrue(isClassLoaded("org.junit.experimental.theories.internal.TheoryMethod"));
		assertTrue(isClassLoaded("org.junit.experimental.theories.suppliers.TestedOnSupplier"));
		assertTrue(isClassLoaded("org.junit.internal.ArrayComparisonFailure"));
		assertTrue(isClassLoaded("org.junit.matchers.Each"));
		assertTrue(isClassLoaded("org.junit.runner.manipulation.NoTestsRemainException"));
		assertTrue(isClassLoaded("org.junit.runners.Enclosed"));
		assertTrue(isClassLoaded("org.junit.runners.Parameterized"));
		assertTrue(isClassLoaded("org.junit.runners.Suite"));
		assertTrue(isClassLoaded("org.junit.PackageLoaderClass1"));
		assertTrue(isClassLoaded("org.junit.PackageLoaderClass2"));
		assertTrue(isClassLoaded("org.junit.PackageLoaderClass3"));

		try
		{
			PackageLoader.getSystemPackageLoader().loadPackage(null);
			fail("Expected a PackageNotFoundException");
		}
		catch(PackageNotFoundException e)
		{
		}
	}
}
