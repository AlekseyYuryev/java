package org.safris.commons.xml.binding;

import java.io.IOException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Binary
{
	public static Base64Binary parseBase64Binary(String string)
	{
		if(string == null)
			return null;

		byte[] bytes = null;
		try
		{
			bytes = new BASE64Decoder().decodeBuffer(string);
		}
		catch(IOException e)
		{
			IllegalArgumentException illegalArgumentException = new IllegalArgumentException("unable to decode");
			illegalArgumentException.setStackTrace(e.getStackTrace());
			throw illegalArgumentException;
		}

		return new Base64Binary(bytes);
	}

	private final byte[] bytes;
	private final String encoded;

	public Base64Binary(byte[] bytes)
	{
		this.bytes = bytes;
		this.encoded = new BASE64Encoder().encodeBuffer(bytes);
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof Base64Binary))
			return false;

		Base64Binary equals = (Base64Binary)obj;
		return encoded.equals(equals.encoded);
	}

	public String toString()
	{
		return encoded;
	}
}
