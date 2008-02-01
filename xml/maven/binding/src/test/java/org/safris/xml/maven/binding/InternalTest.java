package org.safris.xml.maven.binding;

import java.io.File;
import junit.framework.TestCase;
import org.junit.Test;
import org.safris.commons.util.Files;
import org.safris.commons.util.Processes;

public class InternalTest extends TestCase
{
	private static final String POM_PATH = "src/test/resources/xml/";

	public static void main(String[] args) throws Exception
	{
		testInternal();
	}

	@Test
	public static void testInternal() throws Exception
	{
		final Process process = Processes.forkSync(System.in, System.out, System.err, GeneratorMojo.class, new String[]{POM_PATH + "pom-internal.xml"});
		if(process.exitValue() != 0)
			fail();

		Files.deleteAllOnExit(new File(POM_PATH + "target"));
	}
}
