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
