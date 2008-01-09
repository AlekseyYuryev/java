package org.safris.xml.maven.binding;

import java.io.File;
import java.io.IOException;
import junit.framework.TestCase;
import org.apache.maven.plugin.MojoFailureException;
import org.safris.commons.util.Files;
import org.safris.xml.generator.compiler.phase.write.BindingFileFilter;

public class StandaloneTest extends TestCase
{
	public static void main(String[] args) throws Exception
	{
		testStandalone();
		testStandaloneLink();
	}

	public static void testStandalone() throws IOException, MojoFailureException
	{
		GeneratorMojo.main(new String[]{"src/test/resources/xml/pom1.xml"});
		Files.deleteAllOnExit(new File("src/test/resources/xml/src"), new BindingFileFilter(false));
	}

	public static void testStandaloneLink() throws IOException, MojoFailureException
	{
		GeneratorMojo.main(new String[]{"src/test/resources/xml/pom2.xml"});
		Files.deleteAllOnExit(new File("src/test/resources/xml/src"), new BindingFileFilter(false));
	}
}
