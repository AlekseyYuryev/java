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

package org.safris.commons.util.zip;

import java.io.File;
import java.net.URI;

// FIXME: This is not the right way to be doing this!
// FIXME: It creates unnecessary dependencies and relations.
public final class CachedFile extends File {
  private static final long serialVersionUID = 3333613083676335051L;

  private final byte[] bytes;

  public CachedFile(final String pathname, final byte[] bytes) {
    super(pathname);
    this.bytes = bytes;
  }

  public CachedFile(final String parent, final String child, final byte[] bytes) {
    super(parent, child);
    this.bytes = bytes;
  }

  public CachedFile(final File parent, final String child, final byte[] bytes) {
    super(parent, child);
    this.bytes = bytes;
  }

  public CachedFile(final URI uri, final byte[] bytes) {
    super(uri);
    this.bytes = bytes;
  }

  public byte[] getBytes() {
    return bytes;
  }
}