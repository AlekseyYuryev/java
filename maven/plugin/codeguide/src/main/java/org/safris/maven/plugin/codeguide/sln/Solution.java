/* Copyright (c) 2008 Seva Safris
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.maven.plugin.codeguide.sln;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import org.safris.maven.plugin.codeguide.javaproj.JavaProject;
import org.safris.maven.plugin.dependency.GroupArtifact;

public final class Solution {
  private final LinkedHashSet<JavaProject> javaProjects = new LinkedHashSet<JavaProject>();
  private final String name;
  private final String shortName;
  private final String uuid;
  private final File dir;

  public Solution(final GroupArtifact address, final File dir) {
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

  public void addJavaProject(final JavaProject javaProject) {
    javaProjects.add(javaProject);
  }

  public Set<JavaProject> getJavaProjects() {
    return javaProjects;
  }
}
