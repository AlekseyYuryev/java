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

package org.safris.xml.generator.compiler.processor.write;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;

public final class BindingFileFilter implements FileFilter {
  private final boolean acceptHidden;

  public BindingFileFilter(final boolean acceptHidden) {
    this.acceptHidden = acceptHidden;
  }

  public boolean accept(final File pathname) {
    if (!acceptHidden && pathname.isHidden())
      return false;

    if (pathname.isDirectory())
      return true;

    try {
      final InputStream in = pathname.toURI().toURL().openStream();
      final byte[] bytes = new byte[15];
      in.read(bytes);
      in.close();
      return new String(bytes).contains("Safris Software Inc.");
    }
    catch (final IOException e) {
      return false;
    }
  }
}