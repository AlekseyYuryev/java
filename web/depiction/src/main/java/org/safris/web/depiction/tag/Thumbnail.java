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

package org.safris.web.depiction.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.safris.web.depiction.ImageFile;
import org.safris.web.depiction.Metadata;
import org.safris.web.depiction.ThumbnailException;
import org.safris.web.depiction.util.Cache;

public class Thumbnail extends TagSupport
{
  private static final Cache<String,String> cache = new Cache<String,String>();
  private int size = 100;
  private Metadata metadata = null;
  private String baseDir = null;
  private String baseUrl = null;
  private String imgClass = null;
  
  public void setSize(Object size)
  {
    if(size == null)
    {
      size = 100;
      return;
    }
    
    this.size = Integer.parseInt(size.toString());
  }
  
  public void setBaseDir(Object baseDir)
  {
    if(baseDir == null)
    {
      baseDir = null;
      return;
    }
    
    this.baseDir = baseDir.toString();
  }
  
  public void setBaseUrl(Object baseUrl)
  {
    if(baseUrl == null)
    {
      baseUrl = null;
      return;
    }
    
    this.baseUrl = baseUrl.toString();
  }
  
  public void setImgClass(Object imgClass)
  {
    if(imgClass == null)
    {
      imgClass = null;
      return;
    }
    
    this.imgClass = imgClass.toString();
  }
  
  public void setMetadata(Object metadata)
  {
    if(metadata == null)
    {
      metadata = null;
      return;
    }
    
    this.metadata = (Metadata)metadata;
  }
  
  public int doStartTag() throws JspException
  {
    if(metadata == null)
      return SKIP_BODY;
    
    String key = size + metadata.getFile().getPath() + baseDir + baseUrl + imgClass;
    Cache<String,String>.Entry entry = cache.get(key);
    String text = null;
    if(entry != null)
    {
      text = entry.getValue();
    }
    else
    {
      // FIXME: Put this in the right place!
      try
      {
        metadata.getThumbnail(size);
      }
      catch(ThumbnailException e)
      {
        throw new JspException(e);
      }
      
      StringBuffer buffer = new StringBuffer();
      buffer.append("<img ");
      if(imgClass != null)
        buffer.append("class=\"").append(imgClass).append("\" ");
      
      buffer.append("src=\"");
      if(baseUrl != null)
      {
        if(!baseUrl.startsWith("http://"))
          buffer.append("http://");
        
        buffer.append(baseUrl);
      }
      
      String path = ImageFile.getThumbnailFile(metadata.getFile()).getPath();
      if(baseDir != null && path.startsWith(baseDir))
        path = path.substring(baseDir.length(), path.length());
      
      buffer.append(path).append("\">");
      text = buffer.toString();
      cache.put(key, text);
    }
    
    JspWriter out = pageContext.getOut();
    try
    {
      out.write(text, 0, text.length());
    }
    catch(IOException e)
    {
      throw new JspException(e);
    }
    
    return SKIP_BODY;
  }
}
