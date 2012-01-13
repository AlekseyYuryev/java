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

package org.safris.xml.generator.compiler.lang;

import java.lang.reflect.Method;
import java.util.List;
import org.safris.xml.generator.lexer.lang.UniqueQName;

public class NativeBinding {
  private final UniqueQName name;
  private final GenericClass baseClass;
  private final GenericClass nativeClass;
  private final Method factoryMethod;
  private final boolean list;

  public NativeBinding(UniqueQName name, GenericClass baseClass, GenericClass nativeClass, Method factoryMethod) {
    if (name == null)
      throw new NullPointerException("name == null");

    if (baseClass == null)
      throw new NullPointerException("baseClass == null");

    this.name = name;
    this.baseClass = baseClass;
    this.nativeClass = nativeClass;
    this.factoryMethod = factoryMethod;
    this.list = nativeClass != null ? nativeClass.isList() : false;
  }

  public NativeBinding(UniqueQName name, GenericClass baseClass, GenericClass nativeClass) {
    this(name, baseClass, nativeClass, null);
  }

  public NativeBinding(UniqueQName name, GenericClass baseClass) {
    this(name, baseClass, null, null);
  }

  public boolean isList() {
    return list;
  }

  public UniqueQName getName() {
    return name;
  }

  public GenericClass getBaseClass() {
    return baseClass;
  }

  public GenericClass getNativeClass() {
    return nativeClass;
  }

  public Method getFactoryMethod() {
    return factoryMethod;
  }

  public boolean equals(Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof NativeBinding))
      return false;

    final NativeBinding nativeBinding = (NativeBinding)obj;
    return name.equals(nativeBinding.name) && baseClass.equals(nativeBinding.baseClass) && nativeClass.equals(nativeBinding.nativeClass);
  }

  public int hashCode() {
    return toString().hashCode();
  }

  public String toString() {
    return name.toString() + "\n" + baseClass.toString() + "\n" + nativeClass.toString();
  }

  public static class GenericClass {
    private final Class cls;
    private final Class type;
    private Boolean list = null;

    public GenericClass(Class cls, Class type) {
      if (cls == null)
        throw new NullPointerException("cls == null");

      this.cls = cls;
      this.type = type;
    }

    public GenericClass(Class cls) {
      this(cls, null);
    }

    public Class getCls() {
      return cls;
    }

    public Class getType() {
      return type;
    }

    protected boolean isList() {
      if (list != null)
        return list;

      return list = List.class.isAssignableFrom(cls);
    }

    public boolean equals(Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof GenericClass))
        return false;

      final GenericClass genericClass = (GenericClass)obj;
      return cls.equals(genericClass.cls) && (type == null && genericClass.type == null || type != null && type.equals(genericClass.type));
    }

    public int hashCode() {
      return toString().hashCode();
    }

    public String toString() {
      if (type != null)
        return cls.getName() + "<" + type.getName() + ">";

      return cls.getName();
    }
  }
}
