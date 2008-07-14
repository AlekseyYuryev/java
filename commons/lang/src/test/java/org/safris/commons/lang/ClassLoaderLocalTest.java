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

import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassLoaderLocalTest
{
	public static void main(String[] args) throws Exception
	{
		new ClassLoaderLocalTest().testClassLoaderLocal();
	}

	private static String className = "com.sun.jmx.snmp.ThreadContext";

	@Test
	// FIXME
    @Ignore("FIXME")
	public void testClassLoaderLocal() throws Exception
	{
		final String bootClassPath = System.getProperty("sun.boot.class.path");
		final Collection<URL> urls = new ArrayList<URL>();
		final StringTokenizer tokenizer = new StringTokenizer(bootClassPath, ":");
		while(tokenizer.hasMoreTokens())
			urls.add(new URL("file", null, tokenizer.nextToken()));

		final WeakReference<URLClassLoader> classLoaderReference = new WeakReference<URLClassLoader>(new URLClassLoader(urls.toArray(new URL[urls.size()]), null));
		print(classLoaderReference);
		classLoaderReference.get().loadClass(className).getDeclaredMethod("push", String.class, Object.class).invoke(null, "YES", "ONE");
		classLoaderReference.get().loadClass(className);
		print(classLoaderReference);
		classLoaderReference.enqueue();
		System.gc();

		final Object tc2 = ClassLoader.getSystemClassLoader().loadClass(className).getDeclaredMethod("get", String.class).invoke(null, "YES");
		System.out.println(tc2);
		print(classLoaderReference);
	}

	private void print(WeakReference<URLClassLoader> classLoaderReference)
	{
		System.out.println("----");
		if(classLoaderReference.get() != null)
			System.out.println("Weak: " + ClassLoaders.isClassLoaded(classLoaderReference.get(), className) + " " + classLoaderReference.get().getClass().getClassLoader());
		System.out.println("Current: " + ClassLoaders.isClassLoaded(getClass().getClassLoader(), className) + " " + getClass().getClassLoader());
		System.out.println("Context: " + ClassLoaders.isClassLoaded(Thread.currentThread().getContextClassLoader(), className) + " " + Thread.currentThread().getClass().getClassLoader());
		System.out.println("System: " + ClassLoaders.isClassLoaded(ClassLoader.getSystemClassLoader(), className) + " " + ClassLoader.getSystemClassLoader().getClass().getClassLoader());
		System.out.println("Bootstrap: " + ClassLoaders.isClassLoaded(ClassLoader.getSystemClassLoader(), className) + " " + ClassLoader.getSystemClassLoader().getParent().getClass().getClassLoader());
	}
}
