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

package org.w3.x2001.xmlschema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.safris.xml.generator.compiler.runtime.Attribute;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.ComplexType;

public abstract class $xs_anyType<T extends ComplexType> extends $xs_anySimpleType<T> {
  private final List<Binding<? extends BindingType>> anys = new ArrayList<Binding<? extends BindingType>>(7);
  private final List<Binding<? extends Attribute>> anys$ = new ArrayList<Binding<? extends Attribute>>(7);

  public $xs_anyType(final $xs_anyType<T> binding) {
    super(binding);
  }

  public $xs_anyType(final Object text) {
    super(text);
  }

  protected $xs_anyType(final String text) {
    super();
  }

  protected $xs_anyType() {
    super();
  }

  public void addAny$(final Binding<? extends Attribute> any) {
    this.anys$.add(any);
  }

  public List<Binding<? extends Attribute>> getAny$() {
    return anys$;
  }

  public void addAny(final Binding<? extends BindingType> any) {
    this.anys.add(any);
  }

  public List<Binding<? extends BindingType>> getAny() {
    return anys;
  }

  public $xs_anyType<T> clone() {
    return new $xs_anyType<T>(this) {
      protected QName _$$getName() {
        return this._$$getName();
      }

      protected $xs_anyType<T> inherits() {
        return this;
      }
    };
  }

  public boolean equals(final Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof $xs_anyType))
      return false;

    final $xs_anyType<?> that = ($xs_anyType<?>)obj;
    if (anys != null) {
      if (that.anys != null && anys.size() == that.anys.size()) {
        for (int i = 0; i < anys.size(); i++)
          if (!anys.get(i).equals(that.anys.get(i)))
            return false;
      }
      else {
        return false;
      }
    }
    else if (that.anys != null) {
      return false;
    }

    if (anys$ != null) {
      if (that.anys$ != null && anys$.size() == that.anys$.size()) {
        for (int i = 0; i < anys$.size(); i++)
          if (!anys$.get(i).equals(that.anys$.get(i)))
            return false;
      }
      else {
        return false;
      }
    }
    else if (that.anys$ != null) {
      return false;
    }

    return true;
  }
}