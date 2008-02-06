package org.safris.xml.ant.binding;

import junit.framework.TestCase;
import org.junit.Test;
import org.safris.commons.exec.Processes;

public class ExternalTest extends TestCase
{
	private static final String BUILD_PATH = "src/test/resources/xml/";

	public static void main(String[] args) throws Exception
	{
		testExternal();
	}

	@Test
	public static void testExternal() throws Exception
	{
		final Process process = Processes.forkSync(System.in, System.out, System.err, GeneratorTask.class, new String[]{BUILD_PATH + "build-external.xml"});
		if(process.exitValue() != 0)
			fail();
	}
}
