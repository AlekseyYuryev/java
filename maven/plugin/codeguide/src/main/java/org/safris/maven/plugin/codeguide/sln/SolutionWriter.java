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
import java.io.FileOutputStream;
import java.io.IOException;

import org.safris.commons.io.Files;
import org.safris.maven.plugin.codeguide.javaproj.JavaProject;

public final class SolutionWriter {
  public static void write(final Solution solution) throws IOException {
    final StringBuffer buffer = new StringBuffer();
    buffer.append("Microsoft Visual Studio Solution File, Format Version 9.00\n");

    final JavaProject[] javaProjects = solution.getJavaProjects().toArray(new JavaProject[solution.getJavaProjects().size()]);
    for (int i = javaProjects.length - 1; i > -1; i--) {
      final JavaProject javaProject = javaProjects[i];
      String relativePath = Files.relativePath(solution.getDir(), javaProject.getDir());
      if (relativePath.length() != 0)
        relativePath = relativePath + "/";

      buffer.append("Project(\"{" + solution.getUUID() + "}\") = \"" + javaProject.getName() + "\", \"" + relativePath + javaProject.getShortName() + ".javaproj\", \"{" + javaProject.getUUID() + "}\"\nEndProject\n");
    }

    buffer.append("Global\n");
    buffer.append("\tGlobalSection(SolutionConfigurationPlatforms) = preSolution\n");
    buffer.append("\t\tDebug|Any CPU = Debug|Any CPU\n");
    buffer.append("\t\tRelease|Any CPU = Release|Any CPU\n");
    buffer.append("\tEndGlobalSection\n");
    buffer.append("\tGlobalSection(ProjectConfigurationPlatforms) = postSolution\n");

    for (final JavaProject javaProject : solution.getJavaProjects()) {
      buffer.append("\t\t{" + javaProject.getUUID() + "}.Debug|Any CPU.ActiveCfg = Debug\n");
      buffer.append("\t\t{" + javaProject.getUUID() + "}.Debug|Any CPU.Build.0 = Debug\n");
      buffer.append("\t\t{" + javaProject.getUUID() + "}.Release|Any CPU.ActiveCfg = Release\n");
      buffer.append("\t\t{" + javaProject.getUUID() + "}.Release|Any CPU.Build.0 = Release\n");
    }

    buffer.append("\tEndGlobalSection\n");
    buffer.append("\tGlobalSection(SolutionProperties) = preSolution\n");
    buffer.append("\t\tHideSolutionNode = FALSE\n");
    buffer.append("\tEndGlobalSection\n");
    buffer.append("EndGlobal\n");

    final FileOutputStream out = new FileOutputStream(new File(solution.getDir(), solution.getShortName() + ".sln"));
    out.write(buffer.toString().getBytes());
    out.close();
  }
}