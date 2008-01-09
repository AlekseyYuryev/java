package org.safris.web.depiction;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import org.safris.web.depiction.PageCache;

public class PageCache
{
	private static final Map<String,PageCache> cache = new Hashtable<String,PageCache>();
	private final Calendar calendar;
	private final long timeCached;
	private final String page;

	public PageCache(Calendar calendar, String page)
	{
		this.calendar = calendar;
		this.page = page;
		this.timeCached = System.currentTimeMillis();
	}

	public static PageCache getCache(String path)
	{
		return cache.get(path);
	}

	public static boolean invalidateCache(String path)
	{
		return cache.remove(path) != null;
	}

	public static void setCache(String path, PageCache value)
	{
		cache.put(path, value);
	}

	public long getTimeCached()
	{
		return timeCached;
	}

	public boolean expired()
	{
		return calendar != null && calendar.before(new Date(System.currentTimeMillis()));
	}

	public String getPage()
	{
		return page;
	}
}
