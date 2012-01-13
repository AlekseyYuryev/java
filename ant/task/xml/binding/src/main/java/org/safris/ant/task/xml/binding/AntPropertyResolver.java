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

package org.safris.ant.task.xml.binding;

import org.apache.tools.ant.Project;
import org.safris.xml.toolkit.binding.PropertyResolver;

public class AntPropertyResolver implements PropertyResolver {
  private final Project project;

  public AntPropertyResolver(Project project) {
    this.project = project;
  }

  public String resolve(String string) {
    if (string == null)
      return null;

    final int start = string.indexOf("${");
    if (start == -1)
      return string;

    final int end = string.indexOf("}", start + 2);
    if (end == -1)
      return string;

    if (project == null)
      return string;

    final String resolved = project.getProperty(string.substring(start + 2, end));
    if (resolved == null)
      return string.substring(0, end + 1) + resolve(string.substring(end + 1, string.length()));

    return string.substring(0, start) + resolved + resolve(string.substring(end + 1, string.length()));
  }
}
