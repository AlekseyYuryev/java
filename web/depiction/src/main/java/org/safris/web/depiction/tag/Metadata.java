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
