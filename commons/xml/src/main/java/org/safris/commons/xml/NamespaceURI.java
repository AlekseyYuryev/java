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

package org.safris.commons.xml;

import java.util.HashMap;
import java.util.Map;

public final class NamespaceURI {
  private static final Map<String,NamespaceURI> instances = new HashMap<String,NamespaceURI>();

  public static NamespaceURI getInstance(String namespaceURI) {
    NamespaceURI value = instances.get(namespaceURI);
    if (value == null)
      instances.put(namespaceURI, value = new NamespaceURI(namespaceURI));

    return value;
  }

  private final String namespaceURI;
  private final PackageName packageName;

  private NamespaceURI(String namespaceURI) {
  if(namespaceURI == null)
    throw new NullPointerException("namespaceURI == null");

    this.namespaceURI = namespaceURI;
    this.packageName = new PackageName(NamespaceBinding.getPackageFromNamespace(namespaceURI));
  }

  public PackageName getPackageName() {
    return packageName;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof NamespaceURI))
      return false;

    return namespaceURI.equals(((NamespaceURI)obj).namespaceURI);
  }

  public int hashCode() {
    return namespaceURI.hashCode();
  }

  public String toString() {
    return namespaceURI;
  }
}
