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

import java.util.List;

public final class Manifest {
  private String destdir = null;
  private String link = null;
  private List<String> schemas = null;
  
  public String getDestdir() {
    return destdir;
  }
  
  public void setDestdir(final String destdir) {
    this.destdir = destdir;
  }
  
  public String getLink() {
    return link;
  }
  
  public void setLink(final String link) {
    this.link = link;
  }
  
  public List<String> getSchemas() {
    return schemas;
  }
  
  public void setSchemas(final List<String> schemas) {
    this.schemas = schemas;
  }
}