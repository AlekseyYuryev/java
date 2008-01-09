package org.safris.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

	public static Process forkSync(String ... args) throws IOException, InterruptedException
	{
		final Process process = Runtime.getRuntime().exec(args);
		process.waitFor();
		return process;
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
