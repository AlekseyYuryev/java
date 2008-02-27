package org.safris.commons.exec;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.safris.commons.io.Streams;
import org.safris.commons.lang.ClassLoaders;

public final class Processes
{
	public static Process forkAsync(InputStream stdin, OutputStream stdout, OutputStream stderr, String ... args) throws IOException
	{
		final Process process = Runtime.getRuntime().exec(args);
		OutputStream teeStdin = null;
		if(stdin != null)
		{
			final OutputStream processStdin = process.getOutputStream();
			teeStdin = Streams.tee(processStdin, stdin, stdout);
		}

		InputStream teeStdout = null;
		if(stdout != null)
		{
			final InputStream processStdout = process.getInputStream();
			teeStdout = Streams.tee(processStdout, stdout);
		}

		InputStream teeStderr = null;
		if(stderr != null)
		{
			final InputStream processStderr = process.getErrorStream();
			teeStderr = Streams.tee(processStderr, stderr);
		}

		return new PipedProcess(process, teeStdin, teeStdout, teeStderr);
	}

	public static Process forkSync(InputStream stdin, OutputStream stdout, OutputStream stderr, String ... args) throws IOException, InterruptedException
	{
		final Process process = forkAsync(stdin, stdout, stderr, args);
		process.waitFor();
		return process;
	}

	public static Process forkAsync(InputStream stdin, OutputStream stdout, OutputStream stderr, Map<String,String> props, Class clazz, String ... args) throws IOException, InterruptedException
	{
		final Process process = forkAsync(stdin, stdout, stderr, createJavaCommand(null, getSystemProperties(), clazz, args));
		return process;
	}

	public static Process forkAsync(InputStream stdin, OutputStream stdout, OutputStream stderr, String[] vmArgs, Map<String,String> props, Class clazz, String ... args) throws IOException, InterruptedException
	{
		final Process process = forkAsync(stdin, stdout, stderr, createJavaCommand(vmArgs, getSystemProperties(), clazz, args));
		return process;
	}

	public static Process forkAsync(InputStream stdin, OutputStream stdout, OutputStream stderr, String[] vmArgs, Class clazz, String ... args) throws IOException, InterruptedException
	{
		return forkAsync(stdin, stdout, stderr, vmArgs, getSystemProperties(), clazz, args);
	}

	public static Process forkAsync(InputStream stdin, OutputStream stdout, OutputStream stderr, Class clazz, String ... args) throws IOException, InterruptedException
	{
		return forkAsync(stdin, stdout, stderr, null, getSystemProperties(), clazz, args);
	}

	public static Process forkSync(InputStream stdin, OutputStream stdout, OutputStream stderr, String[] vmArgs, Map<String,String> props, Class clazz, String ... args) throws IOException, InterruptedException
	{
		final Process process = forkAsync(stdin, stdout, stderr, createJavaCommand(vmArgs, getSystemProperties(), clazz, args));
		process.waitFor();
		return process;
	}

	public static Process forkSync(InputStream stdin, OutputStream stdout, OutputStream stderr, Map<String,String> props, Class clazz, String ... args) throws IOException, InterruptedException
	{
		final Process process = forkAsync(stdin, stdout, stderr, createJavaCommand(null, getSystemProperties(), clazz, args));
		process.waitFor();
		return process;
	}

	public static Process forkSync(InputStream stdin, OutputStream stdout, OutputStream stderr, String[] vmArgs, Class clazz, String ... args) throws IOException, InterruptedException
	{
		return forkSync(stdin, stdout, stderr, vmArgs, getSystemProperties(), clazz, args);
	}

	public static Process forkSync(InputStream stdin, OutputStream stdout, OutputStream stderr, Class clazz, String ... args) throws IOException, InterruptedException
	{
		return forkSync(stdin, stdout, stderr, getSystemProperties(), clazz, args);
	}

	private static String[] createJavaCommand(String[] vmArgs, Map<String,String> props, Class clazz, String ... args)
	{
		final URL[] classpathURLs = ClassLoaders.getClassPath();
		final StringBuffer classpath = new StringBuffer();
		if(classpathURLs != null && classpathURLs.length != 0)
			for(URL url : classpathURLs)
				classpath.append(File.pathSeparatorChar).append(url.getFile());

		final String[] options = new String[(args != null ? args.length : 0) + (vmArgs != null ? vmArgs.length : 0) + (props != null ? props.size() : 0) + 4];
		int i = -1;
		options[++i] = "java";
		if(vmArgs != null && vmArgs.length != 0)
			for(String vmArg : vmArgs)
				options[++i] = vmArg;

		if(props != null && props.size() != 0)
			for(Map.Entry<String,String> property : props.entrySet())
				options[++i] = "-D" + property.getKey() + "=" + property.getValue();

		options[++i] = "-cp";
		options[++i] = classpath.length() != 0 ? classpath.substring(1) : "";
		options[++i] = clazz.getName();
		System.arraycopy(args, 0, options, ++i, args.length);

		return options;
	}

	private static Map<String,String> getSystemProperties()
	{
		if(System.getProperties().size() == 0)
			return new HashMap<String,String>(0);

		final Map<String,String> properties = new HashMap<String,String>(7);
		for(Map.Entry property : System.getProperties().entrySet())
		{
			final String key = (String)property.getKey();
			final String value = (String)property.getValue();
			if(value.trim().length() != 0 && !value.contains(" ") && !key.contains(" "))
				properties.put(key, value.trim());
		}

		return properties;
	}

	private static final class PipedProcess extends Process
	{
		private final Process process;
		private final OutputStream stdin;
		private final InputStream stdout;
		private final InputStream stderr;

		public PipedProcess(Process process, OutputStream stdin, InputStream stdout, InputStream stderr)
		{
			this.process = process;
			this.stdin = stdin;
			this.stdout = stdout;
			this.stderr = stderr;
		}

		public OutputStream getOutputStream()
		{
			if(stdin != null)
				return stdin;
			else
				return process.getOutputStream();
		}

		public InputStream getInputStream()
		{
			if(stdout != null)
				return stdout;
			else
				return process.getInputStream();
		}

		public InputStream getErrorStream()
		{
			if(stderr != null)
				return stderr;
			else
				return process.getErrorStream();
		}

		public int waitFor() throws InterruptedException
		{
			return process.waitFor();
		}

		public int exitValue()
		{
			return process.exitValue();
		}

		public void destroy()
		{
			process.destroy();
		}
	}

	private Processes()
	{
	}
}
