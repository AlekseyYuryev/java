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

package org.safris.web.depiction.tag.bean;

import java.io.File;
import org.safris.web.depiction.tag.bean.Bean;

public class FileBean implements Bean
{
  private final File file;
  
  public FileBean(File file)
  {
    this.file = file;
  }
  
  public File getObject()
  {
    return file;
  }
  
  public String getPath()
  {
    return file.getPath();
  }
  
  public long getLastModified()
  {
    return file.lastModified();
  }
  
  public String toString()
  {
    return file.toString();
  }
}
