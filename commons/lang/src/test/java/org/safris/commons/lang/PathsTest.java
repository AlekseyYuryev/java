/*  Copyright Safris Software 2008
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

package org.safris.commons.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public final class PathsTest {
  public static void main(final String[] args) throws Exception {
    final PathsTest pathsTest = new PathsTest();
    pathsTest.testGetName();
    pathsTest.testGetParent();
  }

  @Test
  public void testGetName() throws Exception {
    final Map<String,String> paths = new HashMap<String,String>();
    paths.put("share", "file:///usr/share/../share");
    paths.put("lib", "file:///usr/share/../share/../lib");
    paths.put("var", "/usr/share/../share/../lib/../../var");
    paths.put("var", "/usr/share/../share/../lib/../../var/");
    paths.put("resolv.conf", "/etc/resolv.conf");
    paths.put("name", "name");

    for (final Map.Entry<String,String> entry : paths.entrySet())
      assertEquals(entry.getKey(), Paths.getName(entry.getValue()));

    assertNull(Paths.getName(null));
  }

  @Test
  public void testGetParent() throws Exception {
    final Map<String,String> urls = new HashMap<String,String>();
    urls.put("file:///usr", "file:///usr/share/../share");
    urls.put("/usr", "/usr/share/../share/..");
    urls.put("/", "/usr/share/../share/../../");
    urls.put("file:///usr/local", "file:///usr/local/bin/../lib/../bin");

    for (final Map.Entry<String,String> entry : urls.entrySet())
      assertEquals(entry.getKey(), Paths.getParent((entry.getValue())));

    assertNull(Paths.getParent(null));
  }
}