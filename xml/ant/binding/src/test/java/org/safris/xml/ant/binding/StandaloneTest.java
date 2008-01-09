package org.safris.xml.ant.binding;

import java.io.File;
import java.io.IOException;
import junit.framework.TestCase;
import org.safris.commons.util.Files;
import org.safris.xml.generator.compiler.phase.write.BindingFileFilter;

public class StandaloneTest extends TestCase
{
	public static void main(String[] args) throws Exception
	{
		testStandalone();
		testStandaloneLink();
	}

	public static void testStandalone() throws IOException
	{
		GeneratorTask.main(new String[]{"src/test/resources/xml/build1.xml"});
		Files.deleteAllOnExit(new File("src/test/resources/xml/src"), new BindingFileFilter(false));
	}

	public static void testStandaloneLink() throws IOException
	{
		GeneratorTask.main(new String[]{"src/test/resources/xml/build2.xml"});
		Files.deleteAllOnExit(new File("src/test/resources/xml/src"), new BindingFileFilter(false));
	}
}
