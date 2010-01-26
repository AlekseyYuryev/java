/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
