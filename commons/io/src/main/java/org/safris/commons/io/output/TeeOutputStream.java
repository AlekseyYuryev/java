package org.safris.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public class TeeOutputStream extends OutputStream
{
	private final OutputStream out1;
	private final OutputStream out2;

	public TeeOutputStream(OutputStream out1, OutputStream out2)
	{
		this.out1 = out1;
		this.out2 = out2;
	}

	public void write(int b) throws IOException
	{
		out1.write(b);
		out2.write(b);
		if((char)b == '\n')
		{
			out1.flush();
			out2.flush();
		}
	}
}
