package org.safris.xml.maven.binding;

import java.io.File;
import junit.framework.TestCase;
import org.safris.commons.util.Files;
import org.safris.commons.util.Processes;

public class InternalTest extends TestCase
{
	private static final String POM_PATH = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "xml" + File.separator;

	public static void main(String[] args) throws Exception
	{
		testInternal();
	}

	public static void testInternal() throws Exception
	{
		Processes.forkSync(System.in, System.out, System.err, GeneratorMojo.class, POM_PATH + "pom-internal.xml");
		Files.deleteAllOnExit(new File(POM_PATH + "target"));
	}
}
