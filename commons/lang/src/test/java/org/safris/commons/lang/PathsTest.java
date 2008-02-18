package org.safris.commons.lang;

import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import org.junit.Test;

public class PathsTest extends TestCase
{
	public static void main(String[] args) throws Exception
	{
		final PathsTest pathsTest = new PathsTest();
		pathsTest.testGetName();
		pathsTest.testGetParent();
	}

	@Test
	public void testGetName() throws Exception
	{
		final Map<String,String> paths = new HashMap<String,String>();
		paths.put("share", "file:///usr/share/../share");
		paths.put("lib", "file:///usr/share/../share/../lib");
		paths.put("var", "/usr/share/../share/../lib/../../var");
		paths.put("var", "/usr/share/../share/../lib/../../var/");
		paths.put("resolv.conf", "/etc/resolv.conf");
		paths.put("name", "name");

		for(Map.Entry<String,String> entry : paths.entrySet())
			assertEquals(entry.getKey(), Paths.getName(entry.getValue()));

		assertNull(Paths.getName(null));
	}

	@Test
	public void testGetParent() throws Exception
	{
		final Map<String,String> urls = new HashMap<String,String>();
		urls.put("file:///usr", "file:///usr/share/../share");
		urls.put("/usr", "/usr/share/../share/..");
		urls.put("/", "/usr/share/../share/../../");
		urls.put("file:///usr/local", "file:///usr/local/bin/../lib/../bin");

		for(Map.Entry<String,String> entry : urls.entrySet())
			assertEquals(entry.getKey(), Paths.getParent((entry.getValue())));

		assertNull(Paths.getParent(null));
	}
}
