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

package org.safris.maven.plugin.codeguide.sln;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import org.safris.maven.plugin.codeguide.javaproj.JavaProject;
import org.safris.maven.plugin.dependency.GroupArtifact;

public class Solution {
  private final LinkedHashSet<JavaProject> javaProjects = new LinkedHashSet<JavaProject>();
  private final String name;
  private final String shortName;
  private final String uuid;
  private final File dir;

  public Solution(GroupArtifact address, File dir) {
    this.name = address.getGroupId() + ":" + address.getArtifactId();
    this.shortName = address.getArtifactId();
    // FIXME: Figure out what the fuck is up with this crap!
    this.uuid = "85B0C3CB-4F8C-465B-A944-62ABB0F7F898";
    this.dir = dir;
  }

  public String getName() {
    return name;
  }

  public String getShortName() {
    return shortName;
  }

  public String getUUID() {
    return uuid;
  }

  public File getDir() {
    return dir;
  }

  public void addJavaProject(JavaProject javaProject) {
    javaProjects.add(javaProject);
  }

  public Set<JavaProject> getJavaProjects() {
    return javaProjects;
  }
}
