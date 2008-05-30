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
