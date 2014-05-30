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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.safris.commons.lang.PackageLoader;
import org.safris.commons.lang.Resource;
import org.safris.commons.lang.Resources;
import org.safris.commons.lang.reflect.Classes;
import org.safris.commons.net.URLs;
import org.safris.commons.xml.NamespaceBinding;

public abstract class AbstractBinding implements Cloneable {
  protected static final QName XSI_TYPE = new QName("http://www.w3.org/2001/XMLSchema-instance", "type", "xsi");
  protected static final QName XSI_NIL = new QName("http://www.w3.org/2001/XMLSchema-instance", "nil", "xsi");
  protected static final QName XMLNS = new QName("http://www.w3.org/2000/xmlns/", "xmlns");
  protected static final QName XML = new QName("http://www.w3.org/XML/1998/namespace", "xml");

  private static final Map<QName,Class<? extends Binding>> elementBindings = new HashMap<QName,Class<? extends Binding>>();
  private static final Map<QName,Class<? extends Binding>> typeBindings = new HashMap<QName,Class<? extends Binding>>();

  protected static void _$$registerSchemaLocation(final String namespaceURI, final Class<?> className, final String schemaReference) {
    final String simpleName = className.getName().replace('.', '/') + ".class";
    final Resource resource = Resources.getResource(simpleName);
    if (resource == null)
      throw new BindingError("Cannot register: systemId=\"" + namespaceURI + "\"\n\tclassName=\"" + className.getName() + "\"\n\tschemaReference=\"" + schemaReference + "\"");

    final URL parent = URLs.getParent(resource.getURL());
    try {
      BindingEntityResolver.registerSchemaLocation(namespaceURI, new URL(parent + "/" + schemaReference));
    }
    catch (final MalformedURLException e) {
      System.err.println("[ERROR] Cannot register: systemId=\"" + namespaceURI + "\"\n\tclassName=\"" + className.getName() + "\"\n\tschemaReference=\"" + schemaReference + "\"");
    }
  }

  protected static void _$$registerElement(final QName name, final Class<? extends Binding> cls) {
    elementBindings.put(name, cls);
  }

  private static void loadPackage(final String namespaceURI) {
    // FIXME: Look this over. Also make a dedicated RuntimeException for this.
    try {
      PackageLoader.getSystemPackageLoader().loadPackage(NamespaceBinding.getPackageFromNamespace(namespaceURI));
    }
    catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected static Class<? extends Binding> lookupElement(final QName name) {
    final Class<? extends Binding> clazz = elementBindings.get(name);
    if (clazz != null)
      return clazz;

    loadPackage(name.getNamespaceURI());
    return elementBindings.get(name);
  }

  protected static void _$$registerType(final QName name, final Class<? extends Binding> cls) {
    typeBindings.put(name, cls);
  }

  protected static Class<? extends Binding> lookupType(final QName name) {
    final Class<? extends Binding> clazz = typeBindings.get(name);
    if (clazz != null)
      return clazz;

    loadPackage(name.getNamespaceURI());
    return typeBindings.get(name);
  }

  protected static Object _$$getTEXT(final Binding binding) {
    return binding.text();
  }
  
  protected static QName getClassQName(final Class<? extends Binding> binding) {
    final org.safris.xml.generator.compiler.annotation.QName name = Classes.getDeclaredAnnotation(binding, org.safris.xml.generator.compiler.annotation.QName.class);
    return new QName(name.namespaceURI().intern(), name.localPart().intern(), name.prefix().intern());
  }

  protected static QName stringToQName(final java.lang.String name) {
    if (name == null || name.length() == 0)
      return null;

    int index = name.indexOf(":");
    if (index != -1)
      return new QName(null, name.substring(index + 1).intern(), name.substring(0, index).intern());

    return new QName(name.intern());
  }

  protected static String parsePrefix(final String name) {
    if (name == null)
      return null;

    int index = name.indexOf(":");
    if (index != -1)
      return name.substring(0, index);

    return null;
  }

  protected static String parseLocalName(final String name) {
    if (name == null)
      return null;

    int start = name.indexOf("{");
    if (start != -1) {
      int end = name.indexOf("}", start);
      if (end != -1)
        return name.substring(end + 1);
    }

    start = name.indexOf(":");
    if (start != -1)
      return name.substring(start + 1);

    return name;
  }
}