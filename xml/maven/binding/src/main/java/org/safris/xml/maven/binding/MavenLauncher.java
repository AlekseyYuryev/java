package org.safris.xml.maven.binding;

import java.io.File;
import java.util.StringTokenizer;
import org.codehaus.classworlds.Launcher;

public final class MavenLauncher
{
	public static void main(String[] args)
	{
		final String M2_HOME = System.getenv("M2_HOME");
		if(M2_HOME == null)
			throw new RuntimeException("M2_HOME is not defined.");

		final File m2 = new File(M2_HOME, "bin/m2.conf");
		if(!m2.exists())
		{
			System.err.println("Cannot find $M2_HOME/bin/m2.conf: " + m2.getAbsolutePath());
			System.exit(1);
		}

		final String MAVEN_OPTS = System.getenv("MAVEN_OPTS");
		final StringTokenizer tokenizer = new StringTokenizer(MAVEN_OPTS, "-D");
		while(tokenizer.hasMoreTokens())
		{
			final String token = tokenizer.nextToken();
			if(token == null)
				continue;

			final String[] values = token.split("=");
			if(values.length == 2)
				System.setProperty(values[0], values[1]);
		}

		System.setProperty("maven.home", M2_HOME);
		System.setProperty("classworlds.conf", m2.getAbsolutePath());
		Launcher.main(args);
	}

	private MavenLauncher()
	{
	}
}
