/*  Copyright 2008 Safris Technologies Inc.
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
