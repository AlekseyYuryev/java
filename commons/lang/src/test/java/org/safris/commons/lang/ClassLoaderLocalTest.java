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
	@Ignore
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
