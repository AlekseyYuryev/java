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

package org.safris.maven.plugin.cobertura;

import java.io.File;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;

public abstract class CoberturaMojo extends AbstractMojo {
  /**
   * @parameter expression="${project}"
   * @readonly
   * @required
   */
  private MavenProject project;

  protected MavenProject getProject() {
    return project;
  }

  /**
   * @parameter default-value="${maven.test.skip}"
   * @readonly
   * @required
   */
  private Boolean mavenTestSkip = null;

  protected Boolean getMavenTestSkip() {
    return mavenTestSkip;
  }

  /**
   * @parameter default-value="${basedir}"
   * @readonly
   * @required
   */
  private String basedir = null;

  protected String getBasedir() {
    return basedir;
  }

  /**
   * @parameter default-value="${project.build.directory}"
   * @readonly
   * @required
   */
  private String directory = null;

  protected String getDirectory() {
    return directory;
  }

  /**
   * @parameter default-value="${project.build.sourceDirectory}"
   * @readonly
   * @required
   */
  private String sourceDirectory = null;

  protected String getSourceDirectory() {
    return sourceDirectory;
  }

  private File dataFile = null;

  protected File getDataFile() {
    if (dataFile != null)
      return dataFile;

    return dataFile = new File(getCoberturaDir().getAbsoluteFile(), "cobertura.ser");
  }

  private File coberturaDir = null;

  protected File getCoberturaDir() {
    if (coberturaDir != null)
      return coberturaDir;

    return coberturaDir = new File(getDirectory(), "cobertura");
  }
}
