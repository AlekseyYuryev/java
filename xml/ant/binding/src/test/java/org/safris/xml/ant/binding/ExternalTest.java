package org.safris.xml.ant.binding;

import java.io.File;
import junit.framework.TestCase;
import org.safris.commons.util.Processes;
import org.safris.xml.ant.binding.GeneratorTask;

public class ExternalTest extends TestCase
{
	private static final String BUILD_PATH = "src/test/resources/xml" + File.separator;

	public static void main(String[] args) throws Exception
	{
		testExternal();
	}

	public static void testExternal() throws Exception
	{
		final Process process = Processes.forkSync(System.in, System.out, System.err, GeneratorTask.class, BUILD_PATH + "build-external.xml");
		if(process.exitValue() != 0)
			fail();
	}
}
