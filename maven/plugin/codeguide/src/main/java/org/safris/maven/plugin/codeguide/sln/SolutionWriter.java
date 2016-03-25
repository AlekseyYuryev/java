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

    try (final FileOutputStream out = new FileOutputStream(new File(solution.getDir(), solution.getShortName() + ".sln"))) {
      out.write(buffer.toString().getBytes());
    }
  }
}