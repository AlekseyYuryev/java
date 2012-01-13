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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import org.safris.web.depiction.Comment;
import org.safris.web.depiction.CommentException;
import org.safris.web.depiction.ImageException;
import org.safris.web.depiction.ImageFile;
import org.safris.web.depiction.ImageServlet;
import org.safris.web.depiction.Metadata;
import org.safris.web.depiction.Thumbnail;
import org.safris.web.depiction.ThumbnailException;

public class ImageFile
{
  public static String THUMB_PREFIX = ".thumb.";
  private final File file;
  private final Metadata metadata;
  private int thumbnailSize = -1;
  private int width = -1;
  private int height = -1;
  private float ratio = -1;
  private BufferedImage thumbnailImage = null;
  private List<Comment> comment = null;
  
  public static boolean isImage(File file)
  {
    String name = file.getName();
    if(name.startsWith(THUMB_PREFIX))
    {
      if(!getImageFile(file).exists())
        file.delete();
      
      return false;
    }
    
    if(name.endsWith(".jpg"))
      return true;
    
    if(name.endsWith(".JPG"))
      return true;
    
    return false;
  }
  
  public static File getThumbnailFile(File image)
  {
    return new File(image.getParent() + "/" + THUMB_PREFIX + image.getName());
  }
  
  public static File getImageFile(File thumbnail)
  {
    String name = thumbnail.getName();
    return new File(thumbnail.getParent() + "/" + name.substring(7, name.length()));
  }
  
  public static File getLocalFile(File absolute)
  {
    return new File(absolute.getPath().substring(ImageServlet.getPictureDirectory().length(), absolute.getPath().length()));
  }
  
  public ImageFile(File file, int thumbnailSize)
  {
    this.file = file;
    this.thumbnailSize = thumbnailSize;
    metadata = new Metadata(file);
  }
  
  public ImageFile(File file)
  {
    this.file = file;
    metadata = new Metadata(file);
  }
  
  private void initImage() throws ImageException
  {
    if(width != -1)
      return;
    
    ImageReader reader = ImageIO.getImageReadersByFormatName("jpeg").next();
    try
    {
      reader.setInput(new FileImageInputStream(file));
      if(reader.getNumImages(true) == 0)
        return;
      
      width = reader.getWidth(0);
      height = reader.getHeight(0);
      ratio = (float)height / (float)width;
    }
    catch(IOException e)
    {
      throw new ImageException(e);
    }
  }
  
  private void initThumbnail() throws ThumbnailException
  {
    if(thumbnailImage != null)
      return;
    
    Thumbnail thumbnail = null;
    if(thumbnailSize != -1)
    {
      thumbnail = metadata.getThumbnail(thumbnailSize);
    }
    else
    {
      thumbnail = metadata.getThumbnail();
      if(thumbnail == null)
        return;
      
      thumbnailSize = thumbnail.getSize();
    }
    
    if(thumbnail == null)
      return;
    
    thumbnailImage = thumbnail.getImage();
  }
  
  private void initComment() throws CommentException
  {
    if(comment != null)
      return;
    
    comment = metadata.getComment();
  }
  
  public String getPath()
  {
    return file.getPath();
  }
  
  public int getWidth() throws ImageException
  {
    initImage();
    return width;
  }
  
  public int getHeight() throws ImageException
  {
    initImage();
    return height;
  }
  
  public float getRatio() throws ImageException
  {
    initImage();
    return ratio;
  }
  
  public int getThmbnailSize() throws ThumbnailException
  {
    initThumbnail();
    return thumbnailSize;
  }
  
  public BufferedImage getThumbnail() throws ThumbnailException
  {
    initThumbnail();
    return thumbnailImage;
  }
  
  public List<Comment> getComments() throws CommentException
  {
    initComment();
    return comment;
  }
  
  public void addComment(Comment comment) throws CommentException
  {
    metadata.addComment(comment);
  }
  
  public void deleteComment(Comment comment) throws CommentException
  {
    metadata.deleteComment(comment);
  }
  
  public String getLink()
  {
    return "<a href=\"image.jsp?i=" + URLEncoder.encode(ImageFile.getLocalFile(file).getPath()) + "\"><img class=\"image-cell\" src=\"" + ImageServlet.getPictureURL() + ImageFile.getLocalFile(ImageFile.getThumbnailFile(file)).getPath() + "\" border=\"1\"></a>";
  }
}
