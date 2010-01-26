/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
