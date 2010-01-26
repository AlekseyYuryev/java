/*  Copyright 2010 Safris Technologies Inc.
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
