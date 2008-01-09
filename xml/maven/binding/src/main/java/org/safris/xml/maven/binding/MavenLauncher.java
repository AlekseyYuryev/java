package org.safris.xml.maven.binding;

import org.codehaus.classworlds.Launcher;

public class MavenLauncher
{
	public static void main(String[] args)
	{
		final String M2_HOME = System.getenv("M2_HOME");
		if(M2_HOME == null)
			throw new RuntimeException("M2_HOME is not defined.");

		System.setProperty("classworlds.conf", M2_HOME + "/bin/m2.conf");
		System.setProperty("maven.home", M2_HOME);
		Launcher.main(args);
	}
}
