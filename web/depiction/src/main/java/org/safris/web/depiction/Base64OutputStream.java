/*  Copyright Safris Software 2006
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
