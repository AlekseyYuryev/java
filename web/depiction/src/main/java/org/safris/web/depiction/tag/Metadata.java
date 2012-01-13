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

import java.io.File;
import javax.servlet.jsp.tagext.TagSupport;
import org.safris.web.depiction.util.Cache;

public class Metadata extends TagSupport
{
  private static final Cache<String,org.safris.web.depiction.Metadata> cache = new Cache<String,org.safris.web.depiction.Metadata>();
  private File file = null;
  private String var = null;

  public void setFile(String file)
  {
    if(file == null)
    {
      this.file = null;
      return;
    }

    this.file = new File(file);
  }

  public void setVar(String var)
  {
    if(var == null)
    {
      this.var = null;
      return;
    }

    this.var = var.toString();
  }

  public int doStartTag()
  {
    if(file == null || !file.exists() || var == null)
      return SKIP_BODY;

    String key = file.getPath();
    Cache<String,org.safris.web.depiction.Metadata>.Entry entry = cache.get(key);
    // NOTE: This condition happens when there is a modification to the file
    if(entry != null && file.lastModified() < entry.getCreated().getTime())
    {
      org.safris.web.depiction.Metadata metadata = entry.getValue();
      pageContext.setAttribute(var, metadata);
    }
    else
    {
      org.safris.web.depiction.Metadata metadata = new org.safris.web.depiction.Metadata(file);
      if(metadata != null)
      {
        pageContext.setAttribute(var, metadata);
        cache.put(key, metadata);
      }
    }

    return EVAL_BODY_INCLUDE;
  }

  public int doEndTag()
  {
    pageContext.removeAttribute(var);
    return EVAL_BODY_INCLUDE;
  }
}
