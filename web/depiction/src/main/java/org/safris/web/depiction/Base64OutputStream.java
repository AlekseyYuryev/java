package org.safris.web.depiction;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.safris.web.depiction.Base64Coder;

public class Base64OutputStream extends ByteArrayOutputStream
{
	private final BufferedImage image;
	private final JPEGImageEncoder jpegImageEncoder;
	
	public Base64OutputStream(BufferedImage image)
	{
		super();
		this.image = image;
		jpegImageEncoder = JPEGCodec.createJPEGEncoder(this);
		try
		{
			jpegImageEncoder.encode(image);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public char[] getImageByteArray()
	{
		return Base64Coder.encode(buf);
	}
	
	public byte[] getBytes()
	{
		return buf;
	}
	
	public int getSize()
	{
		return buf.length;
	}
}
