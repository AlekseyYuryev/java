package org.safris.xml.ant.binding;

import org.junit.Test;
import org.safris.commons.exec.Processes;

import static org.junit.Assert.*;

public class InternalTest
{
	private static final String BUILD_PATH = "src/test/resources/xml/";

	public static void main(String[] args) throws Exception
	{
		new InternalTest().testInternal();
	}

	@Test
	public void testInternal() throws Exception
	{
		final Process process = Processes.forkSync(System.in, System.out, System.err, GeneratorTask.class, new String[]{BUILD_PATH + "build-internal.xml"});
		if(process.exitValue() != 0)
			fail();
	}
}
