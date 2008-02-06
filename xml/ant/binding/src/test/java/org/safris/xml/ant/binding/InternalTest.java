package org.safris.xml.ant.binding;

import junit.framework.TestCase;
import org.junit.Test;
import org.safris.commons.exec.Processes;

public class InternalTest extends TestCase
{
	private static final String BUILD_PATH = "src/test/resources/xml/";

	public static void main(String[] args) throws Exception
	{
		testInternal();
	}

	@Test
	public static void testInternal() throws Exception
	{
		final Process process = Processes.forkSync(System.in, System.out, System.err, GeneratorTask.class, new String[]{BUILD_PATH + "build-internal.xml"});
		if(process.exitValue() != 0)
			fail();
	}
}
