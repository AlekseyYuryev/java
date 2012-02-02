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

package org.safris.maven.plugin.xml.binding;

import org.apache.maven.project.MavenProject;
import org.safris.commons.util.Resolver;

public class MavenPropertyResolver implements Resolver<String> {
  private final MavenProject project;

  public MavenPropertyResolver(final MavenProject project) {
    this.project = project;
  }

  public String resolve(final String string) {
    if (string == null)
      return null;

    final int start = string.indexOf("${");
    if (start == -1)
      return string;

    final int end = string.indexOf("}", start + 2);
    if (end == -1)
      return string;

    final String resolved = getProperty(string.substring(start + 2, end));
    if (resolved == null)
      return string.substring(0, end + 1) + resolve(string.substring(end + 1, string.length()));

    return string.substring(0, start) + resolved + resolve(string.substring(end + 1, string.length()));
  }

  private String getProperty(final String string) {
    if ("basedir".equals(string))
      return project.getBasedir().getAbsolutePath();

    if ("project.build.directory".equals(string))
      return project.getBuild().getDirectory();

    if ("project.build.outputDirectory".equals(string))
      return project.getBuild().getOutputDirectory();

    if ("project.build.sourceDirectory".equals(string))
      return project.getBuild().getSourceDirectory();

    if ("project.build.testSourceDirectory".equals(string))
      return project.getBuild().getTestSourceDirectory();

    return null;
  }
}
