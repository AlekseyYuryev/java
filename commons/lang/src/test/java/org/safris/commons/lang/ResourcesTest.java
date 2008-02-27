package org.safris.commons.lang;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourcesTest
{
	private static final File JAVA_HOME = new File(System.getProperty("java.home"));
	private static final File RT_JAR = new File(JAVA_HOME, "lib/rt.jar");

	public static void main(String[] args) throws Exception
	{
		final ResourcesTest resourcesTest = new ResourcesTest();
		resourcesTest.testGetLocationBase();
		resourcesTest.testGetResource();
		resourcesTest.testGetResources();
	}

	@Test
	public void testGetLocationBase()
	{
		assertNull(Resources.getLocationBase(null));
		assertTrue(Resources.getLocationBase(ResourcesTest.class).isDirectory());
		assertEquals(RT_JAR, Resources.getLocationBase(String.class));
	}

	@Test
	public void testGetResource() throws Exception
	{
		assertNull(Resources.getResource(null));
		assertNull(Resources.getResource(""));
		assertEquals(new URL("jar:file:" + RT_JAR.getAbsolutePath() + "!/META-INF"), Resources.getResource("META-INF").getURL());
	}

	@Test
	public void testGetResources() throws Exception
	{
		assertNull(Resources.getResources(null));
		assertNull(Resources.getResources(""));
		final Enumeration<Resource> resources = Resources.getResources("META-INF");
		final URL RT_JAR_META_INF = new URL("jar:file:" + RT_JAR.getAbsolutePath() + "!/META-INF");
		boolean found = false;
		while(resources.hasMoreElements())
		{
			final Resource resource = resources.nextElement();
			if(!RT_JAR_META_INF.equals(resource.getURL()))
				continue;

			found = true;
			break;
		}

		assertTrue(found);
	}
}
