package org.safris.commons.xml.binding;

import java.io.ByteArrayOutputStream;

public class HexBinary
{
	public static HexBinary parseHexBinary(String string)
	{
		if(string == null)
			return null;

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for(int i = 0; i < string.length(); i += 2)
		{
			char c1 = string.charAt(i);
			if(i + 1 >= string.length())
				throw new IllegalArgumentException();

			char c2 = string.charAt(i + 1);
			byte b = 0;
			if(c1 >= '0' && c1 <= '9')
				b += ((c1 - '0') * 16);
			else if(c1 >= 'a' && c1 <= 'f')
				b += ((c1 - 'a' + 10) * 16);
			else if(c1 >= 'A' && c1 <= 'F')
				b += ((c1 - 'A' + 10) * 16);
			else
				throw new IllegalArgumentException("bad characted in hex string");

			if(c2 >= '0' && c2 <= '9')
				b += (c2 - '0');
			else if(c2 >= 'a' && c2 <= 'f')
				b += (c2 - 'a' + 10);
			else if(c2 >= 'A' && c2 <= 'F')
				b += (c2 - 'A' + 10);
			else
				throw new IllegalArgumentException("bad characted in hex string");

			out.write(b);
		}

		return new HexBinary(out.toByteArray());
	}

	private static char convertDigit(int value)
	{
		value &= 0x0f;
		if(value >= 10)
			return ((char)(value - 10 + 'a'));
		else
			return ((char)(value + '0'));
	}

	private final byte[] bytes;
	private final String encoded;

	public HexBinary(byte[] bytes)
	{
		this.bytes = bytes;
		final StringBuffer buffer = new StringBuffer(bytes.length * 2);
		for(int i = 0; i < bytes.length; i++)
		{
			buffer.append(convertDigit(bytes[i] >> 4));
			buffer.append(convertDigit(bytes[i] & 0x0f));
		}

		this.encoded = buffer.toString();
	}

	public String toString()
	{
		return encoded;
	}
}
