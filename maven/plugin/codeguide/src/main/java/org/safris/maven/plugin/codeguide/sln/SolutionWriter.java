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
import java.io.FileOutputStream;
import java.io.IOException;
import org.safris.commons.io.Files;
import org.safris.maven.plugin.codeguide.javaproj.JavaProject;

public class SolutionWriter {
    public static void write(Solution solution) throws IOException {
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

        for (JavaProject javaProject : solution.getJavaProjects()) {
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
