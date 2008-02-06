package org.safris.commons.io.output;

import java.io.IOException;
import java.io.InputStream;

public class NonBlockingInputStream extends InputStream
{
	private static int log2(int value)
	{
		return (int)Math.round(Math.log(value) / Math.log(2));
	}

	private final InputStream in;
	private final byte[] buffer;
	private final int tempBufferSize;
	private volatile int writeAhead;
	private volatile int writeIndex = 0;
	private volatile int readIndex = 0;
	private IOException ioException;
	private boolean eof = false;
	private int lost = 0;

	public NonBlockingInputStream(InputStream in, int bufferSize)
	{
		if(bufferSize == 0)
			throw new IllegalArgumentException("bufferSize cannot be 0");

		this.in = in;
		this.buffer = new byte[bufferSize];
		this.tempBufferSize = log2(bufferSize);
		this.writeAhead = bufferSize;
		new ReaderThread().start();
	}

	public int read() throws IOException
	{
		if(ioException != null)
			throw ioException;

		if(eof)
			return -1;

		if(writeAhead == buffer.length)
			return 0;

		final int value;
		synchronized(buffer)
		{
			value = buffer[readIndex];
			if(++readIndex == buffer.length)
				readIndex = 0;

			if(++writeAhead == buffer.length)
				writeAhead = buffer.length;
		}

		return value;
	}

	public int getLostBytesCount()
	{
		return lost;
	}

	private class ReaderThread extends Thread
	{
		public ReaderThread()
		{
			setPriority(Thread.MAX_PRIORITY);
		}

		public void run()
		{
			int length = 0;
			final byte[] bytes = new byte[tempBufferSize];
			try
			{
				while((length = in.read(bytes)) != -1)
				{
					writeAhead -= length;

					if(buffer.length <= writeIndex + length)
					{
						System.arraycopy(bytes, 0, buffer, writeIndex, buffer.length - writeIndex);
						System.arraycopy(bytes, buffer.length - writeIndex, buffer, 0, writeIndex = bytes.length - buffer.length + writeIndex);
					}
					else
					{
						System.arraycopy(bytes, 0, buffer, writeIndex, length);
						writeIndex += length;
					}

					if(writeAhead <= 0)
					{
						lost += -1 * writeAhead;
						writeAhead = 0;
						readIndex = writeIndex;
					}
				}

				eof = true;
			}
			catch(IOException e)
			{
				ioException = e;
			}
		}
	}
}
