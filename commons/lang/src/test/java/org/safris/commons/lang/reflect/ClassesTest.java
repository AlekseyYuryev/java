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
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassesTest
{
	public static void main(String[] args) throws Exception
	{
		final ClassesTest classesTest = new ClassesTest();
		classesTest.setUp();
		classesTest.testGreatestCommonClass();
	}

	private final Map<Class[],Class> classes = new HashMap<Class[],Class>();

	@Before
	public void setUp()
	{
		classes.put(new Class[]{String.class}, String.class);
		classes.put(new Class[]{String.class, Integer.class}, Object.class);
		classes.put(new Class[]{Long.class, Integer.class}, Number.class);
		classes.put(new Class[]{ArrayList.class, LinkedList.class}, AbstractList.class);
		classes.put(new Class[]{HashSet.class, LinkedHashSet.class}, HashSet.class);
		classes.put(new Class[]{FileInputStream.class, ByteArrayInputStream.class, DataInputStream.class, FilterInputStream.class}, InputStream.class);
	}

	@Test
	public void testGreatestCommonClass() throws Exception
	{
		for(Map.Entry<Class[],Class> entry : classes.entrySet())
			assertEquals(Classes.getGreatestCommonSuperclass(entry.getKey()), entry.getValue());

		assertNull(Classes.getGreatestCommonSuperclass(null));
	}
}
