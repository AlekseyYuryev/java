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

package org.safris.maven.plugin.dependency;

import java.util.List;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

public interface DependencyProperties {
  /**
   * Override the char used between the paths. This field is initialized to
   * contain the first character of the value of the system property
   * file.separator. On UNIX systems the value of this field is '/'; on
   * Microsoft Windows systems it is '\'. The default is File.separator
   */
  public String getFileSeparator();

  /**
   * Override the char used between path folders. The system-dependent
   * path-separator character. This field is initialized to contain the first
   * character of the value of the system property path.separator. This
   * character is used to separate filenames in a sequence of files given as a
   * path list. On UNIX systems, this character is ':'; on Microsoft Windows
   * systems it is ';'.
   */
  public String getPathSeparator();

  /**
   * Used to look up Artifacts in the remote repository.
   */
  public ArtifactFactory getFactory();

  /**
   * Used to look up Artifacts in the remote repository.
   */
  public ArtifactResolver getResolver();

  /**
   * The prefix to prepend on each dependent artifact. If undefined, the
   * paths refer to the actual files store in the local repository (the
   * stipVersion parameter does nothing then).
   */
  public String getRepositoryPath();

  /**
   * Location of the local repository.
   */
  public ArtifactRepository getLocal();

  /**
   * List of Remote Repositories used by the resolver
   */
  public List<ArtifactRepository> getRemoteRepos();

  /**
   * POM
   */
  public MavenProject getProject();

  /**
   * If we should exclude transitive dependencies
   */
  public boolean getExcludeTransitive();

  /**
   * Comma Separated list of Types to include. Empty String indicates include
   * everything (default).
   */
  public String getIncludeTypes();

  /**
   * Comma Separated list of Types to exclude. Empty String indicates don't
   * exclude anything (default).
   */
  public String getExcludeTypes();

  /**
   * Scope to include. An Empty string indicates all scopes (default).
   */
  public String getIncludeScope();

  /**
   * Scope to exclude. An empty string indicates no scopes (default).
   */
  public String getExcludeScope();

  /**
   * Comma Separated list of Classifiers to include. Empty String indicates
   * include everything (default).
   */
  public String getIncludeClassifiers();

  /**
   * Comma Separated list of Classifiers to exclude. Empty String indicates
   * don't exclude anything (default).
   */
  public String getExcludeClassifiers();

  /**
   * Specify classifier to look for. Example: sources
   */
  public String getClassifier();

  /**
   * Specify type to look for when constructing artifact based on classifier.
   * Example: java-source,jar,war
   */
  public String getType();

  /**
   * Comma separated list of Artifact names too exclude.
   */
  public String getExcludeArtifactIds();

  /**
   * Comma separated list of Artifact names to include.
   */
  public String getIncludeArtifactIds();

  /**
   * Comma separated list of Artifact names too exclude.
   */
  public String getExcludeGroupIdArtifactIds();

  /**
   * Comma separated list of Artifact names to include.
   */
  public String getIncludeGroupIdArtifactIds();

  /**
   * Comma separated list of GroupId Names to exclude.
   */
  public String getExcludeGroupIds();

  /**
   * Comma separated list of GroupIds to include.
   */
  public String getIncludeGroupIds();

  public Log getLog();
}