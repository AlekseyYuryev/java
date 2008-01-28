package org.safris.xml.ant.binding;

import java.io.File;
import junit.framework.TestCase;
import org.safris.commons.util.Processes;

public class InternalTest extends TestCase
{
	private static final String BUILD_PATH = "src/test/resources/xml/";

	public static void main(String[] args) throws Exception
	{
		testInternal();
	}

	public static void testInternal() throws Exception
	{
		final Process process = Processes.forkSync(System.in, System.out, System.err, GeneratorTask.class, BUILD_PATH + "build-internal.xml");
		if(process.exitValue() != 0)
			fail();
	}
}
