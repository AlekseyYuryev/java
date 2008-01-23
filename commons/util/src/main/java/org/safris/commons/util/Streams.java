package org.safris.commons.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.safris.commons.io.TeeOutputStream;

public final class Streams
{
    /**
     * Reads all bytes from the input stream and returns the resulting buffer
	 * array. This method blocks until all contents have been read, end of
	 * file is detected, or an exception is thrown.
     *
     * <p> If the InputStream <code>in</code> is <code>null</code>, then null
	 * is returned; otherwise, a byte[] of at least size 0 will be returned.
     *
     * @param      in   the input stream to read from.
     * @return     the byte[] containing all bytes that were read from the
	 *             InputStream <code>in</code> until an end of file is detected.
     * @exception  IOException  If the first byte cannot be read for any reason
     * other than the end of the file, if the input stream has been closed, or
     * if some other I/O error occurs.
     * @see        java.io.InputStream#read(byte[])
     */
	public static byte[] getBytes(InputStream in) throws IOException
	{
		if(in == null)
			return null;

		final int BUFFER_SIZE = 1024;
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream(BUFFER_SIZE);
		final byte[] data = new byte[BUFFER_SIZE];
		int length = -1;
		while((length = in.read(data)) != -1)
			buffer.write(data, 0, length);

		return buffer.toByteArray();
    }

	public static OutputStream tee(final OutputStream src, final InputStream snkIn, final OutputStream snkOut) throws IOException
	{
		pipe(snkIn, src, false);
		return new TeeOutputStream(src, snkOut);
	}

	public static InputStream tee(final InputStream src, final OutputStream snk) throws IOException
	{
		return pipe(src, snk, true);
	}

	public static void pipe(final OutputStream src, final InputStream snk) throws IOException
	{
		pipe(snk, src, false);
	}

	public static void pipe(final InputStream src, final OutputStream snk) throws IOException
	{
		pipe(src, snk, false);
	}

	private static InputStream pipe(final InputStream src, final OutputStream snk, final boolean tee) throws IOException
	{
		final PipedOutputStream pipedOut;
		final InputStream pipedIn;
		if(tee)
		{
			pipedOut = new PipedOutputStream();
			pipedIn = new PipedInputStream(pipedOut);
		}
		else
		{
			pipedOut = null;
			pipedIn = null;
		}

		final int BUFFER_SIZE = 1024;
		new Thread("pipe")
		{
			public void run()
			{
				int length = 0;
				final byte[] bytes = new byte[BUFFER_SIZE];
				try
				{
					while((length = src.read(bytes)) != -1)
					{
						if(tee)
						{
							pipedOut.write(bytes, 0, length);
							pipedOut.flush();
						}
						snk.write(bytes, 0, length);
						snk.flush();
					}
				}
				catch(IOException e)
				{
					if("Write end dead".equals(e.getMessage()) || "Broken pipe".equals(e.getMessage()) || "Pipe broken".equals(e.getMessage()) || "Stream closed".equals(e.getMessage()))
						return;

					e.printStackTrace();
				}
			}
		}.start();

		return pipedIn;
	}

	private Streams()
	{
	}
}
