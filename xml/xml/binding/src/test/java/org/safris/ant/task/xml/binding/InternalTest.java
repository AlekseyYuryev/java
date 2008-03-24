package org.safris.ant.task.xml.binding;

import org.junit.Test;
import org.safris.commons.exec.Processes;

import static org.junit.Assert.*;

public class InternalTest
{
	private static final String[] DEBUG_VM_ARGS = null;
//	private static final String[] DEBUG_VM_ARGS = new String[]{"-Xdebug", "-Xnoagent", "-Djava.compiler=NONE", "-Xrunjdwp:transport=dt_socket,address=8000,server=y"};
	private static final String BUILD_PATH = "src/test/resources/xml/";

	public static void main(String[] args) throws Exception
	{
		new InternalTest().testInternal();
	}

	@Test
	public void testInternal() throws Exception
	{
		final Process process = Processes.forkSync(System.in, System.out, System.err, DEBUG_VM_ARGS, GeneratorTask.class, new String[]{BUILD_PATH + "build-internal.xml"});
		if(process.exitValue() != 0)
			fail();
	}
}
