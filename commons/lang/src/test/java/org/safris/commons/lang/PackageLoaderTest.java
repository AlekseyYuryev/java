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

import java.util.HashSet;
import java.util.Set;
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
		final String[] testClasses = new String[]{
			"org.junit.experimental.results.ResultMatchers",
			"org.junit.experimental.theories.internal.TheoryMethod",
			"org.junit.experimental.theories.suppliers.TestedOnSupplier",
			"org.junit.matchers.Each",
			"org.junit.runners.Enclosed",
			"org.junit.runners.Parameterized",
			"org.junit.runners.Suite",
			"org.junit.PackageLoaderClass1",
			"org.junit.PackageLoaderClass2",
			"org.junit.PackageLoaderClass3"
		};

		for(String testClass : testClasses)
			assertFalse(isClassLoaded(testClass));

		final Set<Class<?>> loadedClasses = PackageLoader.getSystemPackageLoader().loadPackage("org.junit");
		final Set<String> classNames = new HashSet<String>();
		for(Class<?> loadedClass : loadedClasses)
			classNames.add(loadedClass.getName());

		for(String testClass : testClasses)
		{
			assertTrue(classNames.contains(testClass));
			assertTrue(isClassLoaded(testClass));
		}

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
