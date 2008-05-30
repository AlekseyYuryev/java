/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.commons.lang;

import org.junit.Test;
import sun.reflect.Reflection;

import static org.junit.Assert.*;

public class PackageLoaderTest
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
		assertFalse(isClassLoaded("org.junit.experimental.results.ResultMatchers"));
		assertFalse(isClassLoaded("org.junit.experimental.theories.internal.TheoryMethod"));
		assertFalse(isClassLoaded("org.junit.experimental.theories.suppliers.TestedOnSupplier"));
		assertFalse(isClassLoaded("org.junit.matchers.Each"));
		assertFalse(isClassLoaded("org.junit.runners.Enclosed"));
		assertFalse(isClassLoaded("org.junit.runners.Parameterized"));
		assertFalse(isClassLoaded("org.junit.runners.Suite"));
		assertFalse(isClassLoaded("org.junit.PackageLoaderClass1"));
		assertFalse(isClassLoaded("org.junit.PackageLoaderClass2"));
		assertFalse(isClassLoaded("org.junit.PackageLoaderClass3"));
		PackageLoader.getSystemPackageLoader().loadPackage("org.junit");
		assertTrue(isClassLoaded("org.junit.experimental.results.ResultMatchers"));
		assertTrue(isClassLoaded("org.junit.experimental.theories.internal.TheoryMethod"));
		assertTrue(isClassLoaded("org.junit.experimental.theories.suppliers.TestedOnSupplier"));
		assertTrue(isClassLoaded("org.junit.matchers.Each"));
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
