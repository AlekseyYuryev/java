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

package org.safris.xml.generator.compiler.runtime;

import java.io.File;
import java.io.IOException;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassLoaderTest
{
	public static void main(String[] args) throws Exception
	{
		if(args == null || args.length == 0)
		{
			new ClassLoaderTest().testClassLoaders();
			return;
		}

		if("weak".equals(args[0]))
			testClassLoader(new WeakClassLoader());
		else if("system".equals(args[0]))
			testClassLoader(ClassLoader.getSystemClassLoader());
		else
			throw new Error("Unknown classLoader option");

		Runtime.getRuntime().gc();
	}

	private static float testClassLoader(java.lang.ClassLoader classLoader) throws Exception
	{
		long freeMemoryBeforeLoad = Runtime.getRuntime().freeMemory();
//		PackageLoader.getSystemPackageLoader().loadPackage("org.safris.xml.toolkit.component.runtime");
		long freeMemoryAfterLoad = Runtime.getRuntime().freeMemory();

		Runtime.getRuntime().gc();

		long freeMemoryAfterUnload = Runtime.getRuntime().freeMemory();
		float ratio = (float)freeMemoryAfterUnload / (float)freeMemoryAfterLoad;
		System.out.println(classLoader.getClass().getName());
		System.out.println("{");
		System.out.println("\tFree Memory Before Load: " + freeMemoryBeforeLoad);
		System.out.println("\tFree Memory After Load: " + freeMemoryAfterLoad);
		System.out.println("\tFree Memory After Unload: " + freeMemoryAfterUnload);
		System.out.println("\tratio: " + ratio);
		System.out.println("}");
		return ratio;
	}

	@Test
	// FIXME
    @Ignore("FIXME")
	public void testClassLoaders() throws IOException
	{
		Runtime.getRuntime().gc();
		// FIXME: The scenario that is tested here is the more performant than the other.
		float system = fork("system");
		float weak = fork("weak");

		weak = fork("weak");
		system = fork("system");
		//assertTrue("Custom classLoader is NOT more efficient than System classLoader!", weak < system);

		system = fork("system");
		weak = fork("weak");
		//assertTrue("Custom classLoader is NOT more efficient than System classLoader!", weak < system);
	}

	private static float fork(String loader) throws IOException
	{
		String classpath = "";
		String userDir = System.getProperty("user.dir");
		String localRepository = System.getProperty("localRepository");
		if(userDir != null)
		{
			classpath += File.pathSeparator + userDir + "/target/classes";
			classpath += File.pathSeparator + userDir + "/target/test-classes";
		}

		if(localRepository != null)
			classpath += File.pathSeparator + localRepository + "/junit/junit/4.3/junit-4.3.jar";

		if(classpath.length() != 0)
			System.setProperty("java.class.path", System.getProperty("java.class.path") + classpath);

/*		Process process = Forks.fork(ClassLoaderTest.class, new String[]{loader});
		InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
		InputStreamReader errorStreamReader = new InputStreamReader(process.getErrorStream());
		BufferedReader bufferedInputReader = new BufferedReader(inputStreamReader);
		BufferedReader bufferedErrorReader = new BufferedReader(errorStreamReader);
		String line = null;
		float ratio = 0;
		while((line = bufferedInputReader.readLine()) != null)
		{
			if(line.contains("ratio: "))
				ratio = Float.parseFloat(line.substring(line.indexOf(": ") + 2));

			System.out.println(line);
		}

		while((line = bufferedErrorReader.readLine()) != null)
			System.out.println(line);

		try
		{
			process.waitFor();
		}
		catch(InterruptedException e)
		{
			throw new Error(e);
		}

		return ratio;*/ return 0;
	}
}
