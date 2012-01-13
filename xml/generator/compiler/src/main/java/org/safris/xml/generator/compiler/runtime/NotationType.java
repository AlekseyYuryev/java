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

package org.safris.xml.generator.compiler.runtime;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;

public abstract class NotationType extends Binding<NotationType> implements BindingType {
  private static final Map<String,NotationType> notations = new HashMap<String,NotationType>(7);

  protected static void _$$registerNotation(NotationType notation) {
    notations.put(notation.getName(), notation);
  }

  public static NotationType parseNotation(String name) {
    return notations.get(name);
  }

  private final QName _$$name;

  protected NotationType() {
    this._$$name = new QName(getName());
    notations.put(getName(), this);
  }

  protected Binding inherits() {
    return this;
  }

  protected abstract String getName();
  protected abstract String getPublic();
  protected abstract String getSystem();
}
