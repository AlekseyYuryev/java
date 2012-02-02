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

package org.safris.commons.xml.dom;

public class DOMStyle {
  protected static DOMStyle consolidate(final DOMStyle ... options) {
    if (options == null)
      return null;

    if (options.length == 0)
      return DEFAULT;

    if (options.length == 1)
      return options[0];

    final DOMStyle consolidated = new DOMStyle(DEFAULT_MASK);
    for (DOMStyle option : options)
      consolidated.mask = consolidated.mask | option.mask;

    return consolidated;
  }

  private static final int DEFAULT_MASK = 0x000;
  private static final int INDENT_MASK = 0x001;
  private static final int INDENT_ATTRS_MASK = 0x010;
  private static final int IGNORE_NAMESPACES_MASK = 0x100;

  private static final DOMStyle DEFAULT = new DOMStyle(DEFAULT_MASK);
  public static final DOMStyle INDENT = new DOMStyle(INDENT_MASK);
  public static final DOMStyle INDENT_ATTRS = new DOMStyle(INDENT_ATTRS_MASK);
  public static final DOMStyle IGNORE_NAMESPACES = new DOMStyle(IGNORE_NAMESPACES_MASK);

  private int mask = 0;

  private DOMStyle(final int mask) {
    this.mask = mask;
  }

  protected boolean isIndentAttributes() {
    return (mask & INDENT_ATTRS_MASK) == INDENT_ATTRS_MASK;
  }

  protected boolean isIndent() {
    return (mask & INDENT_MASK) == INDENT_MASK;
  }

  protected boolean isIgnoreNamespaces() {
    return (mask & IGNORE_NAMESPACES_MASK) == IGNORE_NAMESPACES_MASK;
  }

  public int hashCode() {
    return mask;
  }

  public boolean equals(final Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof DOMStyle))
      return false;

    return ((DOMStyle)obj).mask == mask;
  }
}
