package org.safris.web.depiction.tag;

import java.io.File;
import javax.servlet.jsp.tagext.TagSupport;
import org.safris.web.depiction.FileFind;
import org.safris.web.depiction.ImageFile;
import org.safris.web.depiction.tag.bean.ListBean;
import org.safris.web.depiction.util.Cache;

public class Find extends TagSupport
{
	private static final Cache<String,ListBean<File>> cache = new Cache<String,ListBean<File>>();
	private String path = null;
	private String name = null;
	private String var = null;

	public void setPath(Object path)
	{
		if(path == null)
		{
			this.path = null;
			return;
		}

		this.path = path.toString();
	}

	public void setName(Object name)
	{
		if(name == null)
		{
			this.name = null;
			return;
		}

		this.name = name.toString();
	}

	public void setVar(Object var)
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
		if(var == null)
			return SKIP_BODY;

		File dir = new File(path);
		String key = dir.getPath() + name;
		Cache<String,ListBean<File>>.Entry entry = cache.get(key);
		// NOTE: This condition happens when there is a new file in the directory since
		// NOTE: the cache time, or if a modification to the directory has been made
		if(entry != null && dir.lastModified() < entry.getCreated().getTime())
		{
			ListBean<File> files = entry.getValue();
			pageContext.setAttribute(var, files);
		}
		else
		{
			ListBean<File> files = new ListBean<File>(FileFind.ls(dir, "(?!" + ImageFile.THUMB_PREFIX + ")" + name));
			pageContext.setAttribute(var, files);
			cache.put(key, files);
		}

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag()
	{
		pageContext.removeAttribute(var);
		return EVAL_BODY_INCLUDE;
	}
}
