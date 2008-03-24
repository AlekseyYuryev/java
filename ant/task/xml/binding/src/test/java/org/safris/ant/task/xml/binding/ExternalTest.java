package org.safris.ant.task.xml.binding;

import org.junit.Test;
import org.safris.commons.exec.Processes;

import static org.junit.Assert.*;

public class ExternalTest
{
	private static final String[] DEBUG_VM_ARGS = null;
//	private static final String[] DEBUG_VM_ARGS = new String[]{"-Xdebug", "-Xnoagent", "-Djava.compiler=NONE", "-Xrunjdwp:transport=dt_socket,address=8000,server=y"};
	private static final String BUILD_PATH = "src/test/resources/xml/";

	public static void main(String[] args) throws Exception
	{
		new ExternalTest().testExternal();
	}

	@Test
	public void testExternal() throws Exception
	{
		final Process process = Processes.forkSync(System.in, System.out, System.err, DEBUG_VM_ARGS, GeneratorTask.class, new String[]{BUILD_PATH + "build-external.xml"});
		if(process.exitValue() != 0)
			fail();
	}
}
