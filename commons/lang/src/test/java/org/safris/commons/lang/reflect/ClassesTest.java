package org.safris.commons.lang.reflect;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import junit.framework.TestCase;
import org.junit.Test;
import org.safris.commons.lang.reflect.Classes;
import org.safris.commons.lang.reflect.ClassesTest;

public class ClassesTest extends TestCase
{
	private static final Map<Class[],Class> gccs = new HashMap<Class[],Class>();
	
	static
	{
		gccs.put(new Class[]{String.class, Integer.class}, Object.class);
		gccs.put(new Class[]{Long.class, Integer.class}, Number.class);
		gccs.put(new Class[]{ArrayList.class, LinkedList.class}, AbstractList.class);
		gccs.put(new Class[]{HashSet.class, LinkedHashSet.class}, HashSet.class);
		gccs.put(new Class[]{FileInputStream.class, ByteArrayInputStream.class, DataInputStream.class, FilterInputStream.class}, InputStream.class);
	}
	
	public static void main(String[] args) throws Exception
	{
		new ClassesTest().testGreatestCommonClass();
	}
	
	@Test
	public void testGreatestCommonClass() throws Exception
	{
		for(Map.Entry<Class[],Class> entry : gccs.entrySet())
			assertEquals(Classes.getGreatestCommonSuperclass(entry.getKey()), entry.getValue());
	}
}
