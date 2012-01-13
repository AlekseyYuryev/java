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

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

public class Thumbnail
{
  public static final String PATH_PARAMETER = "p";
  public static final String SIZE_PARAMETER = "s";
  private int size = -1;
  private BufferedImage thumbnail = null;
  
  public Thumbnail(ImageInputStream imageInputStream, ImageOutputStream imageOutputStream, int size) throws IOException
  {
    this.size = size;
    // FIXME: Make a selection based on image type (jpeg, gif, etc)
    BufferedImage thumbnailImage = null;
    ImageReader reader = ImageIO.getImageReadersByFormatName("jpeg").next();
    ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
    reader.setInput(imageInputStream);
    BufferedImage image = reader.read(0);
    MediaTracker mediaTracker = new MediaTracker(new Container());
    mediaTracker.addImage(image, 0);
    try
    {
      mediaTracker.waitForID(0);
    }
    catch(InterruptedException e)
    {
      e.printStackTrace();
    }
    
    // determine thumbnail size from WIDTH and HEIGHT
    int imageWidth = image.getWidth(null);
    int imageHeight = image.getHeight(null);
    int thumbWidth = size;
    int thumbHeight = size;
    float thumbRatio = (float)thumbWidth / (float)thumbHeight;
    float imageRatio = (float)imageWidth / (float)imageHeight;
    if(thumbRatio < imageRatio)
      thumbHeight = (int)(thumbWidth / imageRatio);
    else
      thumbWidth = (int)(thumbHeight * imageRatio);
    
    double sx = (double)thumbWidth / (double)imageWidth;
    double sy = (double)thumbHeight / (double)imageHeight;
    
    thumbnailImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = thumbnailImage.createGraphics();
    AffineTransform at = AffineTransform.getScaleInstance(sx, sy);
    g.drawRenderedImage(image, at);
    
    List<BufferedImage> thumbnails = new ArrayList<BufferedImage>(1);
    thumbnails.add(thumbnailImage);
    
    IIOImage iioImage = new IIOImage(image, thumbnails, reader.getImageMetadata(0));
    
    writer.setOutput(imageOutputStream);
    JPEGImageWriteParam imageWriteParam = new JPEGImageWriteParam(Locale.US);
    imageWriteParam.setOptimizeHuffmanTables(true);
    writer.write(reader.getImageMetadata(0), iioImage, imageWriteParam);
    
    this.thumbnail = thumbnailImage;
  }
  
  public Thumbnail(ImageInputStream imageInputStream) throws IOException
  {
    // FIXME: Make a selection based on image type (jpeg, gif, etc)
    ImageReader reader = ImageIO.getImageReadersByFormatName("jpeg").next();
    reader.setInput(imageInputStream);
    if(reader.getNumImages(true) == 0)
      return;
    
    int imageIndex = reader.getMinIndex();
    if(reader.getNumThumbnails(imageIndex) > 0)
      thumbnail = reader.readThumbnail(imageIndex, 0);
    
    if(thumbnail == null)
      return;
    
    size = Math.max(thumbnail.getWidth(), thumbnail.getHeight());
  }
  
  public int getSize()
  {
    return size;
  }
  
  public BufferedImage getImage()
  {
    return thumbnail;
  }
}
