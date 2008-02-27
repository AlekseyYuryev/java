package org.safris.xml.maven.binding;

import java.io.File;
import org.junit.Test;
import org.safris.commons.exec.Processes;
import org.safris.commons.io.Files;

import static org.junit.Assert.*;

public class ExternalTest
{
	private static final String[] DEBUG_VM_ARGS = null;
//	private static final String[] DEBUG_VM_ARGS = new String[]{"-Xdebug", "-Xnoagent", "-Djava.compiler=NONE", "-Xrunjdwp:transport=dt_socket,address=8000,server=y"};
	private static final String POM_PATH = "src/test/resources/xml/";

	public static void main(String[] args) throws Exception
	{
		new ExternalTest().testExternal();
	}

	@Test
	public void testExternal() throws Exception
	{
		final Process process = Processes.forkSync(System.in, System.out, System.err, DEBUG_VM_ARGS, GeneratorMojo.class, new String[]{POM_PATH + "pom-external.xml"});
		if(process.exitValue() != 0)
			fail();

		Files.deleteAllOnExit(new File(POM_PATH + "target"));
	}
}
